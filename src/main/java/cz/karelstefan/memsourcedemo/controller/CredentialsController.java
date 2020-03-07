package cz.karelstefan.memsourcedemo.controller;

import cz.karelstefan.memsourcedemo.domain.api.CredentialsPayload;
import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity;
import cz.karelstefan.memsourcedemo.service.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credentials")
public class CredentialsController {

    private final CredentialsService credentialsService;

    @GetMapping
    public CredentialsPayload getCredentials() {
        return credentialsService.findLatestCredentials()
                .map(CredentialsPayload::new)
                .orElse(new CredentialsPayload());
    }

    @PostMapping
    public CredentialsEntity updateCredentials(@Validated @RequestBody CredentialsPayload payload) {
        return credentialsService.updateCredentials(payload);
    }

}
