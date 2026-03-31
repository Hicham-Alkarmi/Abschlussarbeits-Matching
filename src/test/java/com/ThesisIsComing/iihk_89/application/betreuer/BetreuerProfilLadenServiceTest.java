package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BetreuerProfilLadenServiceTest {

    private Betreuer createBetreuer(String githubId) {
        return new Betreuer(1l,githubId, new Name("max", "mustermann"),
                new Kontaktinfo(new Raum("20.20.20.20"), new Email("max@hhu.de")),
                Set.of(), Set.of(), Set.of());
    }

    @Test
    @DisplayName("Sollte Exception werfen, wenn der Betreuer nicht existiert")
    public void test1(){
        // arrange
        String githubId = "123";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.empty());

        BetreuerProfilLadenService betreuerProfilLadenService = new BetreuerProfilLadenService(betreuerRepository);
        //act & assert

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            betreuerProfilLadenService.execute(githubId);
        });

        assertEquals("Betreuer nicht gefunden", exception.getMessage());

    }

    @Test
    @DisplayName("Sollte Betreuer-Daten korrekt in BetreuerEditDTO mappen")
    public void test2(){
        // arrange
        String githubId = "234";
        BetreuerRepository betreuerRepository= mock(BetreuerRepository.class);
        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.of(createBetreuer(githubId)));

        BetreuerProfilLadenService betreuerProfilLadenService = new BetreuerProfilLadenService(betreuerRepository);
        // act
        BetreuerEditDTO editDto = betreuerProfilLadenService.execute(githubId);
        // assert
        assertNotNull(editDto);
        assertEquals("max", editDto.vorname());
        assertEquals("mustermann", editDto.nachname());
        assertEquals("max@hhu.de", editDto.email());
        assertEquals("20.20.20.20", editDto.raumNr());
        verify(betreuerRepository).findByGithubId(githubId);
    }
}
