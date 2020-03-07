package cz.karelstefan.memsourcedemo.service;

import cz.karelstefan.memsourcedemo.domain.api.CredentialsPayload;
import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity;

import java.util.Optional;

public interface CredentialsService {
    CredentialsEntity updateCredentials(CredentialsPayload credentials);

    Optional<CredentialsEntity> findLatestCredentials();
}
