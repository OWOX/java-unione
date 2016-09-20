package com.owox.unione.transport;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.owox.unione.Build;
import com.owox.unione.model.Client;
import com.owox.unione.exception.UniOneAccessForbiddenException;
import com.owox.unione.exception.UniOneAuthorizationFailedException;
import com.owox.unione.exception.UniOneErrorServerResponseException;
import com.owox.unione.exception.UniOneException;
import com.owox.unione.exception.UniOneIllegalServerResponseException;
import com.owox.unione.model.responses.Response;


public class RestConnection {


    private static final Logger logger = Logger.getLogger(RestConnection.class.getName());

    private static final String VERSION = Build.VERSION;

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int UNAUTHORIZED_RESPONSE_STATUS_CODE = 401;

    private static final int ACCESS_FORBIDDEN_RESPONSE_STATUS_CODE = 403;

    public final static String ENDPOINT = "https://api.unisender.com";

    private final Client client;

    /**
     * Supported HTTP methods
     */
    private enum Method {

        GET,
        POST,
        PUT,
        DELETE
    }


    public RestConnection(Client client) throws UniOneException {
        this.client = client;
    }


    private HttpURLConnection createConnectionObject(String path, Method method) throws UniOneException {
        HttpURLConnection conn;
        try {
            URL url;
            url = new URL(this.ENDPOINT + path);

            // Retrieve the URLConnection object (but doesn't actually connect):
            // (HttpUrlConnection doesn't connect to the server until we've
            // got one of its streams)
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("User-Agent", "java-unione/" + VERSION);

            conn.setRequestProperty("Content-Type", "application/json");

            switch (method) {
                case GET:
                    conn.setRequestMethod("GET");
                    logger.fine("GET " + url);
                    break;
                case POST:
                    conn.setRequestMethod("POST");
                    // we write the POST data to the "output" stream:
                    conn.setDoOutput(true);
                    logger.fine("POST " + url);
                    break;
                case PUT:
                    conn.setRequestMethod("PUT");
                    // we write the POST data to the "output" stream:
                    conn.setDoOutput(true);
                    logger.fine("PUT " + url);
                    break;
                case DELETE:
                    conn.setRequestMethod("DELETE");
                    logger.fine("DELETE " + url);
                    break;
                default:
                    throw new UniOneException("Invalid Method");
            }
        } catch (MalformedURLException ex) {
            throw new UniOneException("Invalid path: " + path + ex.toString());
        } catch (ProtocolException ex) {
            throw new UniOneException("Invalid method:" + ex.toString());
        } catch (IOException ex) {
            throw new UniOneException("Error with connection to " + path + ex.toString());
        }
        return conn;
    }

