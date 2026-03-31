package com.ThesisIsComing.iihk_89.application.Datei;

public record DateiUploadDTO(
        byte[] inhalt,
        String originalName,
        long groesse,
        String betreuerId,
        String titel,
        String beschreibung
) {}
