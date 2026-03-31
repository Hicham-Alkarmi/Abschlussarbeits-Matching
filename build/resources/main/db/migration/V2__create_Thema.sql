CREATE TABLE IF NOT EXISTS "thema"
(
    id BIGSERIAL PRIMARY KEY,
    titel VARCHAR(255)  NOT NULL,
    beschreibung VARCHAR(255),
    betreuer_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "thema_fachgebiete"
(
    thema_id BIGINT NOT NULL REFERENCES thema(id),
    fachgebiet  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "thema_veranstaltungen"
(
    thema_id BIGINT NOT NULL REFERENCES thema(id),
    veranstaltung VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "thema_links"
(
    thema_id BIGINT NOT NULL REFERENCES thema (id),
    url    VARCHAR(255) NOT NULL,
    anzeigender_text VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS "thema_datei_system_name"
(
    thema_id BIGINT NOT NULL REFERENCES thema (id),
    datei_system_name VARCHAR(255) NOT NULL
);
