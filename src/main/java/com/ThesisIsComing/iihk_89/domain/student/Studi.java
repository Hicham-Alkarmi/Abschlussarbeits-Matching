package com.ThesisIsComing.iihk_89.domain.student;

import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.thema.Veranstaltung;

import java.util.ArrayList;
import java.util.List;

public class Studi {
    private final String githubId;
    private List<Fachgebiet> fachgebiete;
    private List<Veranstaltung> besuchteVeranstaltungen;

    public Studi(String githubId) {
        if (githubId == null || githubId.isBlank())
            throw new IllegalArgumentException("GithubId darf nicht leer sein");
        this.githubId = githubId;
        this.fachgebiete = new ArrayList<>();
        this.besuchteVeranstaltungen = new ArrayList<>();
    }
    public String getGithubId() {
        return githubId;
    }
    public List<Fachgebiet> getFachgebiet() {
        return List.copyOf(fachgebiete);
    }
    public List<Veranstaltung> getBesuchteVeranstaltungen() {
        return List.copyOf(besuchteVeranstaltungen);
    }
    public void addFachgebiet(Fachgebiet fachgebiet) {
        if(!fachgebiete.contains(fachgebiet)) {
            fachgebiete.add(fachgebiet);
        }
    }
    public void addBesuchteVeranstaltung(Veranstaltung veranstaltung) {
        if(!besuchteVeranstaltungen.contains(veranstaltung)) {
            besuchteVeranstaltungen.add(veranstaltung);
        }
    }
    public void removeFachgebiet(Fachgebiet fachgebiet){
            fachgebiete.remove(fachgebiet);
    }

    public void removeVeranstaltung(Veranstaltung veranstaltung){
            besuchteVeranstaltungen.remove(veranstaltung);
    }

}
