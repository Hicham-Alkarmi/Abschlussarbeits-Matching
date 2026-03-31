package com.ThesisIsComing.iihk_89.application.matching;

import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domainService.MatchErgebnis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MatchingServiceTest {

    private ThemaRepository themaRepository;
    private BetreuerRepository betreuerRepository;
    private MatchingService underTest;

    @BeforeEach
    void setUp() {
        themaRepository = mock(ThemaRepository.class);
        betreuerRepository = mock(BetreuerRepository.class);
        underTest = new MatchingService(themaRepository, betreuerRepository);
    }

    @Test
    @DisplayName("Sollte leere Liste liefern, wenn Repositories leer sind")
    void test1() {
        // Arrange
        MatchingAnfrageDTO dto = new MatchingAnfrageDTO("Java", "Mathe");
        when(themaRepository.findAll()).thenReturn(List.of());
        when(betreuerRepository.findAll()).thenReturn(List.of());

        // Act
        List<MatchErgebnis> result = underTest.execute(dto);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Sollte ein Thema finden, wenn Fachgebiet übereinstimmt")
    void test2() {
        // Arrange
        MatchingAnfrageDTO dto = new MatchingAnfrageDTO("Java", "");

        Thema passendesThema = new Thema(1L, "Java Projekt", "b1", "Beschreibung",
                Set.of(new Fachgebiet("Java")), Set.of(), Set.of(), Set.of());

        when(themaRepository.findAll()).thenReturn(List.of(passendesThema));
        when(betreuerRepository.findAll()).thenReturn(List.of());

        // Act
        List<MatchErgebnis> result = underTest.execute(dto);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).istThema()).isTrue();
        assertThat(result.get(0).getThema().getTitel()).isEqualTo("Java Projekt");
        assertThat(result.get(0).getScore()).isEqualTo(1);
    }

    @Test
    @DisplayName("Sollte Ergebnisse nach Score sortieren (Thema mit Score 2 vor Betreuer mit Score 1)")
    void test3() {
        // Arrange

        MatchingAnfrageDTO dto = new MatchingAnfrageDTO("Java, Spring", "");

        Thema topThema = new Thema(1L, "Profi Thema", "b1", "D",
                Set.of(new Fachgebiet("Java"), new Fachgebiet("Spring")), Set.of(), Set.of(), Set.of());

        Betreuer betreuer = mock(Betreuer.class);
        when(betreuer.getFachgebiete()).thenReturn(Set.of(new Fachgebiet("Java")));

        when(themaRepository.findAll()).thenReturn(List.of(topThema));
        when(betreuerRepository.findAll()).thenReturn(List.of(betreuer));

        // Act
        List<MatchErgebnis> result = underTest.execute(dto);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getScore()).isEqualTo(2);
        assertThat(result.get(0).getThema()).isEqualTo(topThema);

        assertThat(result.get(1).getScore()).isEqualTo(1);
        assertThat(result.get(1).getBetreuer()).isEqualTo(betreuer);
    }
}