package com.panov.shortcutreg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "shortcut_collection")
public class Shortcut {
    @Id
    private String shortcut;
    private String fullLink;
}
