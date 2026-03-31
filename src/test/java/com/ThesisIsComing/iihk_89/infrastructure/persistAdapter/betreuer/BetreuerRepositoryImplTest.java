package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import com.ThesisIsComing.iihk_89.TestcontainersConfiguration; // Import deiner neuen Config
import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class) // Container-Config laden
public class BetreuerRepositoryImplTest {

    @Autowired
    BetreuerDAO betreuerDAO; // Das Spring-Data Interface (Infrastruktur)

    private BetreuerRepository betreuerRepository; // Dein Domain-Interface (Adapter)

    @BeforeEach
    void setUp() {
        // Manueller Zusammenbau nach Onion
        betreuerRepository = new BetreuerRepositoryImpl(betreuerDAO);
    }

    private Betreuer erstelleBetreuer(String githubId) {
        return new Betreuer(
                null,
                githubId,
                new Name("Max", "Muster"),
                new Kontaktinfo(new Raum("24.01.02.03"), new Email("max@hhu.de"))
        );
    }

    @Test
    @DisplayName("Betreuer wird gespeichert und über den Adapter gefunden")
    void test1() {
        // arrange
        Betreuer betreuer = erstelleBetreuer("123");

        // act
        betreuerRepository.save(betreuer);

        // assert
        Optional<Betreuer> gefunden = betreuerRepository.findByGithubId("123");
        assertTrue(gefunden.isPresent());
        assertEquals("Max", gefunden.get().getName().getVorname());
    }

    @Test
    @DisplayName("Existenzprüfung über den Adapter funktioniert")
    void test2() {
        // arrange
        betreuerRepository.save(erstelleBetreuer("456"));

        // act & assert
        assertTrue(betreuerRepository.existsByGithubId("456"));
        assertFalse(betreuerRepository.existsByGithubId("gibts-nicht"));
    }


}