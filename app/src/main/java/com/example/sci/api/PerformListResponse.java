package com.example.sci.api;

public class PerformListResponse {
    private static String msg;
    private String[] data;
    private int status;

    public int getStatus() {
        return status;
    }
    public static String getMessage() {return msg;}
    public String[] getData() {
        return data;
    }

}
