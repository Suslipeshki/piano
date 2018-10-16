package leyman.piano;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FrontendService {
    private static List<Question> questions = new ArrayList<>();

    public String showHomePage(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "homePage";
    }

    public String searching(Model model, @ModelAttribute("queryForm") QueryForm queryForm) {
        String title = queryForm.getTitle();
        String author = queryForm.getTitle();
        Date toDate = queryForm.getToDate();
        boolean answered = queryForm.isAnswered();
        Question newQuestion = new Question(title, author, toDate, answered);
        questions.add(newQuestion);
        return "redirect:resultsPage";
    }

    public String results(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        model.addAttribute("questions", questions);
        return "resultsPage";
    }

}
