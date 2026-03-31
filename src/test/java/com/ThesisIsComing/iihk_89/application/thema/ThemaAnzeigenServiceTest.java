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

public class ThemaAnzeigenServiceTest {

    private ThemaRepository repository;
    private ThemaAnzeigenService underTest;

    @BeforeEach
    void setUp() {
        repository = mock(ThemaRepository.class);
        underTest = new ThemaAnzeigenService(repository);
    }


    private Thema createThema(Long id) {
        return new Thema(id, "Titel", "1", "Beschreibung",
                List.of(), List.of(), List.of(), List.of());
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die ID nicht existiert")
    void test1() {
        // Arrange
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thema nicht gefunden")
                .hasMessageContaining(id.toString());
    }

    @Test
    @DisplayName("Sollte das Thema zurückgeben, wenn die ID existiert")
    void test2() {
        // Arrange
        Long id = 42L;
        Thema erwartetesThema = createThema(id);
        when(repository.findById(id)).thenReturn(Optional.of(erwartetesThema));

        // Act
        Thema result = underTest.execute(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result).isEqualTo(erwartetesThema);
    }
}
