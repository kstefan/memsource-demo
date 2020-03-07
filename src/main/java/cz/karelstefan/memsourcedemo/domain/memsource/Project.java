package cz.karelstefan.memsourcedemo.domain.memsource;

import lombok.Data;

import java.util.List;

@Data
public class Project {

    private String uid;
    private String name;
    private String status;
    private String sourceLang;
    private List<String> targetLangs;
}
