package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ThemaBearbeitenServiceTest {

    private ThemaRepository repository;
    private ThemaBearbeitenService underTest;

    @BeforeEach
    void setUp() {
        repository = mock(ThemaRepository.class);
        underTest = new ThemaBearbeitenService(repository);
    }

    @Test
    @DisplayName("Sollte abbrechen, wenn das Thema nicht in der DB existiert")
    void test1() {
        // Arrange
        Long id = 99L;
        ThemaDTO dto = new ThemaDTO("Titel", "Beschreibung", "b1", "Fach", "Vorlesung", "http://url", "Link");
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(id, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thema nicht gefunden");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Sollte Thema finden, Felder aktualisieren und dann speichern")
    void test2() {
        // Arrange
        Long id = 1L;
        Thema existierendesThema = new Thema(id, "Alt", "b1", "Alt", List.of(), List.of(), List.of(), List.of());

        ThemaDTO dto = new ThemaDTO(
                "Neuer Titel",
                "Neue Beschreibung",
                "b1",
                "Informatik",
                "Mathe",
                "https://link.de",
                "Weblink"
        );

        when(repository.findById(id)).thenReturn(Optional.of(existierendesThema));

        // Act
        underTest.execute(id, dto);

        // Assert
        verify(repository, times(1)).save(existierendesThema);

        assertThat(existierendesThema.getTitel()).isEqualTo("Neuer Titel");
        assertThat(existierendesThema.getBeschreibung()).isEqualTo("Neue Beschreibung");
    }
}