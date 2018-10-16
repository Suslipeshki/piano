package leyman.piano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PianoController {

    @Autowired
    private FrontendService frontendService;


    @RequestMapping(value = {"/", "/homePage"}, method = RequestMethod.GET)
    public String getShowHomePage(Model model){
        return frontendService.showHomePage(model);
    }

    @RequestMapping(value = {"/homePage"}, method = RequestMethod.POST)
    public String getSearching(Model model, QueryForm queryForm) {
        return frontendService.searching(model, queryForm);
    }

    @RequestMapping(value = {"/resultsPage"}, method = RequestMethod.GET)
    public String getResults(Model model) {
        return frontendService.results(model);
    }
}
