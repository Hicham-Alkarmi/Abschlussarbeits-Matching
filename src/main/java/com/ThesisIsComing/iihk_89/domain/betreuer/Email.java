package com.ThesisIsComing.iihk_89.domain.betreuer;

import java.util.Objects;

public class Email {
    private final String email;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Email(String email){
        if (email == null)
            throw new IllegalArgumentException("Email darf nicht null sein");
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Ungültige E-Mail: " + email);
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
