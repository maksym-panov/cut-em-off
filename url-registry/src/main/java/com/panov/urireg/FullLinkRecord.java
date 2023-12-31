package com.panov.urireg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "registered_url_collection")
public class FullLinkRecord implements Serializable {
    @Id
    private String fullLink;
    private String shortcut;
}
