package leyman.piano.service.stackexchange;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Item;
import leyman.piano.model.Items;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static leyman.piano.utils.DateEpochConverter.dateToEpoch;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StackExchangeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private StackExchangeServiceImpl service;

    @Test
    public void whenValidQueryForm_thenReturnsListQuestionsAndStatusSuccess() {

        Items items = createItems();
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(items);

        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("asdfg");
        StackExchangeResponse response = service.getQuestions(queryForm);
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        assertNotNull(response.getQuestions());
        assertNotEquals(0, response.getQuestions().size());
        URI expected = expectedURI(queryForm);
        verify(restTemplate, times(1)).getForObject(expected, Items.class);
    }

    @Test()
    public void whenTitleFromQueryFormIsNull_thenReturnsResponseWithFailedStatus() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("");
        StackExchangeResponse response = service.getQuestions(queryForm);
        assertNotNull(response);
        assertEquals(Status.FAILED, response.getStatus());
    }

    @Test
    public void whenRestTemplateReturnNull_thenReturnsResponseWithStatusNotFound() {

        Items items = createNullItems();
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(items);

        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        StackExchangeResponse response = service.getQuestions(queryForm);
        assertNotNull(response.getStatus());
        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenURLIsNotAvailable_thenReturnsResponseWithStatusError() {

        when(restTemplate.getForObject(any(URI.class), any()))
                .thenThrow(new org.springframework.web.client.ResourceAccessException(""));
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        StackExchangeResponse response = service.getQuestions(queryForm);
        assertNotNull(response.getStatus());
        assertEquals(Status.ERROR, response.getStatus());
    }

    private URI expectedURI(QueryForm queryForm) {
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

    public Items createItems() {
        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item(
                "Gilbert Gabriel",
                false,
                1536750435,
                "https://stackoverflow.com/questions/52968968/javafx-jsobject-not-able-to-link-to-java-class-trought-html",
                "JavaFX JSObject not able to link to java class trought html"
        );
        Item item2 = new Item(
                "Javeria Shabbir",
                true,
                1539342435,
                "https://stackoverflow.com/questions/52968918/split-an-already-split-array-string-in-java",
                "split an already split array string in Java"
        );
        Item item3 = new Item(
                "Embid123",
                false,
                1540384596,
                "https://stackoverflow.com/users/10476860/embid123",
                "JAVA creating ArrayList&lt;String&gt; in a method call."
        );

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        return new Items(itemList);
    }

    private Items createNullItems() {
        List<Item> itemList = new ArrayList<>();
        return new Items(itemList);
    }
}