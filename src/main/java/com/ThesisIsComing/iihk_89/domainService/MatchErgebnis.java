package com.ThesisIsComing.iihk_89.domainService;

import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;

public class MatchErgebnis implements Comparable<MatchErgebnis> {

    private final Thema thema;
    private final Betreuer betreuer;
    private final int score;

    public MatchErgebnis(Thema thema, Betreuer betreuer, int score) {
        this.thema = thema;
        this.betreuer = betreuer;
        this.score = score;
    }

    public boolean istThema()    { return thema != null; }
    public boolean istBetreuer() { return betreuer != null; }
    public Thema getThema()      { return thema; }
    public Betreuer getBetreuer(){ return betreuer; }
    public int getScore()        { return score; }

    @Override
    public int compareTo(MatchErgebnis other) {
        return Integer.compare(other.score, this.score);
    }
}
