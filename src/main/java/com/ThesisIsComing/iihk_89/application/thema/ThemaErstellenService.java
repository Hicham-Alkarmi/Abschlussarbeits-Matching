package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domain.thema.ThemaBuilder;
import com.ThesisIsComing.iihk_89.domain.thema.Veranstaltung;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ThemaErstellenService {
    private final ThemaRepository themaRepository;
    private final BetreuerRepository betreuerRepository;

    public ThemaErstellenService(ThemaRepository themaRepository,
                                 BetreuerRepository betreuerRepository){
        this.themaRepository=themaRepository;
        this.betreuerRepository=betreuerRepository;
    }
    public void execute(ThemaDTO dto){
        if(!betreuerRepository.existsByGithubId(dto.betreuerId())){
            throw new IllegalArgumentException("Betreuer mit Id "+ dto.betreuerId() +"existiert nicht");
        }

        ThemaBuilder builder = new ThemaBuilder()
                .mitBetreuerId(dto.betreuerId())
                .mitTitel(dto.titel())
                .mitBeschreibung(dto.beschreibung());

        if(dto.fachgebiete() != null && !dto.fachgebiete().isBlank()){
            for(String f : dto.fachgebiete().split(",")){
                builder.mitFachgebiet(new Fachgebiet(f.trim()));
            }
        }

        if (dto.empfohleneVeranstaltungen() != null && !dto.empfohleneVeranstaltungen().isBlank()) {
            for (String v : dto.empfohleneVeranstaltungen().split(",")) {
                builder.mitVeranstaltung(new Veranstaltung(v.trim()));
            }
        }

        if (dto.linkUri() != null && !dto.linkUri().isBlank()) {
            String[] urls = dto.linkUri().split(",");
            String[] texte = dto.linkText().split(",");
            for (int i = 0; i < urls.length; i++) {
                String url = urls[i].trim();
                String text = i < texte.length ? texte[i].trim() : url;
                builder.mitLinks(new Link(URI.create(url), text));
            }
        }

        Thema thema =builder.build();

        themaRepository.save(thema);
    }
}
