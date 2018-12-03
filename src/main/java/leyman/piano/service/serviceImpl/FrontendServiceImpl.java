package leyman.piano.service.serviceImpl;

import leyman.piano.form.QueryForm;
import org.springframework.stereotype.Component;

@Component
public class FrontendServiceImpl implements leyman.piano.service.FrontendService {

    public QueryForm homePage() {
        return new QueryForm();
    }
}
