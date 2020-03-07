package cz.karelstefan.memsourcedemo.service;

import cz.karelstefan.memsourcedemo.domain.memsource.LoginRequest;
import cz.karelstefan.memsourcedemo.domain.memsource.LoginResponse;
import cz.karelstefan.memsourcedemo.domain.memsource.ProjectsResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
public class MemsourceRestClientImpl implements MemsourceRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public MemsourceRestClientImpl(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${app.memsource.base-url}") String baseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.baseUrl = baseUrl;
    }

    @Override
    public String getToken(LoginRequest loginRequest) {
        try {
            val response = restTemplate.postForObject(composeUrl("/v1/auth/login"), loginRequest, LoginResponse.class);
            if (response == null || response.getToken() == null) {
                throw new RuntimeException("Authentication token hasn't been returned");
            }
            return response.getToken();
        } catch (HttpClientErrorException e) {
            throw new UnauthorizedException("Login failed");
        }
    }

    @Override
    public ProjectsResponse getProjects(String token) {
        return Objects.requireNonNull(
                restTemplate.getForObject(composeUrl("/v1/projects", token), ProjectsResponse.class));
    }

    private String composeUrl(String uri) {
        return composeUrl(uri, null);
    }

    private String composeUrl(String uri, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).path(uri);
        if (token != null) {
            builder.queryParam("token", token);
        }
        return builder.toUriString();
    }

    public static class UnauthorizedException extends RuntimeException {

        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
