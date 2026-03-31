package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BetreuerLoeschenServiceTest {

    private BetreuerRepository betreuerRepository;
    private ThemaRepository themaRepository;
    private BetreuerLoeschenService underTest;

    @BeforeEach
    void setUp() {
        betreuerRepository = mock(BetreuerRepository.class);
        themaRepository = mock(ThemaRepository.class);

        underTest = new BetreuerLoeschenService(betreuerRepository, themaRepository);
    }

    @Test
    @DisplayName("betreuer existiert und wurde inklusive seiner Themen erfolgreich geloescht")
    public void test1() {
        // Arrange
        String githubId = "1234";
        when(betreuerRepository.existsByGithubId(githubId)).thenReturn(true);


        Thema testThema = mock(Thema.class);
        when(testThema.getId()).thenReturn(99L);
        when(themaRepository.findAllByBetreuerId(githubId)).thenReturn(List.of(testThema));

        // Act
        underTest.execute(githubId);

        // Assert

        verify(themaRepository, times(1)).delete(99L);
        verify(betreuerRepository, times(1)).deleteByGithubId(githubId);
    }

    @Test
    @DisplayName("Exception da Betreuer nicht existiert")
    public void test2() {
        // Arrange
        String githubId = "1234";
        when(betreuerRepository.existsByGithubId(githubId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                underTest.execute(githubId));

        verify(betreuerRepository, never()).deleteByGithubId(githubId);
        verify(themaRepository, never()).findAllByBetreuerId(anyString());
    }
}
