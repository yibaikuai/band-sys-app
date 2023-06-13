package com.example.sci.api;

public class MemberResponse {
    private static String msg;
    private Member data;
    private static int status;

    public static int getStatus() {
        return status;
    }
    public static String getMessage() {return msg;}
    public Member getData() {
        return data;
    }

    public static class Member {
        private String memberName;
        private String memberSex;
        private String memberJoinTime;
        private String memberAge;
        private String memberAffiBand;
        private String memberBirthday;
        private String[] memberComment;

        public String getMemberName() {
            return memberName;
        }
        public String getMemberSex() {
            return memberSex;
        }
        public String getMemberJoinTime() {
            return memberJoinTime;
        }
        public String getMemberAge() {
            return memberAge;
        }
        public String getMemberAffiBand() {
            return memberAffiBand;
        }
        public String[] getMemberComment() {
            return memberComment;
        }
    }

}
