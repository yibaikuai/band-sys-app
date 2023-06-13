package com.example.sci.api;

public class PerformResponse {
    private static String msg;
    private Perform data;
    private static int status;

    public static int getStatus() {
        return status;
    }
    public static String getMessage() {return msg;}
    public Perform getData() {
        return data;
    }

    public static class Perform {
        private String performName;
        private String performTime;
        private String performSpot;
        private String performBand;
        private String[] performComment;

        public String getPerformName() {return performName;}
        public String getPerformTime() {
            return performTime;
        }
        public String getPerformSpot() {
            return performSpot;
        }
        public String getPerformBand() {
            return performBand;
        }
        public String[] getPerformComment() {
            return performComment;
        }
    }

}
