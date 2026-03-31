package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BetreuerAnlegenServiceTest {
    @DisplayName("Betreuer erfolgreich anlegen")
    @Test
    void test1(){
        // arrnage
        String githubId = "1234";
        BetreuerRepository repo = mock(BetreuerRepository.class);
        when(repo.existsByGithubId(githubId)).thenReturn(false);

        BetreuerAnlegenService service = new BetreuerAnlegenService(repo);
        BetreuerAnlegenDTO dto = new BetreuerAnlegenDTO(
                "1234", "max", "musmann", "max@hhu.de","25.01.02.03"
        );

        // act
        service.execute(dto);

        // assert
        verify(repo,times(1)).save(any(Betreuer.class));
    }

    @DisplayName("Doppelter Betreuer wirft Exception")
    @Test
    void test2(){
        // arrange
        String githubId = "1234";
        BetreuerRepository repo = mock(BetreuerRepository.class);
        when(repo.existsByGithubId(githubId)).thenReturn(true);

        BetreuerAnlegenService service = new BetreuerAnlegenService(repo);
        BetreuerAnlegenDTO dto = new BetreuerAnlegenDTO(
                "1234", "max", "musmann", "max@hhu.de","25.01.02.03"
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.execute(dto)
        );

        verify(repo , times(0)).save(any());
    }


}
