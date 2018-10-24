package leyman.piano.service;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Item;
import leyman.piano.model.Items;
import leyman.piano.model.Question;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class StackExchangeServiceTest {

    @TestConfiguration
    static class StackExchangeServiceTestContextConfiguration {
        @Bean
        public StackExchangeService stackExchangeService() {
            return new StackExchangeService();
        }
    }

    @Autowired
    private StackExchangeService stackExchangeService;

    @MockBean
    private RestTemplate restTemplate = new RestTemplate();

    @Before
    public void setUp() {
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

        //results for whenValidFilledQueryForm_thenQuestionsShouldBeFound
        List<Item> items11 = new ArrayList<>();
        items11.add(item2);
        Items items1 = new Items(items11);
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                "?intitle=java" +
                "&fromDate=1538391975" +
                "&toDate=1540384595" +
                "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items1);

        //results for whenUnfilledFromDateQueryForm_thenQuestionsShouldBeFound
        List<Item> items22 = new ArrayList<>();
        items22.add(item1);
        items22.add(item2);
        Items items2 = new Items(items22);
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                        "?intitle=java" +
                        "&fromDate" +
                        "&toDate=1540384595" +
                        "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items2);

        //results for whenUnfilledToDateQueryForm_thenQuestionsShouldBeFound
        List<Item> items33 = new ArrayList<>();
        items33.add(item2);
        items33.add(item3);
        Items items3 = new Items(items33);
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                        "?intitle=java" +
                        "&fromDate=1538391975" +
                        "&toDate" +
                        "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items3);

        //results for whenUnfilledDatesQueryForm_thenQuestionsShouldBeFound
        List<Item> items44 = new ArrayList<>();
        items44.add(item1);
        items44.add(item2);
        items44.add(item3);
        Items items4 = new Items(items44);
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                        "?intitle=java" +
                        "&fromDate" +
                        "&toDate" +
                        "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items4);

        //results for whenNonexistentTitleFilledQueryForm_thenQuestionsShouldNotBeFound
        Items items5 = new Items();
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                        "?intitle=Nonexistent Title" +
                        "&fromDate=1538391975" +
                        "&toDate=1540384595" +
                        "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items5);

        //results for whenNotValidDatesFilledQueryForm_thenQuestionsShouldNotBeFound
        Items items6 = new Items();
        Mockito.when(restTemplate.getForObject("http://api.stackexchange.com/2.2/search" +
                        "?intitle=java" +
                        "&fromDate=1540384595" +
                        "&toDate=1538391975" +
                        "&order=desc&sort=activity&site=stackoverflow&filter=!*1ShIjGxdqfo8*16Vn7KWgOU(mrLERWX8-iCBEtST",
                leyman.piano.model.Items.class)).thenReturn(items6);
    }

    @Test
    public void whenValidFilledQueryForm_thenQuestionsShouldBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        queryForm.setFromDate(new Date(1538391975000L));
        queryForm.setToDate(new Date(1540384595000L));
    }

    @Test
    public void whenUnfilledFromDateQueryForm_thenQuestionsShouldBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        queryForm.setToDate(new Date(1540384595000L));
    }

    @Test
    public void whenUnfilledToDateQueryForm_thenQuestionsShouldBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        queryForm.setFromDate(new Date(1538391975000L));
    }

    @Test
    public void whenUnfilledDatesQueryForm_thenQuestionsShouldBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
    }

    @Test
    public void whenNonexistentTitleFilledQueryForm_thenQuestionsShouldNotBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("Nonexistent title");
        queryForm.setFromDate(new Date(1538391975000L));
        queryForm.setToDate(new Date(1540384595000L));
    }

    @Test
    public void whenNotValidDatesFilledQueryForm_thenQuestionsShouldNotBeFound() {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        queryForm.setFromDate(new Date(1538391975000L));
        queryForm.setToDate(new Date(1540384595000L));
    }

}