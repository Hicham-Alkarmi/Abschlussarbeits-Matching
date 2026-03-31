package com.ThesisIsComing.iihk_89.application.betreuer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BetreuerAnlegenDTO (
        @NotBlank String githubId,
        @NotBlank String vorname,
        @NotBlank String nachname,
        @Email String  email,
        String raumNr
        ){
}
