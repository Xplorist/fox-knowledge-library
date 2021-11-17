package com.foxconn.bidding.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 郵件內容bean
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class EmailContent {
    @NonNull
    private String to;
    private String from;
    @NonNull
    private String subject;
    @NonNull
    private String body;
    private String subtype;
    private String fileAttach; //base64string
    private String filename;
    private String userid;
    private String password;
}
