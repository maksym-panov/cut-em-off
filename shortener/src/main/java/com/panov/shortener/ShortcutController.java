package com.panov.shortener;

import com.panov.shortener.dto.ShortcutRequest;
import com.panov.shortener.dto.ShortcutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shortener")
public record ShortcutController(ShortcutService shortcutService) {
    @PostMapping
    public ResponseEntity<ShortcutResponse> shortenURL(
            @RequestBody ShortcutRequest request
    ) {
        String shortcutVal = shortcutService
                .generateShortcutAndSave(request.fullLink());

        if (shortcutVal == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(new ShortcutResponse(shortcutVal));
    }
}