    // Send HTTP data (payload) to server
    private void sendData(HttpURLConnection conn, String data) throws UniOneException {

        byte[] bytes = null;
        try {
            bytes = data.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            // This should never happen. UTF-8 should always be available but we
            // have to catch it so pass it on if it fails.
            throw new UniOneException(e);
        }

        String lenStr = Integer.toString(bytes.length);
        conn.setRequestProperty("Content-Length", lenStr);
        conn.setRequestProperty("Content-Type", "application/json");

        logger.fine("Sending data (" + lenStr + " bytes): " + data);
        // Send data. At this point connection to server may not be established,
        // but writing data to it will trigger the connection.
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(bytes);
            wr.flush();
        } catch (IOException ex) {
            throw new UniOneException("Error sending request data:" + ex.toString());
        }
    }

    // Send HTTP request to server
    private void sendRequest(HttpURLConnection conn, String data, Response response) throws UniOneException {

        if (data != null) {
            sendData(conn, data);
        }

        try {
            // If no data was sent (right above), then connection to server
            // is not made yet ; however asking for the response code (below)
            // makes conn connect to the server,
            // send the request, and start reading the response.

            // getResponseCode() blocks until the response code is read from the
            // stream from the server
            int code = conn.getResponseCode();
            response.setResponseCode(code);
            response.setContentType(conn.getHeaderField("Content-Type"));
            String msg = conn.getResponseMessage();
            response.setResponseMessage(msg);

        } catch (IOException ex) {
            throw new UniOneException("Connection error:" + ex.toString());
        }
    }

    // Read response body from server
    private Response receiveResponse(HttpURLConnection conn, Response response) throws UniOneException {

        try {
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 299) {
                // All 2xx responses are success
                return receiveSuccessResponse(conn, response);

            } else if (conn.getResponseCode() >= 400 && conn.getResponseCode() <= 499) {
                // 4xx errors means there is something wrong with the request
                return receiveErrorResponse(conn, response);

            } else if (conn.getResponseCode() >= 500 && conn.getResponseCode() <= 599) {
                // 5xx errors means something went wrong on server and should be retried
                return receiveErrorResponse(conn, response);

            } else {
                // We got some other response from the server.
                throw new UniOneIllegalServerResponseException("Unexpected server response ContentType("
                        + conn.getContentType()
                        + ") from "
                        + conn.getURL()
                        + " responseCode("
                        + conn.getResponseCode()
                        + ")"
                        + " contentLength("
                        + conn.getContentLength()
                        + ")");
            }

        } catch (IOException ex) {
            throw new UniOneErrorServerResponseException(
                    "Error reading server response: " + ex.toString() + " (" + response.getResponseMessage() + ")",
                    response.getResponseCode());
        }
    }

    // This is used to handle 2xx HTTP responses
    private Response receiveSuccessResponse(HttpURLConnection conn, Response response) throws UniOneException {


        if (conn.getContentLength() == 0) {
            return response;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET))) {
            // Buffer the result into a string:
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            response.setResponseBody(sb.toString());
        } catch (IOException ex) {
            String line = "";

            try {
                // We are in the success case handling but check the error stream anyway just in case
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), DEFAULT_CHARSET))) {

                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }

                    response.setResponseBody(sb.toString());

                    logger.warning("Server Response:\n" + sb.toString() + "\n");

                } catch (IOException ex2) {
                    // Ignore we are going to throw an exception anyway
                }
            } catch (Exception e) {
                // Log but ignore we are going to throw an exception anyway
                logger.log(Level.WARNING, "Error while handlign an HTTP response error. Ignoring and will use orginal exception", e);
            }

            logger.fine("Server Response:" + response);

            throw new UniOneErrorServerResponseException(
                    "Error reading server response: " + ex.toString() + ": " + sb.toString() + "(" + response.getResponseMessage() + ")",
                    response.getResponseCode());
        }
        return response;
    }

    // This is used to handle 2xx HTTP responses
    private Response receiveErrorResponse(HttpURLConnection conn, Response response) throws UniOneException, IOException {

        if (conn.getContentLength() == 0) {
            throw new UniOneErrorServerResponseException(
                    "Unexpected server response ContentType("
                            + conn.getContentType()
                            + ") from "
                            + conn.getURL()
                            + " contentLength("
                            + conn.getContentLength()
                            + ")",
                    conn.getResponseCode());
        }
        StringBuilder sb = new StringBuilder();

        try {
            // We are in the success case handling but check the error stream anyway just in case
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), DEFAULT_CHARSET))) {

                String line = "";
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                response.setResponseBody(sb.toString());

                logger.warning("Server Response:\n" + sb.toString() + "\n");

            } catch (IOException ex2) {
                // Ignore since an exception is getting thrown anyway
            }
        } catch (Exception e) {
            // Log but ignore since an exception is getting thrown anyway
            logger.log(Level.WARNING, "Error while handlign an HTTP response error. Ignoring and will use orginal exception", e);
        }

        logger.fine("Server Response:" + response);

        if (response.getResponseCode() == UNAUTHORIZED_RESPONSE_STATUS_CODE) {
            throw new UniOneAuthorizationFailedException(
                    "Error reading server response: "
                            + sb.toString()
                            + "("
                            + response.getResponseMessage()
                            + ") responseCode("
                            + response.getResponseCode()
                            + ")");

        } else if (response.getResponseCode() == ACCESS_FORBIDDEN_RESPONSE_STATUS_CODE) {
            throw new UniOneAccessForbiddenException(
                    "Error reading server response: "
                            + sb.toString()
                            + "("
                            + response.getResponseMessage()
                            + ") responseCode("
                            + response.getResponseCode()
                            + ")");
        } else {

            throw new UniOneErrorServerResponseException(
                    "Error reading server response: " + sb.toString() + "(" + response.getResponseMessage() + ")",
                    response.getResponseCode());
        }
    }

    // This method actually performs an HTTP request.
    // It is called by get(), put(), post() and delete() below
    private Response doHttpMethod(String path, Method method, String data, Response response) throws UniOneException {
        HttpURLConnection conn = null;
        try {
            response.setRequest(path);
            conn = createConnectionObject(path, method);
            sendRequest(conn, data, response);
            receiveResponse(conn, response);

            logger.fine("Server Response:" + response);

            return response;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Perform an HTTP POST request. This method throws an exception if the
     * server returns anything else than a 200.
     *
     * @param path
     *            API ENDPOINT to send the request to.
     * @param json
     *            POST data block to send with the request. May be null.
     * @return Server response to the request.
     * @throws UniOneException
     *             if something goes wrong
     */
    public Response post(String path, String json) throws UniOneException {
        Response response = new Response();
        return doHttpMethod(path, Method.POST, json, response);
    }

}
