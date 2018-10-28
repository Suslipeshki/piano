package leyman.piano.controller;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Question;
import leyman.piano.service.FrontendService;
import leyman.piano.service.StackExchangeResponse;
import leyman.piano.service.StackExchangeService;
import leyman.piano.service.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PianoController {

    @Autowired
    private FrontendService frontendService;

    @Autowired
    private StackExchangeService stackExchangeService;

    @Value("${failed.message}")
    private String failedMessage;

    @Value("${nothingFound.message}")
    private String nothingFoundMessage;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/homePage"}, method = RequestMethod.GET)
    public String getHomePage(Model model) {
        QueryForm queryForm = frontendService.homePage();
        model.addAttribute("queryForm", queryForm);
        return "homePage";
    }

    @RequestMapping(value = {"/homePage", "/resultsPage"}, method = RequestMethod.POST)
    public String getResults(Model model, QueryForm queryForm) {
        StackExchangeResponse response = stackExchangeService.getQuestions(queryForm);
        switch (response.getStatus()) {
            case FAILED:
                model.addAttribute("failedMessage", failedMessage);
                return "resultsPage";
            case SUCCESS:
                model.addAttribute("questions", response.getQuestions());
                return "resultsPage";
            case NOT_FOUND:
                model.addAttribute("nothingFoundMessage", nothingFoundMessage);
                return "resultsPage";
            case ERROR:
                model.addAttribute("errorMessage", errorMessage);
                return "resultsPage";

        }
        model.addAttribute("errorMessage", errorMessage);
        return "resultsPage";
    }
}
