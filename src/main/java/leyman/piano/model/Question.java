package leyman.piano.model;

import java.util.Date;

public class Question {
    private String title;
    private String author;
    private Date creation;
    private boolean answered;
    private String link;

    public Question() {
    }

    public Question(String title, String author, Date creation, boolean answered, String link) {
        this.title = title;
        this.author = author;
        this.creation = creation;
        this.answered = answered;
        this.link = link;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Date getCreation() { return creation; }
    public void setCreation(Date creation) { this.creation = creation; }

    public boolean isAnswered() { return answered; }
    public void setAnswered(boolean answered) { this.answered = answered; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
