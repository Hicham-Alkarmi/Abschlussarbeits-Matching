package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;


import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerRoleService;
import com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity.CustomOAuth2UserService;
import com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Import({SecurityConfig.class, CustomOAuth2UserService.class})
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BetreuerRoleService betreuerRoleService;

    @Test
    @DisplayName("Startseite ist erreichbar")
    public void test1()throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Startseite"));
    }
}
