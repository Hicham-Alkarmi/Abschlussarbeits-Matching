package com.ThesisIsComing.iihk_89.domain.domainService;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domainService.MatchErgebnis;
import com.ThesisIsComing.iihk_89.domainService.MatchingBerechner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchingBerechnerTest {

    private MatchingBerechner berechner;

    @BeforeEach
    void setUp() {
        berechner = new MatchingBerechner();
    }



    private Thema erstelleThema(String... fachgebiete) {
        return new Thema(null, "Test Thema", "123", "",
                Arrays.stream(fachgebiete)
                        .map(Fachgebiet::new).toList(),
                List.of(), List.of(), List.of());
    }

    private Betreuer erstelleBetreuer(String... fachgebiete) {
        Betreuer betreuer = new Betreuer(null, "456",
                new Name("Max", "Muster"),
                new Kontaktinfo(
                        new Raum("24.01.02.03"),
                        new Email("max@hhu.de")));
        betreuer.updateFachgebiete(String.join(", ", fachgebiete));
        return betreuer;
    }


    @Test
    @DisplayName("Thema matcht wenn Fachgebiet passt")
    void test1() {
        // arrange
        Thema thema = erstelleThema("mathe");

        // act
        List<MatchErgebnis> ergebnisse = berechner.berechne(
                "mathe", "",
                List.of(thema),
                List.of()
        );

        // assert
        assertEquals(1, ergebnisse.size());
        assertTrue(ergebnisse.get(0).istThema());
        assertEquals(1, ergebnisse.get(0).getScore());
    }

    @Test
    @DisplayName("Kein Ergebnis wenn kein Fachgebiet passt")
    void test2() {
        // arrange
        Thema thema = erstelleThema("mathe");
        Betreuer betreuer = erstelleBetreuer("physik");

        // act
        List<MatchErgebnis> ergebnisse = berechner.berechne(
                "informatik", "",
                List.of(thema),
                List.of(betreuer)
        );

        // assert
        assertTrue(ergebnisse.isEmpty());
    }
}