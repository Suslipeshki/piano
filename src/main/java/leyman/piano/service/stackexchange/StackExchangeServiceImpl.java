package leyman.piano.service.stackexchange;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Item;
import leyman.piano.model.Items;
import leyman.piano.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static leyman.piano.utils.DateEpochConverter.dateToEpoch;
import static leyman.piano.utils.DateEpochConverter.epochToDate;

@Component
public class StackExchangeServiceImpl implements StackExchangeService {

    private final RestTemplate restTemplate;

    @Autowired
    public StackExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StackExchangeResponse getQuestions(QueryForm queryForm) {
        try {
            if (queryForm.getTitle().equals("")) {
                return new StackExchangeResponse
                        (Collections.emptyList(),"Line \"TITLE\" is required!", Status.FAILED);
            }
            URI targetUrl = prepareRequestUrl(queryForm);
            Items items = restTemplate.getForObject(targetUrl, Items.class);
            if (items.getItems().size() == 0) {
                return new StackExchangeResponse
                        (null,"Nothing found. Try to change the request parameters.", Status.NOT_FOUND);
            }
            List<Question> questions = processResponse(items);
            return new StackExchangeResponse(questions, "", Status.SUCCESS);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            return new StackExchangeResponse
                    (null,"\"www.api.stackexchange.ru\" is not available at the moment", Status.ERROR);
        }
    }

    private List<Question> processResponse(Items items) {
        List<Question> questions = new ArrayList<>();
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
        return questions;
    }

    private URI prepareRequestUrl(QueryForm queryForm) {
        return UriComponentsBuilder.fromUriString("http://api.stackexchange.com/2.2/search")
                    .queryParam("intitle", queryForm.getTitle())
                    .queryParam("fromDate", dateToEpoch(queryForm.getFromDate()))
                    .queryParam("toDate", dateToEpoch(queryForm.getToDate()))
                    .queryParam("order", "desc")
                    .queryParam("sort", "activity")
                    .queryParam("site", "stackoverflow")
                    .queryParam("filter", "!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST")
                    .build()
                    .encode()
                    .toUri();
    }
}
