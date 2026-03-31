package com.ThesisIsComing.iihk_89.domain.thema;

import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;

import java.util.ArrayList;
import java.util.List;

public class ThemaBuilder {
    private Long id;
    private String titel;
    private String beschreibung = "";
    private List<Fachgebiet> fachgebiete = new ArrayList<>();
    private List<Veranstaltung> veranstaltungen = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    private List<String> dateiSystemNamen = new ArrayList<>();
    private String betreuerId;

    public ThemaBuilder mitId(Long id){
        this.id=id;
        return this;
    }
    public ThemaBuilder mitTitel(String titel){
        this.titel=titel;
        return this;
    }
    public ThemaBuilder mitBeschreibung(String beschreibung){
        this.beschreibung=beschreibung;
        return this;
    }
    public ThemaBuilder mitBetreuerId(String betreuerId){
        this.betreuerId=betreuerId;
        return this;
    }
    public ThemaBuilder mitFachgebiet(Fachgebiet fachgebiet){
        this.fachgebiete.add(fachgebiet);
        return this;
    }
    public ThemaBuilder mitVeranstaltung(Veranstaltung veranstaltung){
        this.veranstaltungen.add(veranstaltung);
        return this;
    }
    public ThemaBuilder mitLinks(Link link){
        this.links.add(link);
        return this;
    }
    public ThemaBuilder mitDatei(String dateiSystemName) {
        this.dateiSystemNamen.add(dateiSystemName);
        return this;
    }

    public Thema build(){
        if(titel == null || titel.isBlank())throw  new IllegalStateException("Titel darf nicht leer sein");
        if(betreuerId == null) throw new IllegalStateException("BetreuerId darf nicht leer sein");

        return  new Thema(id,titel, betreuerId,beschreibung,
                          fachgebiete, veranstaltungen, links,dateiSystemNamen);
    }




}
