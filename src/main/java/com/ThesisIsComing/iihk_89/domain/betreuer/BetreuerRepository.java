package com.ThesisIsComing.iihk_89.domain.betreuer;

import java.util.List;
import java.util.Optional;

//app layer -

public interface BetreuerRepository {
    void save (Betreuer betreuer);
    Optional<Betreuer> findByGithubId(String githubId);
    List<Betreuer> findAll();
    boolean existsByGithubId(String githubID);
   // boolean existsByEmail(String email);
    void deleteByGithubId(String githubId);
}