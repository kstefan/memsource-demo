package cz.karelstefan.memsourcedemo.domain.memsource;

import lombok.Data;

@Data
public class LoginRequest {

    private String userName;
    private String password;
    private String code;

}
