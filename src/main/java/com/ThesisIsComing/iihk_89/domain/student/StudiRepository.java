package com.ThesisIsComing.iihk_89.domain.student;
import java.util.List;
import java.util.Optional;

public interface StudiRepository {
    void save( Studi studi);
    Optional<Studi> findById(String githubId);
    List<Studi> findAll();
    boolean existsById(String githubId);
}
