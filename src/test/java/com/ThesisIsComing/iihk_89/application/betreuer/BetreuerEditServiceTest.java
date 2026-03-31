package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class BetreuerEditServiceTest {

    private BetreuerEditDTO dto(){
        return  new BetreuerEditDTO("max","muster","mo@hhu.de",
                "90.90.90.90", "mathe ", "hhtp:/","hhu");
    }
    private Betreuer betreuer (String  githubId){
        return new Betreuer(
                1L, githubId,
                new Name("Vorher", "Name"),
                new Kontaktinfo(new Raum("20.20.20.20"), new Email("alt@hhu.de"))
        );
    }
    @Test
    @DisplayName("betreuer editiert sein profil")
    void test1(){
        // arrange
        String githubId = "123";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);

        Betreuer testBetreuer = betreuer(githubId);
        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.of(testBetreuer));

        BetreuerEditService betreuerEditService = new BetreuerEditService(betreuerRepository);

        // act
        betreuerEditService.execute(githubId, dto());

        // assert
        verify(betreuerRepository).save(testBetreuer);
    }

    @Test
    @DisplayName(" Betreuer existiert nicht")
    void test2() {
        // arrange
        String githubId = "unbekannt";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);

        when(betreuerRepository.findByGithubId(githubId)).thenReturn(Optional.empty());

        BetreuerEditService betreuerEditService = new BetreuerEditService(betreuerRepository);

        // act & assert
        assertThrows(RuntimeException.class, () -> {
            betreuerEditService.execute(githubId, dto());
        });

        verify(betreuerRepository, never()).save(any());
    }
}
