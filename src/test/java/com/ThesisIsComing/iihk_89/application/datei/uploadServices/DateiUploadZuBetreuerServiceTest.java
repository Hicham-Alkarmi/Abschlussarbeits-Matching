package com.ThesisIsComing.iihk_89.application.datei.uploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import com.ThesisIsComing.iihk_89.application.Datei.DateiUploadDTO;
import com.ThesisIsComing.iihk_89.application.Datei.UploadServices.DateiUploadZuBetreuerService;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DateiUploadZuBetreuerServiceTest {

    private DateiRepository dateiRepository;
    private DateiStorage dateiStorage;
    private BetreuerRepository betreuerRepository;
    private DateiUploadZuBetreuerService underTest;

    @BeforeEach
    void setUp() {
        dateiRepository = mock(DateiRepository.class);
        dateiStorage = mock(DateiStorage.class);
        betreuerRepository = mock(BetreuerRepository.class);
        underTest = new DateiUploadZuBetreuerService(dateiRepository, dateiStorage, betreuerRepository);
    }

    @Test
    @DisplayName("Sollte abbrechen, wenn die Datei die 10MB Grenze überschreitet")
    void test1() {
        // Arrange
        DateiUploadDTO dto = new DateiUploadDTO(new byte[0], "test.pdf", 11L * 1024 * 1024, "b1", "T", "D");

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Datei zu gross");

        verifyNoInteractions(dateiStorage, dateiRepository);
    }

    @Test
    @DisplayName("Sollte abbrechen, wenn der Betreuer nicht existiert")
    void test2() {
        // Arrange
        DateiUploadDTO dto = new DateiUploadDTO(new byte[10], "test.pdf", 100L, "unbekannt", "T", "D");
        when(betreuerRepository.findByGithubId("unbekannt")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Betreuer nicht gefunden");
    }

    @Test
    @DisplayName("Sollte Datei speichern und dem Betreuer zuordnen")
    void test3() {
        // Arrange
        byte[] inhalt = "PDF-Inhalt".getBytes();
        DateiUploadDTO dto = new DateiUploadDTO(inhalt, "vorlesung.pdf", 100L, "b1", "Mathe", "Skript");

        Betreuer betreuer = mock(Betreuer.class);
        when(betreuerRepository.findByGithubId("b1")).thenReturn(Optional.of(betreuer));

        // Act
        underTest.execute(dto);

        // Assert
        verify(dateiStorage).speichern(eq(inhalt), anyString());


        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(dateiRepository).save(dateiCaptor.capture());
        assertThat(dateiCaptor.getValue().getOriginalName()).isEqualTo("vorlesung.pdf");


        verify(betreuer).dateiHinzufuegen(anyString());
        verify(betreuerRepository).save(betreuer);
    }
}