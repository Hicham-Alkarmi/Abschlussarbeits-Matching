package com.ThesisIsComing.iihk_89.domain.betreuer;

import java.util.Objects;

public class Raum {

    private final String raumNr;
    private static final String RAUMNR_REGEX="^(0[1-9]|[1-9][0-9])\\.(0[1-9]|[1-9][0-9])\\.(0[1-9]|[1-9][0-9])\\.(0[1-9]|[1-9][0-9])$";

    public Raum(String raumNr){
        if (raumNr == null) throw new IllegalArgumentException("RaumNr darf nicht null sein");
        if (!raumNr.matches(RAUMNR_REGEX)) {
            throw new IllegalArgumentException("Ungültige RaumNR: " + raumNr);
        }
        this.raumNr = raumNr;
    }

    public String getRaumNr() {
        return raumNr;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Raum raum = (Raum) o;
        return Objects.equals(raumNr, raum.raumNr);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(raumNr);
    }
}
