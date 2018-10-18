package leyman.piano;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QueryForm {
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }
}
