package com.ThesisIsComing.iihk_89.application.datei.uploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import com.ThesisIsComing.iihk_89.application.Datei.DateiUploadDTO;
import com.ThesisIsComing.iihk_89.application.Datei.UploadServices.DateiUploadZuThemaService;
import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DateiUploadZuThemaServiceTest {

    private DateiStorage dateiStorage;
    private DateiRepository dateiRepository;
    private ThemaRepository themaRepository;
    private DateiUploadZuThemaService underTest;

    @BeforeEach
    void setUp() {
        dateiStorage = mock(DateiStorage.class);
        dateiRepository = mock(DateiRepository.class);
        themaRepository = mock(ThemaRepository.class);
        underTest = new DateiUploadZuThemaService(dateiStorage, dateiRepository, themaRepository);
    }

    @Test
    @DisplayName("Sollte Fehler werfen, wenn die Datei > 10MB ist")
    void test1() {
        // Arrange
        DateiUploadDTO dto = new DateiUploadDTO(new byte[0], "test.pdf", 15L * 1024 * 1024, "b1", "T", "D");

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(dto, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Datei zu gross");

        verifyNoInteractions(dateiStorage, dateiRepository);
    }

    @Test
    @DisplayName("Sollte Fehler werfen, wenn das Thema nicht existiert")
    void test2() {
        // Arrange
        Long themaId = 99L;
        DateiUploadDTO dto = new DateiUploadDTO(new byte[10], "test.pdf", 100L, "b1", "T", "D");
        when(themaRepository.findById(themaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.execute(dto, themaId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Thema nicht gefunden");
    }

    @Test
    @DisplayName("Sollte Datei speichern, im Repo ablegen und dem Thema zuordnen")
    void test3() {
        // Arrange
        Long themaId = 1L;
        byte[] inhalt = "Mein Inhalt".getBytes();
        DateiUploadDTO dto = new DateiUploadDTO(inhalt, "arbeit.pdf", 500L, "betreuer-7", "Titel", "Beschreibung");

        Thema thema = mock(Thema.class);
        when(themaRepository.findById(themaId)).thenReturn(Optional.of(thema));

        // Act
        underTest.execute(dto, themaId);

        // Assert

        verify(dateiStorage).speichern(eq(inhalt), anyString());


        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(dateiRepository).save(dateiCaptor.capture());
        assertThat(dateiCaptor.getValue().getOriginalName()).isEqualTo("arbeit.pdf");


        verify(thema).dateiHinzufuegen(anyString());
        verify(themaRepository).save(thema);
    }
}