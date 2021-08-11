package com.example.ticketgenerationsystem.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, Integer> roleMap;
    public static final Map<String, Integer> ticketStatusMap;
    public static final Map<String, Integer> issueTypeMap;

    public static String OK = "Ok";

    public static String AUTHORIZATION_HEADER_PREFIX = "Bearer";

    public static String ALPHABET_REGEX = "[a-zA-Z]+";
    public static String EMAIL_REGEX = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static String DECIMAL_REGEX = "[0-9]+";
    public static String ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+";
    public static String TEXT_REGEX = "[\\S\\s]*[a-zA-Z0-9]+[\\S\\s]*";

    /*
     * Passwords will contain at least 1 upper case letter
     * Passwords will contain at least 1 lower case letter
     * Passwords will contain at least 1 number or special character
     * Passwords will contain at least 8 characters in length
     */
    public static String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";

    public static String INVALID_REQUEST_PARAMETERS = "Invalid Request Parameters";
    public static String OPERATION_NOT_ALLOWED = "Operation Not Allowed";

    public static String ROLE_OPERATOR = "ROLE_OPERATOR";
    public static String ROLE_EXECUTIVE = "ROLE_EXECUTIVE";
    public static String ROLE_ADMIN = "ROLE_ADMIN";

    public static String ISSUE_TYPE_GROUND_VISIT_REQUIRED = "ISSUE_TYPE_GROUND_VISIT_REQUIRED";

    public static String TICKET_STATUS_ACTIVE = "TICKET_STATUS_ACTIVE";
    public static String TICKET_STATUS_CLOSED = "TICKET_STATUS_CLOSED";

    public static int DEFAULT_PAGE_NO = 0;
    public static int DEFAULT_PAGE_SIZE = 5;

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put(ROLE_OPERATOR, 1);
        map.put(ROLE_EXECUTIVE, 2);
        map.put(ROLE_ADMIN, 3);
        roleMap = Collections.unmodifiableMap(map);

        Map<String, Integer> map1 = new HashMap<>();

        map1.put(TICKET_STATUS_ACTIVE, 1);
        map1.put(TICKET_STATUS_CLOSED, 2);
        ticketStatusMap = Collections.unmodifiableMap(map1);

        Map<String, Integer> map2 = new HashMap<>();

        map2.put(ISSUE_TYPE_GROUND_VISIT_REQUIRED, 1);
        issueTypeMap = Collections.unmodifiableMap(map2);
    }
}
