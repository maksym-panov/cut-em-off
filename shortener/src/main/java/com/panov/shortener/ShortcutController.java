package com.panov.shortener;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shortener")
public record ShortcutController(ShortcutService shortenedLinkService) {
    @PostMapping
    public ResponseEntity<ShortcutResponse> shortenURL(
            @RequestBody ShortcutRequest request
    ) {
        // todo: implement shortener endpoint
        throw new UnsupportedOperationException();
    }
}
