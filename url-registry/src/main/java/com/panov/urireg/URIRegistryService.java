package com.panov.urireg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public record URIRegistryService(URIRegistryRepository uriRegistryRepository) {
    public Optional<FullLinkRecord> findExistingURIRecord(String fullLink) {
        try {
            log.info(
                "Searching for shortcut of link '{}'",
                fullLink
            );
            return uriRegistryRepository.findById(fullLink);
        } catch (Exception e) {
            log.error(
                "Error during an attempt of finding shortcut for '{}'",
                fullLink
            );
            return Optional.empty();
        }
    }

    public void registerNewURIRecord(FullLinkRecord fullLinkRecord) {
        try {
            String full = fullLinkRecord.getFullLink();
            log.info("Checking if there is shortcut for link '{}'", full);
            var exists = uriRegistryRepository.existsById(full);
            if (exists) {
                log.info("Shortcut for '{}' already exists", full);
                return;
            }
            log.info(
                "Persisting new shortcut '{}' for link '{}'",
                fullLinkRecord.getShortcut(),
                fullLinkRecord.getFullLink()
            );
            uriRegistryRepository.save(fullLinkRecord);
        } catch (Exception e) {
            log.error(
                "Error during an attempt of persisting new shortcut '{}' for link '{}'",
                fullLinkRecord.getShortcut(),
                fullLinkRecord.getFullLink()
            );
        }
    }
}
