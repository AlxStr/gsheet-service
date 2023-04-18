package com.gsheet.student.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", Locale.getDefault())
            .getString(messageKey);
    }

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages", locale)
            .getString(messageKey);
    }
}