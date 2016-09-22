package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends Base {

    /**
     * ex: "image/png"
     */
    private String type;
    private String name;
    private String content;
}
