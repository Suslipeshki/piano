package leyman.piano.service.frontend;

import leyman.piano.form.QueryForm;
import org.springframework.stereotype.Component;

@Component
public class FrontendServiceImpl implements FrontendService {

    public QueryForm homePage() {
        return new QueryForm();
    }
}
