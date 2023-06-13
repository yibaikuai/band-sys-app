package com.example.sci.api;

public class BandResponse {
    private static String msg;
    private Band data;
    private static int status;

    public static int getStatus() {
        return status;
    }
    public static String getMessage() {return msg;}
    public Band getData() {
        return data;
    }

    public static class Band {
        private String bandName;
        private String bandCreTime;
        private String bandAlbum;
        private String bandMember;
        private String[] bandComment;
        private int bmenum;

        private int isLove;

        public Band() {
        }

        public String getBandName() {
            return bandName;
        }
        public String getBandCreTime() {
            return bandCreTime;
        }
        public String getBandAlbum() {
            return bandAlbum;
        }
        public String getBandMember() {
            return bandMember;
        }
        public String[] getBandComment() {
            return bandComment;
        }

        public int getIsLove(){ return isLove;}
        public int getNum(){ return bmenum;}
    }
}
