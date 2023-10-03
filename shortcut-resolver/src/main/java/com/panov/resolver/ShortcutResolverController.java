package com.panov.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public record ShortcutResolverController(ShortcutResolverService shortcutResolverService) {

    @GetMapping("/{shortcut}")
    public String resolveShortenedURL(@PathVariable("shortcut") String shortcut) {
        String redirectURL = shortcutResolverService.resolveShortcut(shortcut);
        if (redirectURL == null) {
            log.warn("Resolved URL is null");
            return "redirect:error/404";
        }

        log.info("Redirecting client to www.'{}'", redirectURL);
        return String.format("redirect:http://www.%s", redirectURL);
    }
}
