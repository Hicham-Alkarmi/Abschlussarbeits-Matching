package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.betreuer.*;
import com.ThesisIsComing.iihk_89.domain.betreuer.*;


import com.ThesisIsComing.iihk_89.helper.WithMockOAuth2User;
import com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity.CustomOAuth2UserService;
import com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, CustomOAuth2UserService.class})
public class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AlleBetreuerAnzeigenService alleBetreuerAnzeigenService;
    @MockBean
    BetreuerLoeschenService betreuerLoeschenService;
    @MockBean
    BetreuerAnlegenService betreuerAnlegenService;
    @MockBean
    private BetreuerRoleService betreuerRoleService;

    private Betreuer erstelleBetreuer(String githubId) {
        return new Betreuer(
                null,
                githubId,
                new Name("mo", "muster"),
                new Kontaktinfo(
                        new Raum("24.12.01.73"),
                        new Email("mo@hhu.de")
                )
        );
    }
    private BetreuerAnlegenDTO erstelleBetreuerAnlegenDTO(){
        return new BetreuerAnlegenDTO("1L","max","mustermann","mm@hhu.de","10.20.20.20");
    }


    @Test
    @DisplayName("es gibt 2 Betreuer")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test1()throws Exception {
        // Arrange
        Betreuer b1 = erstelleBetreuer("12");
        Betreuer b2 = erstelleBetreuer("34");
        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of(b1,b2));
        // act
        mockMvc.perform(get("/admin/alle-betreuer"))
                .andExpect(status().isOk())
                .andExpect(view().name("alleBetreuer"))
                .andExpect(model().attribute("betreuer",List.of(b1,b2)));
        verify(alleBetreuerAnzeigenService).execute();

    }
    @Test
    @DisplayName("Keine Betreuer also -> message")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test2() throws Exception {
        // arrange
        when(alleBetreuerAnzeigenService.execute()).thenReturn(List.of());
        //act
        mockMvc.perform(get("/admin/alle-betreuer"))
                .andExpect(status().isOk())
                .andExpect(view().name("alleBetreuer"))
                .andExpect(model().attribute("message", "es gibt noch keine Betreuer"));
        // assert
        System.out.println("test2");
        verify(alleBetreuerAnzeigenService).execute();
    }
    @Test
    @DisplayName("form angeben ")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test3() throws Exception{
        BetreuerAnlegenDTO betreuerDto = new  BetreuerAnlegenDTO("","","","","");
        mockMvc.perform(get("/admin/betreuer-anlegen"))
                .andExpect(status().isOk())
                .andExpect(view().name("betreuerForm"))
                .andExpect(model().attribute("betreuerDto" ,betreuerDto ));
    }

    @Test
    @DisplayName("Betreuer erfolgreich anlegen")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test4() throws Exception{
        
        mockMvc.perform(post("/admin/betreuer-anlegen")
                .param("githubId", "1L")
                .param("vorname", "max")
                .param("nachname", "mustermann")
                .param("email", "mm@hhu.de")
                .param("raumNr","10.20.20.20")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/alle-betreuer"));
        verify(betreuerAnlegenService).execute(erstelleBetreuerAnlegenDTO());
    }
    @Test
    @DisplayName("Validation error da githubId fehlt")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test5() throws Exception{

        mockMvc.perform(post("/admin/betreuer-anlegen")
                .param("githubId", "")
                .param("vorname", "max")
                .param("nachname", "mustermann")
                .param("email", "mm@hhu.de")
                .param("raumNr","24.12.01.73")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("betreuerForm"));

    }
    @Test
    @DisplayName("fehler vom System")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test6() throws Exception{
        doThrow(new IllegalArgumentException("Betreuer existiert bereits!"))
                .when(betreuerAnlegenService).execute(any());

        mockMvc.perform(post("/admin/betreuer-anlegen")
                .param("githubId", "1L")
                .param("vorname", "max")
                .param("nachname", "mustermann")
                .param("email", "mm@hhu.de")
                .param("raumNr","24.12.01.73")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("betreuerForm"))
                .andExpect(model().attribute("fehler", "Betreuer existiert bereits!"));

    }

    @Test
    @DisplayName("Betreuer wurde geloescht")
    @WithMockOAuth2User(roles={"ADMIN"})
    public void test7() throws Exception{

        mockMvc.perform(post("/admin/betreuer-loeschen/{githubId}", "L1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/alle-betreuer"));

        verify(betreuerLoeschenService).execute("L1");

    }

    //@Test
    // Redirects prüfen und Das gerenderte HTML prüfe(o geht's: Du beendest das mvc.perform(...) mit .andReturn() und ziehst dir den HTML-String heraus, um ihn mit AssertJ zu prüfen)
  //  @DisplayName("CSRF bei Post nicht vergessen , bad rewquest und parameter")

}


