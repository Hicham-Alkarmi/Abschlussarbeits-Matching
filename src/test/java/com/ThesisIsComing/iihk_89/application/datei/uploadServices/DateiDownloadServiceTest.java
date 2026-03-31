package com.ThesisIsComing.iihk_89.application.datei.uploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiDownloadService;
import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DateiDownloadServiceTest {

    private DateiRepository dateiRepository;
    private DateiStorage dateiStorage;
    private DateiDownloadService underTest;

    @BeforeEach
    void setUp() {
        dateiRepository = mock(DateiRepository.class);
        dateiStorage = mock(DateiStorage.class);
        underTest = new DateiDownloadService(dateiRepository, dateiStorage);
    }

    @Test
    @DisplayName("Sollte Datei Inhalt zurückgeben, wenn die Datei existiert")
    void test1() {
        // Arrange
        String dateiName = "uuid-123.pdf";
        byte[] erwarteterInhalt = "PDF-Daten".getBytes();

        when(dateiRepository.existsByDateiSystemName(dateiName)).thenReturn(true);
        when(dateiStorage.laden(dateiName)).thenReturn(erwarteterInhalt);

        // Act
        byte[] result = underTest.execute(dateiName);

        // Assert
        assertThat(result).isEqualTo(erwarteterInhalt);
        verify(dateiStorage).laden(dateiName);
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn die Datei nicht im Repository gelistet ist")
    void test2() {
        // Arrange
        String dateiName = "unbekannt.zip";
        when(dateiRepository.existsByDateiSystemName(dateiName)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(dateiName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Datei nicht gefunden");

        verifyNoInteractions(dateiStorage);
    }
}