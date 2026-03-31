package com.ThesisIsComing.iihk_89.domain.betreuer;

import com.ThesisIsComing.iihk_89.RehydrationConstructor;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;


import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class Betreuer {
    private Long id;
    private final String githubId;
    private  Name name;
    private   Kontaktinfo kontaktinfo;
    private final Set<Link> links = new HashSet<>();
    private final Set<Fachgebiet> fachgebiete = new HashSet<>();
    private  final Set<String> dateiSystemNamen = new HashSet<>();

    public Betreuer(Long id,String githubId, Name name , Kontaktinfo kontaktinfo){
        if (githubId == null || githubId.isBlank())
            throw new IllegalArgumentException("GithubId darf nicht null sein");
        if (name == null) throw new IllegalArgumentException("Name darf nicht null sein");
        if (kontaktinfo == null) throw new IllegalArgumentException("Kontaktinfo darf nicht null sein");
        this.id=id;
        this.githubId = githubId;
        this.name = name;
        this.kontaktinfo = kontaktinfo;
    }
    //
    @RehydrationConstructor
    public Betreuer(Long id, String githubId, Name name, Kontaktinfo kontaktinfo,
            Set<Link> links, Set<Fachgebiet> fachgebiete, Set<String> dateiSystemNamen ){
        this.id=id;
        this.githubId=githubId;
        this.name=name;
        this.kontaktinfo=kontaktinfo;
        this.links.addAll(links);
        this.fachgebiete.addAll(fachgebiete);
        this.dateiSystemNamen.addAll(dateiSystemNamen);
    }

    public Long getId(){
        return id;
    }
    public String getGithubId() {
        return githubId;
    }
    public Name getName(){
        return name;
    }
    public Kontaktinfo getKontaktinfo(){
        return kontaktinfo;
    }
    public Set<Link> getLinks(){
        return Set.copyOf(links);
    }
    public Set<Fachgebiet> getFachgebiete(){
        return Set.copyOf(fachgebiete);
    }
    public void updateName(Name name){
        if(name == null){
            throw new IllegalArgumentException("Name darf nicht null sein");
        }
        this.name = name;
    }
    public void updateKontaktinfo(Kontaktinfo kontaktinfo){
        if(kontaktinfo == null){
            throw new IllegalArgumentException("Kontaktinfo darf nicht null sein ");
        }
        this.kontaktinfo = kontaktinfo;
    }

    public void dateiHinzufuegen(String dateiSystemName){
        if (dateiSystemName == null || dateiSystemName.isBlank())
            throw new IllegalArgumentException("DateiName fehlt");
        if (dateiSystemNamen.contains(dateiSystemName))
            throw new IllegalArgumentException("Datei bereits zugeordnet");
        dateiSystemNamen.add(dateiSystemName);
    }
    public void dateiEntfernen(String dateiSystemName){
        if(!dateiSystemNamen.remove(dateiSystemName)){
            throw new IllegalArgumentException("Datei nicht vorhanden");
        }
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
