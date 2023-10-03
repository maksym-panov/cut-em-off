package com.panov.shortener;

import com.panov.shortcutreg.Shortcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("SHORTCUTREGISTRY")
public interface ShortcutRegistryFeign {
    @PostMapping
    void registerNewShortcut(@RequestBody Shortcut request);
}
