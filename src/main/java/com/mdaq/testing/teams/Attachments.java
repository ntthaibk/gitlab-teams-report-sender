package com.mdaq.testing.teams;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Attachments {
    private String contentType;
    private String contentUrl;
    private Content content;

}
