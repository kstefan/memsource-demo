package cz.karelstefan.memsourcedemo.repository;

import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

    Optional<CredentialsEntity> findOptionalTopByOrderByIdDesc();
}
