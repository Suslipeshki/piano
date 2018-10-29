package leyman.piano.service;

import leyman.piano.model.Question;

import java.util.List;

public class StackExchangeResponse {

    private List<Question> questions;
    private String message;
    private Status status;

    public StackExchangeResponse() {
    }

    public StackExchangeResponse(List<Question> questions, String message, Status status) {
        this.questions = questions;
        this.message = message;
        this.status = status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
