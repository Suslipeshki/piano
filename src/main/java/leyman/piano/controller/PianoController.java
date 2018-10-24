package leyman.piano.controller;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Question;
import leyman.piano.service.FrontendService;
import leyman.piano.service.StackExchangeService;
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

    @Value("${error.message}")
    private String errorMessage;

    @Value("${nothingFound.message}")
    private String nothingFoundMessage;

    @RequestMapping(value = {"/", "/homePage"}, method = RequestMethod.GET)
    public String getHomePage(Model model){
        QueryForm queryForm = frontendService.homePage();
        model.addAttribute("queryForm", queryForm);
        return "homePage";
    }

    @RequestMapping(value = {"/homePage", "/resultsPage"}, method = RequestMethod.POST)
    public String getResults(Model model, QueryForm queryForm) {
        if (!queryForm.getTitle().equals("")) {
            List<Question> questions = stackExchangeService.getQuestions(queryForm);
            if (questions.size() != 0) {
                model.addAttribute("questions", questions);
                return "resultsPage";
            }
            model.addAttribute("nothingFoundMessage", nothingFoundMessage);
            return "resultsPage";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "resultsPage";
    }
}
