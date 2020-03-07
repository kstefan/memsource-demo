package cz.karelstefan.memsourcedemo.service;

import cz.karelstefan.memsourcedemo.domain.api.CredentialsPayload;
import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity;
import cz.karelstefan.memsourcedemo.domain.memsource.LoginRequest;
import cz.karelstefan.memsourcedemo.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final MemsourceRestClient memsourceRestClient;
    private final CredentialsRepository credentialsRepository;

    @Override
    public CredentialsEntity updateCredentials(CredentialsPayload credentials) {
        val credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername(credentials.getUsername());
        credentialsEntity.setPassword(credentials.getPassword());
        credentialsEntity.setToken(getToken(credentials));
        credentialsRepository.saveAndFlush(credentialsEntity);
        return credentialsEntity;
    }

    @Override
    public Optional<CredentialsEntity> findLatestCredentials() {
        return credentialsRepository.findOptionalTopByOrderByIdDesc();
    }

    private String getToken(CredentialsPayload credentials) {
        val loginRequest = new LoginRequest();
        loginRequest.setUserName(credentials.getUsername());
        loginRequest.setPassword(credentials.getPassword());
        return memsourceRestClient.getToken(loginRequest);
    }
}
