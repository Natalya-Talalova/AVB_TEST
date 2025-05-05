package org.example.common.service;

import lombok.AllArgsConstructor;
import org.example.common.dto.CompanyResponceDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@AllArgsConstructor
public class CompanyServiceClient {

    private final RestClient restClient;

    public CompanyResponceDto getCompanyById(Long companyId) {
        try {
            return restClient.get()
                    .uri("http://company-service:8082/api/companies/{id}", companyId)
                    .retrieve()
                    .body(CompanyResponceDto.class);
        } catch (RestClientResponseException ex) {
            if (ex.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }
            throw ex;
        }
    }
}
