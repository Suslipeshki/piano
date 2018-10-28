package leyman.piano.service;

import leyman.piano.model.Question;

import java.util.List;

public class StackExchangeResponse {

    private List<Question> questions;
    private Status status;

    public StackExchangeResponse() {
    }

    public StackExchangeResponse(List<Question> questions, Status status) {
        this.questions = questions;
        this.status = status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
