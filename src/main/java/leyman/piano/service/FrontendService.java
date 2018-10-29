package leyman.piano.service;

import leyman.piano.form.QueryForm;
import org.springframework.stereotype.Component;

@Component
public class FrontendService implements leyman.piano.serviceImpl.FrontendService {

    public QueryForm homePage() {
        return new QueryForm();
    }
}
