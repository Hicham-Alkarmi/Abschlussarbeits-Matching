package com.ThesisIsComing.iihk_89.domain.betreuer;

import java.util.Objects;

public class Kontaktinfo {
    private final Raum raum;
    private  final Email email;

    public Kontaktinfo(Raum raum , Email email){
        if (raum == null) throw new IllegalArgumentException("Raum darf nicht null sein");
        if (email == null) throw new IllegalArgumentException("Email darf nicht null sein");
        this.raum = raum;
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public Raum getRaum() {
        return raum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Kontaktinfo that = (Kontaktinfo) o;
        return Objects.equals(raum, that.raum) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raum, email);
    }
}
