package com.ThesisIsComing.iihk_89.application.betreuer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BetreuerEditDTO(
    @NotBlank String vorname,
    @NotBlank String nachname,
    @NotBlank @Email String email,
    @NotBlank String raumNr,
    String fachgebiete,
    String url,
    String urlText
    )
{}
