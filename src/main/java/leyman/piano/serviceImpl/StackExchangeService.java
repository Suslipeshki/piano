package leyman.piano.serviceImpl;

import leyman.piano.form.QueryForm;
import leyman.piano.service.StackExchangeResponse;

public interface StackExchangeService {
    StackExchangeResponse getQuestions(QueryForm queryForm);
}
