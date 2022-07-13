package com.example.softarex_demo_project.rest.users;

/**
 * This interface contains URLs for UserRestController.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserRestUrls {
    String baseUrl = "/api/v1/users/";
    String idUrl = "{id}";
    String editUrl = "{id}/edit";
}
