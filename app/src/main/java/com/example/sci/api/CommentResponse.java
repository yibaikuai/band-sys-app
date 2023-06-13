package com.example.sci.api;

public class CommentResponse {
    private int code;
    private static String msg;
    private String data;
    private static int status;

    public static int getStatus() {
        return status;
    }

    public static String getMessage() {
        return msg;
    }

}
