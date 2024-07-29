package tech.mooki;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest (webEnvironment =
SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardApplicationTests {

    Logger log = Logger.getLogger(CashCardApplicationTests.class.getName());

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldReturnACashCardWhenDataIsSaved() {
        ResponseEntity <String> response =
                restTemplate.getForEntity("/cashcards/99", String.class);

        DocumentContext context = JsonPath.parse(response.getBody());

        log.info("Response: " + response);

        Number id = context.read("$.id");
        Double amount = context.read("$.amount");
        assertThat(id).isEqualTo(99);
        assertThat(amount).isEqualTo(123.45);
    }

    @Test
    public void shouldNotReturnACashCardWithAnUnknownId (){
        ResponseEntity <String> response =
                restTemplate.getForEntity("/cashcards/100", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }
}
