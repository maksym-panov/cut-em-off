package com.panov.urireg;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/url-registry")
@RequiredArgsConstructor
public class URIRegistryController {
    private final URIRegistryService uriRegistryService;

    @Cacheable(value = "CachedURLRecord", key = "#fullLink")
    @GetMapping
    public FullLinkRecord getExistingShortcut(@RequestBody String fullLink) {
        var opt = uriRegistryService.findExistingURIRecord(fullLink);
        return opt.orElse(null);
    }

    @PostMapping
    public void registerShortcut(@RequestBody FullLinkRecord fullLinkRecord) {
        uriRegistryService.registerNewURIRecord(fullLinkRecord);
    }
}
