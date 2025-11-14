package com.crookedcoder.habitjournal.journal.entries;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "entries")
public @Data class Entry {

    @Id
    private String id;

    @NotEmpty(message = "Journal ID is required")
    @Indexed
    private String journalId;

    @Indexed
    private String habitId; // Optional: link entry to specific habit

    private String body; // Journal entry text content

    @NotEmpty(message = "Timestamp is required")
    private LocalDateTime timestamp;

}