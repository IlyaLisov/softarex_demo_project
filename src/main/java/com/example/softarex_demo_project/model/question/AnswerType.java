package com.example.softarex_demo_project.model.question;

import java.util.Locale;

/**
 * This enum describes answer types.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public enum AnswerType {
    SINGLE_LINE_TEXT, MULTILINE_TEXT, RADIO_BUTTON, CHECKBOX, COMBOBOX, DATE;

    @Override
    public String toString() {
        return (name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT)).replaceAll("_", " ");
    }
}
