package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class AlleThemenAnzeigenServiceTest {

    private Thema createThema(Long id){
        return  new Thema(id ,"x","123","z",
                List.of(), List.of(), List.of(), List.of());
    }

    @Test
    @DisplayName("es gibt noch keine Themen")
    public void test1(){
        // arrange
        ThemaRepository themaRepository = mock(ThemaRepository.class);
        when(themaRepository.findAll()).thenReturn(List.of());

        AlleThemenAnzeigenService alleThemenAnzeigenService = new AlleThemenAnzeigenService(themaRepository);

        // act
        List<Thema> result = alleThemenAnzeigenService.execute();
        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("2 Themen werden angezeit")
    public void test2(){
        // arrange
        Thema t1 = createThema(1L);
        Thema t2 = createThema(2L);
        ThemaRepository themaRepository = mock(ThemaRepository.class);
        when(themaRepository.findAll()).thenReturn(List.of(t1 , t2));

        AlleThemenAnzeigenService alleThemenAnzeigenService = new AlleThemenAnzeigenService(themaRepository);
        // act
        List<Thema> result = alleThemenAnzeigenService.execute();

        // assert
        assertThat(result)
                .hasSize(2)
                .containsExactly(t1, t2);
    }
}
