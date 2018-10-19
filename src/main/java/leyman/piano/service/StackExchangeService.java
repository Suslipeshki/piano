package leyman.piano.service;

import leyman.piano.model.Item;
import leyman.piano.model.Question;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.NoRouteToHostException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StackExchangeService {

    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
            new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());

    private RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

    public List<leyman.piano.model.Question> getQuestions(leyman.piano.form.QueryForm queryForm) {
        List<Question> questions = new ArrayList<>();
        URI targetUrl = UriComponentsBuilder.fromUriString("http://api.stackexchange.com/2.2/search")
                .queryParam("intitle", queryForm.getTitle())
                .queryParam("fromDate", dateToEpoch(queryForm.getFromDate()))
                .queryParam("toDate", dateToEpoch(queryForm.getToDate()))
                .queryParam("order", "desc")
                .queryParam("sort", "activity")
                .queryParam("site", "stackoverflow")
                .queryParam("filter","!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST")
                .build()
                .encode()
                .toUri();
        try {
            leyman.piano.model.Items items = restTemplate.getForObject(targetUrl,
                    leyman.piano.model.Items.class);
            for (Item item : items.getItems()) {
                Question question = new Question(
                        item.getTitle(),
                        item.getOwner().getDisplayName(),
                        epochToDate(item.getCreationDate()),
                        item.getIsAnswered(),
                        item.getLink()
                );
                questions.add(question);
            }
        } catch (Exception e) {
            return questions;
        }
        return questions;
    }

    //Date conversation methods
    private Date epochToDate(long epoch) {
        return new Date(epoch*1000);
    }

    private String dateToEpoch(Date date) {
        if (date != null) {
            return Long.toString(date.getTime()/1000);
        }
        return null;
    }
}
