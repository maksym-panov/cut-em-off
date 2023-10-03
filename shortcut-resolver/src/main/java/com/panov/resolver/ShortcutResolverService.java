package com.panov.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record ShortcutResolverService(ShortcutRegistryFeign shortcutRegistryFeign) {
    public String resolveShortcut(String shortcut) {
        try {
            log.info("Trying to find URL that matches the shortcut '{}'", shortcut);
            return shortcutRegistryFeign
                .getLinkForShortcut(shortcut)
                .fullLink();
        } catch (Exception e) {
            log.error(
                "Error occurred during an attempt of fetching full URL for shortcut '{}'",
                shortcut
            );
            return null;
        }
    }
}
