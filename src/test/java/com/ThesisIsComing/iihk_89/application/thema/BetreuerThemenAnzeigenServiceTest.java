package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BetreuerThemenAnzeigenServiceTest {

    private Thema createThema(Long id, String betreuerId) {
        return new Thema(id, "Titel", betreuerId, "Beschreibung",
                List.of(), List.of(), List.of(), List.of());
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn der Betreuer gar nicht existiert")
    void test1() {
        // Arrange
        String id = "123";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        ThemaRepository themaRepository = mock(ThemaRepository.class);
        when(betreuerRepository.existsByGithubId(id)).thenReturn(false);

        BetreuerThemenAnzeigenService betreuerThemenAnzeigenService = new BetreuerThemenAnzeigenService(themaRepository,betreuerRepository);
        // Act & Assert
        assertThatThrownBy(() -> betreuerThemenAnzeigenService.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nicht gefunden");
    }

    @Test
    @DisplayName("Sollte leere Liste liefern, wenn Betreuer existiert, aber keine Themen hat")
    void test2() {
        // Arrange
        String id = "b-0";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        ThemaRepository themaRepository = mock(ThemaRepository.class);

        when(betreuerRepository.existsByGithubId(id)).thenReturn(true);
        when(themaRepository.findAllByBetreuerId(id)).thenReturn(List.of());

        BetreuerThemenAnzeigenService betreuerThemenAnzeigenService = new BetreuerThemenAnzeigenService(themaRepository,betreuerRepository);
        // Act
        List<Thema> result = betreuerThemenAnzeigenService.execute(id);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Sollte ein Thema liefern, wenn Betreuer existiert und 1 Thema hat")
    void test3() {
        // Arrange
        String id = "123";
        Thema thema = createThema(1L, id);
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        ThemaRepository themaRepository = mock(ThemaRepository.class);
        when(betreuerRepository.existsByGithubId(id)).thenReturn(true);
        when(themaRepository.findAllByBetreuerId(id)).thenReturn(List.of(thema));

        BetreuerThemenAnzeigenService betreuerThemenAnzeigenService = new BetreuerThemenAnzeigenService(themaRepository,betreuerRepository);
        // Act
        List<Thema> result = betreuerThemenAnzeigenService.execute(id);

        // Assert
        assertThat(result).hasSize(1).contains(thema);
    }

    @Test
    @DisplayName("Sollte zwei Themen liefern, wenn Betreuer existiert und 2 Themen hat")
    void test4() {
        // Arrange
        String id = "b-2";
        Thema t1 = createThema(1L, id);
        Thema t2 = createThema(2L, id);
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        ThemaRepository themaRepository = mock(ThemaRepository.class);

        BetreuerThemenAnzeigenService betreuerThemenAnzeigenService = new BetreuerThemenAnzeigenService(themaRepository,betreuerRepository);
        when(betreuerRepository.existsByGithubId(id)).thenReturn(true);
        when(themaRepository.findAllByBetreuerId(id)).thenReturn(List.of(t1, t2));

        // Act
        List<Thema> result = betreuerThemenAnzeigenService.execute(id);

        // Assert
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(t1, t2);
    }

}
