package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BetreuerRoleServiceTest {

    @Test
    @DisplayName("Benutzer ist tatsaechlich ein Betreuer -> True")
    public void test1(){
        // arrange
        String githubId = "1234";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        when(betreuerRepository.existsByGithubId(githubId)).thenReturn(true);
        BetreuerRoleService betreuerRoleService = new BetreuerRoleService(betreuerRepository);
        // act
        boolean result = betreuerRoleService.execute(githubId);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Benutzer ist kein Betreuer  ->")
    public void test2(){
        // arrange
        String githubId = "1234";
        BetreuerRepository betreuerRepository = mock(BetreuerRepository.class);
        when(betreuerRepository.existsByGithubId(githubId)).thenReturn(false);
        BetreuerRoleService betreuerRoleService = new BetreuerRoleService(betreuerRepository);
        // act
        boolean result = betreuerRoleService.execute(githubId);
        //assert
        assertThat(result).isFalse();
    }
}
