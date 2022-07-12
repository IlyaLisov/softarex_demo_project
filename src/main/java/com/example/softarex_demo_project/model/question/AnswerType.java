package com.example.softarex_demo_project.model.question;

import java.util.Locale;

/**
 * This enum describes answer types.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public enum AnswerType {
    SINGLE_LINE_TEXT("Single line text"), MULTILINE_TEXT("Multiline text"), RADIO_BUTTON("Radiobutton"),
    CHECKBOX("Checkbox"), COMBOBOX("Combobox"), DATE("Date");

    private final String name;

    AnswerType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
