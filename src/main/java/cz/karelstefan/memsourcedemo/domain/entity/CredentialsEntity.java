package cz.karelstefan.memsourcedemo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "credentials")
@Data
public class CredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneOffset.UTC);

    private String token;
}
