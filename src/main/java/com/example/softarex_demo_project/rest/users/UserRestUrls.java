package com.example.softarex_demo_project.rest.users;

/**
 * This interface contains URLs for UserRestController.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserRestUrls {
    String BASE_URL = "/api/v1/users/";
    String ID_URL = "{id}";
    String FROM_QUESTIONS_URL = "{id}/questions/from";
    String TO_QUESTIONS_URL = "{id}/questions/to";
    String USER_EMAILS = "emails";
}
