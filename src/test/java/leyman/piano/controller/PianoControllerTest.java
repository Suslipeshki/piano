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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PianoControllerTest {

    private MockMvc mvc;
    private List<Question> questions = new ArrayList<>();

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
    public void whenMethodCalled_thenQueryFormAddsIntoModel()
            throws Exception{
        when(frontendService.homePage())
                .thenReturn(new QueryForm());
        this.mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("queryForm"));
    }
    @Test
    public void whenQueryForm_thenSendItToStackExchange_and_whenResponseWithStatusSuccess_thenQuestionsAddsIntoModel()
            throws Exception{
        StackExchangeResponse stackExchangeResponse =
                new StackExchangeResponse(questions, "", Status.SUCCESS);
        when(stackExchangeService.getQuestions(any(QueryForm.class)))
                .thenReturn(stackExchangeResponse);
        mvc.perform(post("/homePage")
                .param("title", "java"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("questions"));
    }
    @Test
    public void whenQueryForm_thenSendItToStackExchange_andWhenResponseWithStatusFailed_thenFailedMessageAddsIntoModel()
        throws Exception {
        StackExchangeResponse stackExchangeResponse =
                new StackExchangeResponse(questions, "Line \"TITLE\" is required!", Status.FAILED);
        when(stackExchangeService.getQuestions(any(QueryForm.class)))
                .thenReturn(stackExchangeResponse);
        mvc.perform(post("/homePage")
                .param("title", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("failedMessage"));
    }
    @Test
    public void whenQueryForm_thenSendItToStackExchange_andWhenResponseWithStatusNotFound_thenNotFoundMessageAddsIntoModel()
            throws Exception {
        StackExchangeResponse stackExchangeResponse =
                new StackExchangeResponse(questions, "Nothing found. Try to change the request parameters.", Status.NOT_FOUND);
        when(stackExchangeService.getQuestions(any(QueryForm.class)))
                .thenReturn(stackExchangeResponse);
        mvc.perform(post("/homePage")
                .param("title", "ILNUR"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("nothingFoundMessage"));
    }
    @Test
    public void whenQueryForm_thenSendItToStackExchange_andWhenResponseWithStatusError_thenErrorMessageAddsIntoModel()
            throws Exception {
        StackExchangeResponse stackExchangeResponse =
                new StackExchangeResponse(questions, "\"www.api.stackexchange.ru\" is not available at the moment", Status.ERROR);
        when(stackExchangeService.getQuestions(any(QueryForm.class)))
                .thenReturn(stackExchangeResponse);
        mvc.perform(post("/homePage")
                .param("title", "java"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"));
    }

}