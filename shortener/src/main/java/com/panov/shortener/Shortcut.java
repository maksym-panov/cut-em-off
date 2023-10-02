package com.panov.shortener;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "shorts")
public class Shortcut {
    @Id
    private String shortcut;
    private String fullLink;
}
