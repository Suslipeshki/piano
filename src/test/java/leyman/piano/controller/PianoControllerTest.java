package leyman.piano.controller;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Question;
import leyman.piano.service.FrontendService;
import leyman.piano.service.StackExchangeResponse;
import leyman.piano.service.StackExchangeService;
import leyman.piano.service.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PianoController.class)
@AutoConfigureMockMvc
public class PianoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FrontendService frontendService;

    @MockBean
    private StackExchangeService stackExchangeService;

/*
    @Test
    public void whenHttpRequest_thenReturnModelWithQueryForm() throws Exception {
        given(frontendService.homePage()).willReturn(new QueryForm());
        ResultActions results = mvc.perform(get("/"));
        results.andExpect(status().isOk())
                .andExpect(model().attributeExists("queryForm"));
    }
*/

    @Test
    public void whenHttpRequestWithQueryFormAndResponseWithStatusNotFound_thenReturnModelWithNotFoundMessage()
            throws Exception {
        QueryForm queryForm = new QueryForm();
        queryForm.setTitle("java");
        queryForm.setFromDate(new Date(1540732307000L));
        queryForm.setToDate(new Date(1540732307000L));
        StackExchangeResponse stackExchangeResponse = new StackExchangeResponse(null, Status.NOT_FOUND);
        String title = "ILNUR";
        given(frontendService.homePage()).willReturn(queryForm);
        given(stackExchangeService.getQuestions(queryForm)).willReturn(stackExchangeResponse);
        ResultActions results = mvc.perform(post("/resultsPage")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("title", title));
    }
}