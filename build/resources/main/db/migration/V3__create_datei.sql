CREATE TABLE IF NOT EXISTS "datei"
(
    id BIGSERIAL PRIMARY KEY,
    datei_system_name VARCHAR(255) NOT NULL ,
    uploader_id VARCHAR(255) NOT NULL ,
    upload_Time TIMESTAMP NOT NULL ,
    titel VARCHAR(255) NOT NULL ,
    beschreibung VARCHAR(255),
    original_name VARCHAR(255) NOT NULL
);