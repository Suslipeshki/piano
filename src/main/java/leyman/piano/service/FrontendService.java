package leyman.piano.service;

import leyman.piano.form.QueryForm;
import leyman.piano.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Component
public class FrontendService {
    @Autowired
    private StackExchangeService stackExchangeService;

    @Value("${error.message}")
    private String errorMessage;

    @Value("${nothingFound.message}")
    private String nothingFoundMessage;

    public String homePage(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "homePage";
    }

    public String search(Model model, @ModelAttribute("queryForm") QueryForm queryForm) {
        if (queryForm.getTitle() != "") {
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

    public String resultsPage(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "resultsPage";
    }

}
