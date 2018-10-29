package leyman.piano.controller;

import leyman.piano.filter.CORSFilter;
import leyman.piano.form.QueryForm;
import leyman.piano.model.Question;
import leyman.piano.service.FrontendService;
import leyman.piano.service.StackExchangeResponse;
import leyman.piano.service.StackExchangeService;
import leyman.piano.service.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PianoControllerTest {

    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    private MockMvc mvc;

    @Mock
    private FrontendService frontendService;

    @Mock
    private StackExchangeService stackExchangeService;

    @InjectMocks
    private PianoController pianoController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(pianoController)
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    public void getHomePage() throws Exception{
        when(frontendService.homePage()).thenReturn(new QueryForm());
        this.mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("queryForm"));
    }

    @Test
    public void getResults() throws Exception{
        QueryForm queryForm = new QueryForm();
        List<Question> questions = new ArrayList<>();
        StackExchangeResponse stackExchangeResponse = new StackExchangeResponse(questions, Status.SUCCESS);
        ResultActions resultActions = mvc.perform(post("/homePage")
                .param("title", "java"));
        when(stackExchangeService.getQuestions(queryForm)).thenReturn(stackExchangeResponse);

    }


}