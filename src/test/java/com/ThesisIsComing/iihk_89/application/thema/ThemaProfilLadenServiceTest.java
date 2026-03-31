package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ThemaProfilLadenServiceTest {

    private ThemaRepository repository;
    private ThemaProfilLadenService underTest;

    @BeforeEach
    void setUp() {
        repository = mock(ThemaRepository.class);
        underTest = new ThemaProfilLadenService(repository);
    }

    @Test
    @DisplayName("Sollte ein ThemaDTO korrekt befüllen, wenn die ID existiert")
    void test1() {
        // Arrange
        Long id = 1L;
        Thema thema = new Thema(id, "Test Titel", "123", "Beschreibung",
                List.of(), List.of(), List.of(), List.of());

        when(repository.findById(id)).thenReturn(Optional.of(thema));

        // Act
        ThemaDTO result = underTest.execute(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.titel()).isEqualTo("Test Titel");
        assertThat(result.beschreibung()).isEqualTo("Beschreibung");
        assertThat(result.betreuerId()).isEqualTo("123");
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn das Thema nicht existiert")
    void test2() {
        // Arrange
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thema nicht gefunden")
                .hasMessageContaining("999");
    }
}