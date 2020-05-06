package com.csce4901.tnma.Constants;

public class UserConstant {

    //constant to match with DB field names
    public static final String EVENT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String IS_FEATURED = "featured";

    public static final String FS_EVENTS_COLLECTION = "events";
    public static final String FS_EVENT_ENROLLED_USERS = "enrolledUsers";
    public static final String FS_USERS_COLLECTION = "users";
    public static final String FS_BLOGS_COLLECTION = "blogs";
    public static final String FS_BLOGS_USER_COMMENTS = "comments";
    public static final String FS_BLOGS_USER_COMMENT_COUNT = "commentCount";
    public static final String FS_BLOG_BOOST_COUNT = "boostCount";
    public static final String FS_BLOG_BOOST_USERS = "boostedByUser";
    public static final String FS_DONATIONS_COLLECTION = "donations";
    public static final String FS_DONATIONS_DETAILS= "details";
    public static final String FS_LOGO = "dynamic-logo";
    public static final String FS_LOGO_DOC = "TNMA_LOGO";
    public static final String FS_QUESTIONS_COLLECTION = "questions";
    public static final String FS_QUESTIONS_QUESTION = "question";
    public static final String FS_QUESTIONS_ISANSWERED = "answered";
    public static final String FS_QUESTIONS_ANSWER = "answer";
    public static final String FS_QUESTIONS_ANSWERER = "answeredBy";

    public static final String FS_CHATS_COLLECTION = "chats";
    public static final String FS_CHATS_MSG = "message";
    public static final String IS_SENDER_CHATS = "sender";
    public static final String IS_RECEIVER_CHATS = "receiver";

    public static final int GENERAL_USER_ROLE = 1;
    public static final int STUDENT_ROLE = 2;
    public static final int MENTOR_ROLE = 3;
    public static final int ADMIN_ROLE = 4;

    public static final String IS_STUDENT = "Student";
    public static final String IS_MENTOR = "Mentor";
}
