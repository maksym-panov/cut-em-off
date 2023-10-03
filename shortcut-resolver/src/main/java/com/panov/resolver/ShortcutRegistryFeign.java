package com.panov.resolver;

import com.panov.shortcutreg.FullURLResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("SHORTCUTREGISTRY")
public interface ShortcutRegistryFeign {
    @GetMapping("/{shortcut}")
    FullURLResponse getLinkForShortcut(@PathVariable("shortcut") String shortcut);
}
