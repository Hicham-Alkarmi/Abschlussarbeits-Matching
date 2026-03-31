package com.ThesisIsComing.iihk_89.domain.thema;

import java.util.Objects;

public class Veranstaltung {
    private final String veranstaltung;

    public Veranstaltung(String veranstaltung){
        if (veranstaltung == null || veranstaltung.isBlank())
            throw new IllegalArgumentException("Veranstaltung darf nicht leer sein");
        this.veranstaltung = veranstaltung.trim();
    }

    public String getVeranstaltung() {
        return veranstaltung;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Veranstaltung that = (Veranstaltung) o;
        return Objects.equals(veranstaltung, that.veranstaltung);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(veranstaltung);
    }
}
