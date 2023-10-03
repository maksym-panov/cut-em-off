package com.panov.shortener;

import com.panov.shortener.dto.FullURLResponse;
import com.panov.shortener.dto.Shortcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("shortcut-registry")
public interface ShortcutRegistryFeign {
    @GetMapping("/{shortcut}")
    FullURLResponse getLinkForShortcut(@PathVariable("shortcut") String shortcut);

    @PostMapping
    void registerNewShortcut(@RequestBody Shortcut request);
}
