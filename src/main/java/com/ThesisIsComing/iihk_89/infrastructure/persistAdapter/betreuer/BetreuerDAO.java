package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import org.springframework.data.repository.CrudRepository;

public interface BetreuerDAO
        extends CrudRepository<BetreuerRecord, Long> {

    BetreuerRecord findByGithubId(String githubId);
    boolean existsByGithubId(String githubID);
    void deleteByGithubId(String githubId);
}

// crud gibt es diese gratis
// -> save(record) , findById(id) , findAll() , delete(record)
