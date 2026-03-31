CREATE TABLE IF NOT EXISTS "betreuer"
(
    id        BIGSERIAL PRIMARY KEY ,
    github_id VARCHAR(255) NOT NULL UNIQUE,
    vorname   VARCHAR(255) NOT NULL,
    nachname  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    raum_nr   VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS betreuer_fachgebiete
(
    betreuer_id BIGINT NOT NULL REFERENCES "betreuer" (id),
    fachgebiet  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS betreuer_links
(
    betreuer_id     BIGINT       NOT NULL REFERENCES "betreuer" (id),
    url             VARCHAR(255) NOT NULL,
    anzeigender_text VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS betreuer_datei_system_namen
(
    betreuer_id      BIGINT       NOT NULL REFERENCES "betreuer" (id),
    datei_system_name VARCHAR(255) NOT NULL
);