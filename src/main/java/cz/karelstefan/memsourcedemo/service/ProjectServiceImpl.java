package cz.karelstefan.memsourcedemo.service;

import cz.karelstefan.memsourcedemo.domain.memsource.Project;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final MemsourceRestClient memsourceRestClient;
    private final CredentialsService credentialsService;

    @Override
    public List<Project> getProjects() {
        try {
            val projects = callWithToken(memsourceRestClient::getProjects).getContent();
            return Collections.unmodifiableList(projects);
        } catch (CredentialsNotFoundException e) {
            return Collections.emptyList();
        }
    }

    private <R> R callWithToken(Function<String, R> function) throws CredentialsNotFoundException {
        val credentialsOptional = credentialsService.findLatestCredentials();
        if (credentialsOptional.isEmpty()) {
            throw new CredentialsNotFoundException("Credentials not found");
        }
        var credentials = credentialsOptional.get();
        return function.apply(credentials.getToken());
    }

    public static class CredentialsNotFoundException extends Exception {
        public CredentialsNotFoundException(String message) {
            super(message);
        }
    }

}
