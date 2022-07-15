package com.example.softarex_demo_project.rest.questions;

/**
 * This interface contains URLs for QuestionsRestController.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionsRestUrls {
    String BASE_URL = "/api/v1/questions/";
    String ID_URL = "{id}";
    String CREATE_URL = "create";
    String USER_QUESTIONS_URL = "user/{id}";
    String ANSWER_URL = "answer/{id}";
    String EDIT_URL = "{id}/edit";
}
