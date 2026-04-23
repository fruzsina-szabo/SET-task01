package com.set.image_service_api.service;

import java.util.List;

import com.set.image_service_api.dto.ImageDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ImageQueryService {

    private static final ParameterizedTypeReference<List<ImageDto>> IMAGE_LIST_TYPE =
        new ParameterizedTypeReference<>() {};

    private final RestClient imageBackendRestClient;

    public ImageQueryService(RestClient imageBackendRestClient) {
        this.imageBackendRestClient = imageBackendRestClient;
    }

    public List<ImageDto> getByLabel(String label) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label is required");
        }

        return imageBackendRestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/images")
                .queryParam("label", label.trim())
                .build())
            .retrieve()
            .body(IMAGE_LIST_TYPE);
    }
}
