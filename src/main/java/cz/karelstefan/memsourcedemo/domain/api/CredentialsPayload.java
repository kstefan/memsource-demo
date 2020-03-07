package cz.karelstefan.memsourcedemo.domain.api;

import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CredentialsPayload {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public CredentialsPayload(CredentialsEntity entity) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
    }
}
