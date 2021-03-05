package br.com.ntconsultant.instant.cooperative.voting.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Daniel Santos
 * {@link br.com.ntconsultant.instant.cooperative.voting.util.RestUtils} classe útil que tem por motivação e finalidade
 * possuir metodos genéricos para efetuar chamadas de consumo de API {@link RestTemplate}
 * @since 05/03/2021
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestUtils {

    private final RestTemplate restTemplate;

    public <T> T responseObject(Class<T> setType, String url, HttpMethod method, HttpEntity<?> body) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, method, body, setType);

            if (response.getStatusCodeValue() != 200) {
                return null;
            }

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("URL call failed | url: {}, statusCode: {}, body: {}", url, e.getRawStatusCode(),
                    e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("responseObject() | ", e.getMessage());
        }
        return null;
    }
}
