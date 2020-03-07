package cz.karelstefan.memsourcedemo.domain.memsource;

import lombok.Data;

import java.util.List;

@Data
public class ProjectsResponse {

    private List<Project> content;
}
