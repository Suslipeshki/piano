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

    @Autowired
    private StackExchangeService stackExchangeService;

    @RequestMapping(value = {"/", "/homePage"}, method = RequestMethod.GET)
    public String getHomePage(Model model){
        return frontendService.homePage(model);
    }

    @RequestMapping(value = {"/resultsPage"}, method = RequestMethod.GET)
    public String getResults(Model model) {
        return frontendService.results(model);
    }

    @RequestMapping(value = {"/homePage", "/resultsPage"}, method = RequestMethod.POST)
    public String getSearch(Model model, QueryForm queryForm) {
        return frontendService.search(model, queryForm);
    }
}
