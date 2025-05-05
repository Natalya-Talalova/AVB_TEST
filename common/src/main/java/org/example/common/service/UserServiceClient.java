package org.example.common.service;

import lombok.AllArgsConstructor;
import org.example.common.dto.UserResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceClient {

    private final RestClient restClient;

    public List<UserResponseDto> fetchUsersByIds(List<Long> ids) {
        try {
            return restClient
                    .post()  // RequestBodyUriSpec
                    .uri("http://user-service:8081/api/users/batch")
                    .body(ids)  // передаём сам список Long
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<UserResponseDto>>() {});
        } catch (RestClientResponseException ex) {
            // если batch-эндпоинт вернул 404 — значит ни одного пользователя
            if (ex.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return List.of();
            }
            throw ex;
        }
    }
}
