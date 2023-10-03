package com.panov.shortener;

import com.panov.shortener.dto.FullLinkRecord;
import com.panov.shortener.dto.LookupRequest;
import com.panov.shortener.dto.Shortcut;
import com.panov.shortener.utils.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public record ShortcutService(
    URLRegistryFeign urlRegistryFeign,
    ShortcutRegistryFeign shortcutRegistryFeign,
    Encoder encoder,
    Environment environment
) {
    public String generateShortcutAndSave(String url) {
        String domain = environment.getProperty("shortener.domain");

        log.info("Trimming and removing https://, http://, www. from URL '{}'", url);
        url = prepareURL(url);

        log.info("Checking if requested URL '{}' already has a shortcut", url);

        try {
            LookupRequest request = new LookupRequest(url);
            FullLinkRecord fromPersistence = urlRegistryFeign
                    .getExistingURLMapping(request);
            if (fromPersistence != null) {
                String shortcut = fromPersistence.shortcut();
                log.info("URL '{}' already has a shortcut '{}'", url, shortcut);
                return String.format("%s/%s", domain, shortcut);
            }
        } catch (Exception e) {
            log.error("Error during fetching information about registered URLs");
            e.printStackTrace();
            return null;
        }

        var generatedShortcut = generateShortcut();
        log.info("Shortcut '{}' has been generated for URL '{}'", generatedShortcut, url);

        try {
            log.info("Saving information about URL '{}' in the URL registry", url);
            var newFullLinkRecord = new FullLinkRecord(url, generatedShortcut);
            urlRegistryFeign.registerURLMapping(newFullLinkRecord);

            log.info("Saving generated shortcut '{}' in the shortcut registry", generatedShortcut);
            var newShortcut = new Shortcut(generatedShortcut, url);
            shortcutRegistryFeign.registerNewShortcut(newShortcut);
        } catch (Exception e) {
            log.error("Error during saving information about shortcut for new URL '{}'", url);
            return null;
        }

        return String.format("%s/%s", domain, generatedShortcut);
    }

    private String prepareURL(String url) {
        return url
            .trim()
            .replaceFirst("^(https?://www\\.|https?://|www\\.)", "");
    }

    private String generateShortcut() {
        long random = new Random().nextLong();
        return encoder.encode(random);
    }
}
