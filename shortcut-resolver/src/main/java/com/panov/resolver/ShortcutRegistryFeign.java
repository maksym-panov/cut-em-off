package com.panov.resolver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("shortcut-registry")
public interface ShortcutRegistryFeign {
    @GetMapping("/{shortcut}")
    FullURLResponse getLinkForShortcut(@PathVariable("shortcut") String shortcut);
}
