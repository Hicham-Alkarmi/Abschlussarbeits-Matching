package com.ThesisIsComing.iihk_89.application.Datei;

import jakarta.validation.constraints.NotBlank;

public record FormDTO(
        @NotBlank String titel,
        String beschreibung
) {}