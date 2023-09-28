package com.github.gribanoveu.auth.constants;

/**
 * @author Evgeny Gribanov
 * @version 28.08.2023
 */
public class ErrorMessages {
    public static final String FORBIDDEN = "Action forbidden";

    public static final String PASSWORD_NOT_EQUALS = "Passwords don't match";
    public static final String PASSWORD_EQUALS = "Password cannot be the same as the old password";
    public static final String PASSWORD_UPDATED = "Password updated";
    public static final String OLD_PASSWORD_NOT_MATCH = "The old password doesn't match";

    public static final String USER_NOT_FOUND = "User '%s' not found";
    public static final String USER_NOT_EXIST = "No user with such data was found";
    public static final String USER_ALREADY_EXIST = "A user with the specified data exists";
    public static final String USER_CREATED = "User created";
    public static final String USER_DELETED = "User deleted";
    public static final String USER_UPDATED = "User updated";
    public static final String USER_NOT_UPDATED = "User not updated";
    public static final String USER_DISABLED = "User has been disabled";
    public static final String USER_ENABLED = "User has been enabled";
    public static final String USER_SET_DEFAULT_PASSWORD = "A default password has been set to the user";

    public static final String PERMISSION_NOT_EXIST = "No permission with such name was found";
    public static final String PERMISSION_EXIST = "Permission with such name already exist";
    public static final String PERMISSION_CREATED = "Permission successfully created";
    public static final String PERMISSION_DELETED = "Permission successfully deleted";
    public static final String PERMISSION_UPDATED = "Permission successfully updated";
    public static final String PERMISSION_NOT_UPDATED = "Permission not updated";
}
