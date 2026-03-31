package com.ThesisIsComing.iihk_89.application.datei.uploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiLoeschenService;
import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DateiLoeschenServiceTest {

    private DateiRepository dateiRepository;
    private DateiStorage dateiStorage;
    private DateiLoeschenService underTest;

    @BeforeEach
    void setUp() {
        dateiRepository = mock(DateiRepository.class);
        dateiStorage = mock(DateiStorage.class);
        underTest = new DateiLoeschenService(dateiRepository, dateiStorage);
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn die Datei gar nicht existiert")
    void test1_DateiNichtGefunden() {
        // Arrange
        String name = "ghost.pdf";
        when(dateiRepository.findByDateiSystemName(name)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(name, "user123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Datei nicht gefunden");

        verifyNoInteractions(dateiStorage);
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn ein fremder Nutzer löschen will")
    void test2_KeineBerechtigung() {
        // Arrange
        String name = "rezept.pdf";
        String echterUploader = "chef_koch";
        String angreifer = "hunger_gast";

        Datei datei = new Datei(1L, echterUploader, "Titel", LocalDateTime.now(), "D", name, "rezept.pdf");
        when(dateiRepository.findByDateiSystemName(name)).thenReturn(Optional.of(datei));

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(name, angreifer))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("keine Berechtigung");

        verify(dateiStorage, never()).loeschen(anyString());
        verify(dateiRepository, never()).deleteByDateiSystemName(anyString());
    }

    @Test
    @DisplayName("Sollte Datei aus Storage und Repo löschen, wenn alles passt")
    void test3() {
        // Arrange
        String name = "daten.zip";
        String uploader = "admin";

        Datei datei = new Datei(1L, uploader, "Titel", LocalDateTime.now(), "D", name, "daten.zip");
        when(dateiRepository.findByDateiSystemName(name)).thenReturn(Optional.of(datei));

        // Act
        underTest.execute(name, uploader);

        // Assert
        verify(dateiStorage).loeschen(name);
        verify(dateiRepository).deleteByDateiSystemName(name);
    }
}