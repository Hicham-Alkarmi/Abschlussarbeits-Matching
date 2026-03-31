package com.ThesisIsComing.iihk_89.application.thema;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import java.util.List;
import java.util.Optional;

public interface ThemaRepository {
    Thema save(Thema thema);
    Optional<Thema> findById(Long themaId);
    List<Thema> findAll();
    List<Thema> findAllByBetreuerId(String betreuerId);
    void delete(Long id);
   // boolean existsById(Long themaId);
}