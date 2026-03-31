package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BetreuerProfilAnzeigenServiceTest {

    @Test
    @DisplayName("Sollte eine IllegalArgumentException werfen, wenn kein Betreuer gefunden wird")
    public void test1(){
        // arrange
        String githubId = "123";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.empty());

        BetreuerProfilAnzeigenService betreuerProfilAnzeigenService = new BetreuerProfilAnzeigenService(betreuerRepository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            betreuerProfilAnzeigenService.execute(githubId);
        });

        assertEquals("Betreuer nicht gefunden: 123", exception.getMessage());
        verify(betreuerRepository).findByGithubId(githubId);


    }

    @Test
    @DisplayName("Sollte den Betreuer zurückgeben, wenn die Github-ID existiert")
    public void test2_betreuerGefunden() {
        // arrange
        String githubId = "456";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);

        Betreuer testBetreuer = createBetreuer(githubId);

        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.of(testBetreuer));

        BetreuerProfilAnzeigenService service = new BetreuerProfilAnzeigenService(betreuerRepository);

        // act
        Betreuer result = service.execute(githubId);

        // assert
        assertNotNull(result);
        assertEquals(githubId, result.getGithubId());
        verify(betreuerRepository).findByGithubId(githubId);
    }

    private Betreuer createBetreuer(String githubId) {
        return new Betreuer(1l,githubId, new Name("max", "mustermann"),
                new Kontaktinfo(new Raum("20.20.20.20"), new Email("max@hhu.de")),
                Set.of(), Set.of(), Set.of());
    }


}
