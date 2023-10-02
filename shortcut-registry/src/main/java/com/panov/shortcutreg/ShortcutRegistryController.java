package com.panov.shortcutreg;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shortcut-registry")
@RequiredArgsConstructor
public class ShortcutRegistryController {
    private final ShortcutRegistryService shortcutRegistryService;

    @Cacheable(value = "CachedShortcut", key = "#shortcut")
    @GetMapping("/{shortcut}")
    public FullURLResponse getLinkForShortcut(@PathVariable("shortcut") String shortcut) {
        var opt = shortcutRegistryService.getFullURLByShortcut(shortcut);
        if (opt.isEmpty()) {
            return null;
        }
        return new FullURLResponse(opt.get().getFullLink());
    }

    @PostMapping
    public void registerNewShortcut(@RequestBody Shortcut request) {
        shortcutRegistryService.registerNewShortcutMapping(request);
    }
}
