package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.datei;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("datei")
public record DateiRecord(
        @Id Long id,

        @Column("datei_system_name")
        String dateiSystemName,

        @Column("uploader_id")
        String uploaderId,

        @Column("upload_time")
        LocalDateTime uploadTime,

        String titel,
        String beschreibung,

        @Column("original_name")
        String originalName
) {}