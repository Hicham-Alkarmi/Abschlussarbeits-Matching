package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;


import com.ThesisIsComing.iihk_89.TestcontainersConfiguration;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domain.thema.Veranstaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class ThemaRepositoryImplTest {

    @Autowired
    ThemaDAO themaDAO;

    private ThemaRepositoryImpl themaRepository;

    @BeforeEach
    void setUp() {
        themaRepository = new ThemaRepositoryImpl(themaDAO);
    }

    private Thema erstelleTestThema(String titel, String betreuerId) {
        return new Thema(
                null,
                titel,
                betreuerId,
                "Eine spannende Beschreibung",
                List.of(new Fachgebiet("KI")),
                List.of(new Veranstaltung("Propädeutikum")),
                List.of(new Link(URI.create("https://hhu.de"), "Link Text")),
                List.of("anhang.pdf")
        );
    }

    @Test
    @DisplayName("Save & Load: Behält das Thema all seine Anhängsel? ")
    void test1() {
        // arrange
        Thema thema = erstelleTestThema("Deep Learning in Java", "prof-1");

        // act
        Thema gespeichert = themaRepository.save(thema);

        // assert
        Optional<Thema> gefunden = themaRepository.findById(gespeichert.getId());
        assertTrue(gefunden.isPresent());

        Thema geladen = gefunden.get();
        assertEquals("Deep Learning in Java", geladen.getTitel());
        assertEquals(1, geladen.getFachgebiete().size(), "Fachgebiete fehlen!");
        assertEquals(1, geladen.getLinks().size(), "Links wurden verschluckt!");
        assertEquals("https://hhu.de", geladen.getLinks().get(0).getUrl().toString());
    }

    @Test
    @DisplayName("Finder-Check: Finden wir alle Themen eines Betreuers? 🔍")
    void test2() {
        // arrange
        themaRepository.save(erstelleTestThema("Thema 1", "betreuer-x"));
        themaRepository.save(erstelleTestThema("Thema 2", "betreuer-x"));
        themaRepository.save(erstelleTestThema("Thema 3", "andere-id"));

        // act
        List<Thema> betreuerThemen = themaRepository.findAllByBetreuerId("betreuer-x");

        // assert
        assertEquals(2, betreuerThemen.size(), "Die Anzahl der Themen für diesen Betreuer stimmt nicht.");
        assertTrue(betreuerThemen.stream().anyMatch(t -> t.getTitel().equals("Thema 1")));
        assertTrue(betreuerThemen.stream().anyMatch(t -> t.getTitel().equals("Thema 2")));
    }
}