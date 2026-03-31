package com.ThesisIsComing.iihk_89.domain.shared;

import java.util.Objects;

public class Fachgebiet {
    private final String fachgebiet;

    public Fachgebiet(String fachgebiet){
        if(fachgebiet == null || fachgebiet.isBlank()){
            throw new IllegalArgumentException("Fachgebiet darf nicht leer sein");
        }
        this.fachgebiet = fachgebiet.trim().toLowerCase();
    }

    public String getFachgebiet(){
        return fachgebiet;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return  true;
        if (o == null || getClass() != o.getClass()) return false;
        Fachgebiet that = (Fachgebiet) o;
        return Objects.equals(fachgebiet, that.fachgebiet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fachgebiet);
    }
}
