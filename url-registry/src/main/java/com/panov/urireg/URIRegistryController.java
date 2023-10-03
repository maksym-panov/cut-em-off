package com.panov.urireg;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class URIRegistryController {
    private final URIRegistryService uriRegistryService;

    @Cacheable(value = "CachedURLRecord", key = "#request.url")
    @PostMapping("/existing")
    public FullLinkRecord getExistingURLMapping(@RequestBody LookupRequest request) {
        var opt = uriRegistryService.findExistingURIRecord(request.getUrl());
        return opt.orElse(null);
    }

    @PostMapping
    public void registerURLMapping(@RequestBody FullLinkRecord fullLinkRecord) {
        uriRegistryService.registerNewURIRecord(fullLinkRecord);
    }
}
