package com.panov.urireg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Document(collection = "registered_url_collection")
public class FullLinkRecord implements Serializable {
    @Id
    private String fullLink;
    private String shortcut;
}
