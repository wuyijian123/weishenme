package cn.edu.lzzy.mypractices.constant;


public final class ApiConstant {
    private ApiConstant(){}

    public static final String API_PRODUCES = "application/json";

    public static final String ROUTE_AUTH_ROOT = "api/v1";
    public static final String ROUTE_AUTH_LOGIN = "/login";
    public static final String ROUTE_AUTH_LOGOUT = "/logout";
    public static final String ROUTE_AUTH_EXISTS = "/exists/{user}";
    public static final String ROUTE_AUTH_REGISTER = "/register";
    public static final String ROUTE_FILE_UPLOAD = "/upload";

    public static final String ROUTE_USER_ROOT = "api/v1/admin";
    public static final String ROUTE_USERS_ALL = "/";
    public static final String ROUTE_USERS_SEARCH = "/search/{kw}";
    public static final String ROUTE_USERS_PAGE = "/page";
    public static final String ROUTE_USERS_APPLYING = "/applying";
    public static final String ROUTE_USERS_APPROVE = "/approve";
    public static final String ROUTE_USERS_DECLINE = "/decline";
    public static final String ROUTE_USERS_BAN = "/ban";

    public static final String ROUTE_COURSE_ROOT = "api/v1/course";
    public static final String ROUTE_COURSE_ALL = "/";
    public static final String ROUTE_COURSE_PAGE = "/page";
    public static final String ROUTE_COURSE_TEACHER = "/teacher";
    public static final String ROUTE_COURSE_ADD = "/add";
    public static final String ROUTE_COURSE_PUT = "/put/{id}";
    public static final String ROUTE_COURSE_STUDENT = "/student";
    public static final String ROUTE_COURSE_REMOVE = "/remove";

    public static final String ROUTE_APPLY_ROOT = "api/v1/apply";
    public static final String ROUTE_APPLY_STUDENT_APPLY = "/apply";
    public static final String ROUTE_APPLY_STUDENT_COURSES = "/courses";
    public static final String ROUTE_APPLY_STUDENT_ENROLLED = "/enrolled";
    public static final String ROUTE_APPLY_STUDENT_APPLYING = "/applying";
    public static final String ROUTE_APPLY_STUDENT_DECLINED = "/declined";
    public static final String ROUTE_APPLY_APPROVE = "/approve";
    public static final String ROUTE_APPLY_DECLINE = "/decline";
    public static final String ROUTE_APPLY_REMOVE = "/remove";
    public static final String ROUTE_APPLY_TEACHER_ENROLLED = "/teacher/enrolled";
    public static final String ROUTE_APPLY_TEACHER_APPLYING = "/teacher/applying";
    public static final String ROUTE_APPLY_TEACHER_DECLINED = "/teacher/declined";

    public static final String ROUTE_CHAPTER_ROOT = "api/v1/chapter";
    public static final String ROUTE_CHAPTER_TEACHER = "/teacher";
    public static final String ROUTE_CHAPTER_STUDENT = "/student";
    public static final String ROUTE_CHAPTER_ADD = "/add";
    public static final String ROUTE_CHAPTER_PUT = "/put/{id}";
    public static final String ROUTE_CHAPTER_DELETE = "/delete";

    public static final String ROUTE_QUESTION_ROOT = "api/v1/question";
    public static final String ROUTE_QUESTION_CHAPTER = "/chapter";
    public static final String ROUTE_QUESTION_CHAPTER_PAGE = "/chapter/page";
    public static final String ROUTE_QUESTION_ADD = "/add";
    public static final String ROUTE_QUESTION_PUT = "/put/{id}";
    public static final String ROUTE_QUESTION_DELETE = "/delete";
    public static final String ROUTE_QUESTION_OPTION_ADD = "/option/add";
    public static final String ROUTE_QUESTION_OPTION_DELETE = "/option/delete";

    public static final String ROUTE_RESULT_ROOT = "api/v1/result";
    public static final String ROUTE_RESULT_POST = "/post";
    public static final String ROUTE_RESULT_DELETE = "/delete";
    public static final String ROUTE_RESULT_CHAPTER_DELETE = "/chapter/delete";
    public static final String ROUTE_RESULT_ONE = "/";
    public static final String ROUTE_RESULT_CHAPTER = "/chapter";
    public static final String ROUTE_RESULT_QUESTION = "/question";

    public static final String ROUTE_AUTHORIZE_ROOT = "api/v1/authorize";
    public static final String ROUTE_AUTHORIZE_URLS = "/urls";
    public static final String ROUTE_AUTHORIZE_ALLOCATE = "/allocate";
    public static final String ROUTE_AUTHORIZE_TYPES = "/types";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_LOGIN_USER_NAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";

    public static final String KEY_REGISTER_NICK_NAME = "nick_name";
    public static final String KEY_REGISTER_USER_NAME = "user_name";
    public static final String KEY_REGISTER_PASSWORD = "password";
    public static final String KEY_REGISTER_AVATAR = "avatar";
    public static final String KEY_REGISTER_EMAIL = "email";
    public static final String KEY_REGISTER_PHONE = "phone";
    public static final String KEY_REGISTER_APPLY_TEACHER = "apply_teacher";

    public static final String PARAM_UPLOAD_FILE_NAME = "file";

    public static final String KEY_COURSE_NAME = "name";
    public static final String KEY_COURSE_DESC = "desc";
    public static final String KEY_COURSE_COVER = "cover";
    public static final String KEY_COURSE_OPEN = "open";
    public static final String KEY_COURSE_TEACHER_ID = "teacher_id";
    public static final String KEY_COURSE_ID = "course_id";

    public static final String KEY_APPLY_STUDENT_ID = "student_id";
    public static final String KEY_APPLY_COURSE_ID = "course_id";
    public static final String KEY_APPLY_APPLICANT_ID = "applicant_id";

    public static final String KEY_CHAPTER_COURSE_ID = "course_id";
    public static final String KEY_CHAPTER_NAME = "name";
    public static final String KEY_CHAPTER_DESCRIPTION = "desc";
    public static final String KEY_CHAPTER_OPEN = "open";
    public static final String KEY_CHAPTER_ID = "chapter_id";

    public static final String KEY_QUESTION_TYPE = "type";
    public static final String KEY_QUESTION_CONTENT = "content";
    public static final String KEY_QUESTION_ANALYSIS = "analysis";
    public static final String KEY_QUESTION_ORDINAL = "ordinal";
    public static final String KEY_QUESTION_CHAPTER_ID = "chapter_id";
    public static final String KEY_QUESTION_OPTION_ARRAY = "options";
    public static final String KEY_OPTION_CONTENT = "content";
    public static final String KEY_OPTION_LABEL = "label";
    public static final String KEY_OPTION_IS_ANSWER = "is_answer";
    public static final String KEY_QUESTION_ID = "question_id";
    public static final String KEY_QUESTION_OPTION_ID = "option_id";

    public static final String KEY_RESULT_RESULTS = "results";
    public static final String KEY_RESULT_QUESTION_ID = "question_id";
    public static final String KEY_RESULT_OPTION_ARRAY_ID = "options_id";
    public static final String KEY_RESULT_ID = "result_id";
    public static final String KEY_RESULT_CHAPTER_ID = "chapter_id";
    public static final String PARAM_RESULT_STUDENT_ID = "sid";
    public static final String PARAM_RESULT_CHAPTER_ID = "cid";
    public static final String PARAM_RESULT_QUESTION_ID = "qid";

    public static final String KEY_AUTHORIZE_URL = "url";
    public static final String KEY_AUTHORIZE_VALUES = "values";
    public static final String KEY_AUTHORIZE_MODULES = "modules";
}
