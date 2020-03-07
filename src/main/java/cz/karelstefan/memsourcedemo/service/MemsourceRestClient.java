package cz.karelstefan.memsourcedemo.service;

import cz.karelstefan.memsourcedemo.domain.memsource.LoginRequest;
import cz.karelstefan.memsourcedemo.domain.memsource.ProjectsResponse;

public interface MemsourceRestClient {
    String getToken(LoginRequest loginRequest);

    ProjectsResponse getProjects(String token);
}
