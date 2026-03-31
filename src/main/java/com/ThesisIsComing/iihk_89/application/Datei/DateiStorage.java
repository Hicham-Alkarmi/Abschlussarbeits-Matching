package com.ThesisIsComing.iihk_89.application.Datei;

public interface DateiStorage {

    void speichern(byte[] inhalt, String dateiSystemName);
    byte[] laden(String dateiSystemName);
    void loeschen(String dateiSystemName);
}