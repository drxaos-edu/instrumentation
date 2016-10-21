package com.github.drxaos.edu.instrumentation.proxy.app;


import java.util.Calendar;

public class Message {

    private Calendar date;
    private String username;
    private String text;

    private transient String formatted;

    public Message(Calendar date, String username, String text) {
        this.date = date;
        this.username = username;
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
