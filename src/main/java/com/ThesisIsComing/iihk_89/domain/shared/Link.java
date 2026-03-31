package com.ThesisIsComing.iihk_89.domain.shared;

import java.net.URI;
import java.util.Objects;

public class Link {
    private final URI url;
    private final String anzeigenderText;

    public Link(URI url ,String anzeigenderText){
        if (url == null)
            throw new IllegalArgumentException("URL darf nicht null sein");
        if (anzeigenderText == null || anzeigenderText.isBlank())
            throw new IllegalArgumentException("Anzeigetext darf nicht leer sein");
        this.url=url;
        this.anzeigenderText = anzeigenderText;
    }

    public String getAnzeigenderText() {
        return anzeigenderText;
    }

    public URI getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, anzeigenderText);
    }
}
