package com.ThesisIsComing.iihk_89.domain.betreuer;

import java.util.Objects;

public class Name {
    private final String vorname;
    private final String nachname;

    public Name(String vorname, String nachname){
        if (vorname == null || vorname.isBlank())
            throw new IllegalArgumentException("Vorname darf nicht leer sein");
        if (nachname == null || nachname.isBlank())
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        this.vorname = vorname.trim();
        this.nachname = nachname.trim();
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(vorname, name.vorname) && Objects.equals(nachname, name.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorname, nachname);
    }
}
