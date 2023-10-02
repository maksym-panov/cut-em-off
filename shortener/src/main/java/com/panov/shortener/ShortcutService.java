package com.panov.shortener;

import org.springframework.stereotype.Service;

@Service
public record ShortcutService(ShortcutRepository shortcutRepository) {
    public boolean generateShortcutAndSave(String url) {
        // todo: check if "url-registry" service already
        // todo: knows about shortcut for this url

        // todo: implement shortcut generation

        // todo: asynchronously persist shortcut via
        // todo: "shortcut-registry" and "url-registry" services
        throw new UnsupportedOperationException();
    }
}
