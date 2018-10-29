package leyman.piano.service.stackexchange;

import leyman.piano.form.QueryForm;
import leyman.piano.service.stackexchange.StackExchangeResponse;

public interface StackExchangeService {
    StackExchangeResponse getQuestions(QueryForm queryForm);
}
