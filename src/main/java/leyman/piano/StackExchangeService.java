package leyman.piano;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StackExchangeService {
    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
            new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
    private RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    private Date epochToDate(long epoch) {
        return new Date(epoch*1000);
    }

    private String dateToEpoch(Date date) {
        if (date != null) {
            return Long.toString(date.getTime()/1000);
        }
        return null;
    }
    public List<Question> scan(QueryForm queryForm) {
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
        QuestionsDto questionsDto = restTemplate.getForObject(targetUrl,
                QuestionsDto.class);
        List<Question> questions = new ArrayList<>();

        for (Item item : questionsDto.getItems()) {
            Question question = new Question(
                    item.getTitle(),
                    item.getOwner().getDisplayName(),
                    epochToDate(item.getCreationDate()),
                    item.getIsAnswered(),
                    item.getLink()
            );
            questions.add(question);
        }
       return questions;
    }
}
