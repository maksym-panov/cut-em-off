package com.panov.shortcutreg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public record ShortcutRegistryService(
        ShortcutRegistryRepository shortcutRegistryRepository
) {
    public Optional<Shortcut> getFullURLByShortcut(String shortcut) {
        try {
            log.info("Searching for a full URL for shortcut '{}'", shortcut);
            return shortcutRegistryRepository.findById(shortcut);
        } catch (Exception e) {
            log.error("Error during fetching full URL for shortcut '{}'", shortcut);
            return Optional.empty();
        }
    }

    public void registerNewShortcutMapping(Shortcut shortcut) {
        try {
            String shortcutVal = shortcut.getShortcut();
            log.info("Checking if there is already shortcut '{}'", shortcutVal);
            boolean exists = shortcutRegistryRepository.existsById(shortcutVal);
            if (exists) {
                log.info("Shortcut '{}' already exists", shortcutVal);
                return;
            }
            log.info(
                "Persisting new shortcut '{}' referring to '{}'",
                shortcut.getShortcut(),
                shortcut.getFullLink()
            );
            shortcutRegistryRepository.save(shortcut);
        } catch (Exception e) {
            log.error(
                "Error during an attempt of persisting shortcut '{}' for link '{}'",
                shortcut.getShortcut(),
                shortcut.getFullLink()
            );
        }
    }
}
