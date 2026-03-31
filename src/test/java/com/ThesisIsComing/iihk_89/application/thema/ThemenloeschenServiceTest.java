package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ThemenloeschenServiceTest {

    private ThemaRepository repository;
    private ThemenloeschenService underTest;

    @BeforeEach
    void setUp() {
        repository = mock(ThemaRepository.class);
        underTest = new ThemenloeschenService(repository);
    }

    @Test
    @DisplayName("Sollte das Thema löschen, wenn es existiert")
    void test1() {
        // Arrange
        Long id = 1L;
        Thema thema = new Thema(id, "Titel", "123", "Beschreibung", List.of(), List.of(), List.of(), List.of());
        when(repository.findById(id)).thenReturn(Optional.of(thema));

        // Act
        underTest.execute(id);

        // Assert
        verify(repository, times(1)).delete(id);
    }

    @Test
    @DisplayName("Sollte Exception werfen und nicht löschen, wenn das Thema nicht existiert")
    void test2() {
        // Arrange
        Long id = 404L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Thema nicht gefunden");

        verify(repository, never()).delete(anyLong());
    }
}