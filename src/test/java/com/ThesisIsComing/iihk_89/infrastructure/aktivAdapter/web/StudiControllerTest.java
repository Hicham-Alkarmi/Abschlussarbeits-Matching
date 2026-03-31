package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.betreuer.AlleBetreuerAnzeigenService;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerRoleService;
import com.ThesisIsComing.iihk_89.application.matching.MatchingAnfrageDTO;
import com.ThesisIsComing.iihk_89.application.matching.MatchingService;
import com.ThesisIsComing.iihk_89.application.thema.AlleThemenAnzeigenService;
import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StudiController.class)
//@Import({SecurityConfig.class, CustomOAuth2UserService.class})
@AutoConfigureMockMvc(addFilters = false)
public class StudiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MatchingService matchingService;

    @MockBean
    AlleBetreuerAnzeigenService alleBetreuerAnzeigenService;

    @MockBean
    AlleThemenAnzeigenService alleThemenAnzeigenService;
    @MockBean
    private BetreuerRoleService betreuerRoleService;

    private Betreuer createBetreuer(Long id) {
        return new Betreuer(
                id,
                "123",
                new Name("mo", "muster"),
                new Kontaktinfo(
                        new Raum("24.12.01.73"),
                        new Email("mo@hhu.de")
                )
        );
    }
    private Thema createThema(Long id){
        return new Thema(id,"x","y","z",
                List.of(),List.of(),List.of(), List.of());
    }

    @Test
    @DisplayName("aus der db werden 2 fachgebiete geholt")
    public void test1() throws Exception{
        when(matchingService.alleFachgebiete()).thenReturn(Set.of("java, python"));


        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk())
                .andExpect(view().name("Matching"))
                .andExpect(model().attribute("matchingAnfragenDTO", new MatchingAnfrageDTO("","")))
                .andExpect(model().attribute("alleFachgebiete", Set.of("java, python")));
    }

    @Test
    @DisplayName("es gibt 2 Betreuer und diese werden aus der db geholt ")
    public void test2() throws  Exception{
        Betreuer b1 = createBetreuer(1L);
        Betreuer b2 = createBetreuer(2L);
        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of(b1,b2));

        mockMvc.perform(get("/alle-betreuer"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("betreuer",List.of(b1,b2)))
                .andExpect(view().name("AlleBetreuerSeite"));
    }

    @Test
    @DisplayName("es gibt keine Betreuer")
    public void test3() throws  Exception{
        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of());

        mockMvc.perform(get("/alle-betreuer"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("betreuer",List.of()))
                .andExpect(model().attribute("message","Noch keine Betreuer vorhanden"))
                .andExpect(view().name("AlleBetreuerSeite"));
    }

    @Test
    @DisplayName("es gibt zwei Themen")
    public void test4() throws  Exception{
        Thema t1 = createThema(1L);
        Thema t2 = createThema(2L);
        when(alleThemenAnzeigenService.execute()).thenReturn(List.of(t1,t2));

        Betreuer b1 = createBetreuer(1L);
        Betreuer b2 = createBetreuer(2L);
        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of(b1,b2));

        mockMvc.perform(get("/alle-themen"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("themen",List.of(t1,t2)))
                .andExpect(model().attribute("betreuer",List.of(b1,b2)))
                .andExpect(view().name("AlleThemenSeite"));
    }

    @Test
    @DisplayName("es gibt noch Themen")
    public void test5() throws  Exception{
        when(alleThemenAnzeigenService.execute()).thenReturn(List.of());

        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of());

        mockMvc.perform(get("/alle-themen"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("themen",List.of()))
                .andExpect(model().attribute("betreuer",List.of()))
                .andExpect(model().attribute("message","Noch keine Themen vorhanden"))
                .andExpect(view().name("AlleThemenSeite"));
    }
}
