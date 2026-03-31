package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class AlleBetreuerAnzeigenServiceTest {

    private Betreuer createBetreuer(String githubId){
        return  new Betreuer(
                1L ,
                githubId,
                new Name("max","mustermann"),
                new Kontaktinfo(new Raum("20.20.20.20"),new Email("max@hhu.de")),
                Set.of() , Set.of(), Set.of());

    }

    @Test
    @DisplayName("es gibt noch keine Betreuer")
    public void test1(){
        // arrange
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        when(betreuerRepository.findAll()).thenReturn(List.of());

        AlleBetreuerAnzeigenService alleBetreuerAnzeigenService = new AlleBetreuerAnzeigenService(betreuerRepository);

        // act
        List<Betreuer> result = alleBetreuerAnzeigenService.execute();
        // assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("es gibt einen Betreuer")
    public void test2(){
        // arrange
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        Betreuer b1 = createBetreuer("12");
        when(betreuerRepository.findAll()).thenReturn(List.of(b1));

        AlleBetreuerAnzeigenService alleBetreuerAnzeigenService = new AlleBetreuerAnzeigenService(betreuerRepository);

        // act
        List<Betreuer> result = alleBetreuerAnzeigenService.execute();

        // assert
        assertThat(result).containsExactly(b1);
    }
    @Test
    @DisplayName("es gibt zwei Betreuer")
    public void test3(){
        // arrange
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        Betreuer b1 = createBetreuer("12");
        Betreuer b2 = createBetreuer("34");
        when(betreuerRepository.findAll()).thenReturn(List.of(b1,b2));

        AlleBetreuerAnzeigenService alleBetreuerAnzeigenService = new AlleBetreuerAnzeigenService(betreuerRepository);

        // act
        List<Betreuer> result = alleBetreuerAnzeigenService.execute();

        // assert
        assertThat(result).containsExactly(b1,b2);
    }
}
