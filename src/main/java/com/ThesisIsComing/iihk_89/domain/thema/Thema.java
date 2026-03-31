package com.ThesisIsComing.iihk_89.domain.thema;

import com.ThesisIsComing.iihk_89.RehydrationConstructor;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

// wir wollen spaeter Themen -> filtern , matchen , anzeigen
public class Thema {
    private Long id;
    private String titel;
    private String beschreibung = ""; //optional
    private Set<Fachgebiet> fachgebiete = new HashSet<>(); // optional
    private Set<Veranstaltung> empfohleneVeranstaltungen =new HashSet<>();// optional
    private Set<Link> links = new HashSet<>();// optional
    //private List<Datei> dateien = new ArrayList<>(); // optional vllt
    private String betreuerId;// gucken spaeter ob es passt
    private Set<String> dateiSystemNamen = new HashSet<>();

    public Thema(Long id, String titel, String betreuerId,String beschreibung,
                 Set<Fachgebiet> fachgebiete, Set<Veranstaltung> empfohleneVeranstaltungen,
                 Set<Link> links,Set<String> dateiSystemNamen){
        if(titel == null || titel.isBlank()){
            throw new IllegalArgumentException("Titel darf nicht leer sein ");
        }
        if (betreuerId == null)
            throw new IllegalArgumentException("BetreuerId darf nicht null sein");
        this.id = id;
        this.titel = titel;
        this.betreuerId = betreuerId;
        this.beschreibung=beschreibung;
        this.fachgebiete = fachgebiete;
        this.empfohleneVeranstaltungen= empfohleneVeranstaltungen;
        this.links=links;
        this.dateiSystemNamen = dateiSystemNamen;
        //this.dateien=dateien;
    }

    @RehydrationConstructor
    public Thema(Long id, String titel, String betreuerId,
                 String beschreibung,
                 List<Fachgebiet> fachgebiete,
                 List<Veranstaltung> empfohleneVeranstaltungen,
                 List<Link> links,
                 List<String> dateiSystemNamen) {
        if (titel == null || titel.isBlank())
            throw new IllegalArgumentException("Titel darf nicht leer sein");
        if (betreuerId == null)
            throw new IllegalArgumentException("BetreuerId darf nicht null sein");
        this.id = id;
        this.titel = titel;
        this.betreuerId = betreuerId;
        this.beschreibung = beschreibung != null ? beschreibung : "";
        this.fachgebiete.addAll(fachgebiete != null ? fachgebiete : List.of());
        this.empfohleneVeranstaltungen.addAll(empfohleneVeranstaltungen != null
                ? empfohleneVeranstaltungen : List.of());
        this.links.addAll(links != null ? links : List.of());
        this.dateiSystemNamen.addAll(dateiSystemNamen != null
                ? dateiSystemNamen : List.of());
    }


    public Long getId(){
        return id;
    }
    public String getTitel(){
        return titel;
    }
    public String getBeschreibung(){
        return beschreibung;
    }
    public List<Fachgebiet> getFachgebiete(){
        return  List.copyOf(fachgebiete);
    }
    public List<Veranstaltung> getEmpfohleneVeranstaltungen(){
        return List.copyOf(empfohleneVeranstaltungen);
    }
    public List<Link> getLinks(){
        return List.copyOf(links);
    }
    //public List<Datei> getDateien(){
     //   return List.copyOf(dateien);
   // }
    public String getBetreuerId() {
        return betreuerId;
    }

    public void updateTitel(String titel){
        if(titel == null){
            throw new IllegalArgumentException("Titel darf nicht null sein");
        }
        this.titel = titel;
    }
    public void updateBeschreibung(String beschreibung){
        if(beschreibung == null){
            throw new IllegalArgumentException("Beschreibung darf nicht null sein");
        }
        this.beschreibung = beschreibung;
    }

    public void dateiHinzufuegen(String dateiSystemName){
        if (dateiSystemName == null || dateiSystemName.isBlank())
            throw new IllegalArgumentException("DateiName fehlt");
        if (dateiSystemNamen.contains(dateiSystemName))
            throw new IllegalArgumentException("Datei bereits zugeordnet");
        dateiSystemNamen.add(dateiSystemName);
    }

    public void dateiEntfernen(String dateiSystemName){
        dateiSystemNamen.remove(dateiSystemName);
    }
    public Set<String> getDateiSystemNamen() {
        return Set.copyOf(dateiSystemNamen);
    }

    public void updateFachgebiete(String fachgebieteInput) {
        fachgebiete.clear();
        if (fachgebieteInput == null || fachgebieteInput.isBlank()){
            return;
        }
        Arrays.stream(fachgebieteInput.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Fachgebiet::new)
                .forEach(fachgebiete::add);
    }

    public void updateVeranstaltungen(String veranstaltungenInput) {
        empfohleneVeranstaltungen.clear();
        if (veranstaltungenInput == null || veranstaltungenInput.isBlank()) return;
        Arrays.stream(veranstaltungenInput.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Veranstaltung::new)
                .forEach(empfohleneVeranstaltungen::add);
    }

    public void updateLinks(String urlsInput, String texteInput) {
        links.clear();
        if (urlsInput == null || urlsInput.isBlank()) return;
        String[] urls = urlsInput.split(",");
        String[] texte = texteInput != null ? texteInput.split(",") : new String[0];
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i].trim();
            if (!url.isEmpty()) {
                String text = i < texte.length ? texte[i].trim() : url;
                links.add(new Link(URI.create(url), text));
            }
        }
    }

    public String getFachgebieteAlsString() {
        return fachgebiete.stream()
                .map(Fachgebiet::getFachgebiet)
                .collect(Collectors.joining(", "));
    }

    public String getVeranstaltungenAlsString() {
        return empfohleneVeranstaltungen.stream()
                .map(Veranstaltung::getVeranstaltung)
                .collect(Collectors.joining(", "));
    }

    public String getLinkUrlsAlsString() {
        return links.stream()
                .map(l -> l.getUrl().toString())
                .collect(Collectors.joining(", "));
    }

    public String getLinkTexteAlsString() {
        return links.stream()
                .map(Link::getAnzeigenderText)
                .collect(Collectors.joining(", "));
    }


}
