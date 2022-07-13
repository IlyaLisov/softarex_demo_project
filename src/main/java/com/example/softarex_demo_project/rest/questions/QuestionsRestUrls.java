package com.example.softarex_demo_project.rest.questions;

/**
 * This interface contains URLs for QuestionsRestController.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionsRestUrls {
    String baseUrl = "/api/v1/questions/";
    String idUrl = "{id}";
    String createUrl = "create";
    String userQuestionsUrl = "user/{id}";
}
