package leyman.piano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FrontendService {
    @Autowired
    private StackExchangeService stackExchangeService;
    private static List<Question> questions = new ArrayList<>();

    @Value("${error.message}")
    private String errorMessage;


    public String homePage(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "homePage";
    }

    public String search(Model model, @ModelAttribute("queryForm") QueryForm queryForm) {
        if (queryForm.getTitle() != "") {
            List<Question> scan = stackExchangeService.scan(queryForm);
            model.addAttribute("questions", scan);
            return "resultsPage";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "homePage";
    }

    public String results(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        model.addAttribute("question", questions);
        return "resultsPage";
    }

}
