package leyman.piano.service;

import leyman.piano.form.QueryForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class FrontendServiceTest {

    @TestConfiguration
    static class FrontendServiceTestContextConfiguration {

        @Bean
        public FrontendService frontendService() {
            return new FrontendService();
        }
    }

    @Autowired
    private FrontendService frontendService;

    @Test
    public void whenMethodCalled_thenQueryFormShouldBeReturned() {
        QueryForm queryForm = frontendService.homePage();
        assertThat(queryForm).isNotEqualTo(null);
    }
}