package com.panov.shortcutreg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public record ShortcutRegistryServiceTest(
        @Autowired ShortcutRegistryService underTest
) {
    @Test
    @DisplayName("Should retrieve empty optional if provided shortcut does not exists")
    void shouldReturnEmptyOptionalIfThereIsNoSuchShortcut() {
        // given
        var invalidShortcut = "DOES_NOT_EXISTS";
        // when
        var emptyOptional = underTest.getFullURLByShortcut(invalidShortcut);
        // then
        assertThat(emptyOptional).isEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("Should return optional with corresponding object if provided shortcut exists")
    void shouldReturnNotEmptyOptionalIfShortcutExists() {
        // given
        String shortcut1 = "ags24";
        String full1 = "https://www.test1.com";
        String shortcut2 = "bql13";
        String full2 = "https://www.test2.ua";
        var shortcutEntity1 = new Shortcut(shortcut1, full1);
        var shortcutEntity2 = new Shortcut(shortcut2, full2);
        // when
        underTest.registerNewShortcutMapping(shortcutEntity1);
        underTest.registerNewShortcutMapping(shortcutEntity2);
        var opt1 = underTest.getFullURLByShortcut(shortcut1);
        var opt2 = underTest.getFullURLByShortcut(shortcut2);
        // then
        assertThat(opt1).isNotEmpty();
        var ret1 = opt1.get();
        assertThat(ret1.getShortcut()).isEqualTo(shortcut1);
        assertThat(ret1.getFullLink()).isEqualTo(full1);
        assertThat(opt2).isNotEmpty();
        var ret2 = opt2.get();
        assertThat(ret2.getShortcut()).isEqualTo(shortcut2);
        assertThat(ret2.getFullLink()).isEqualTo(full2);
    }

    @Test
    @Order(1)
    @DisplayName("Should not modify mapping of existing shortcut")
    void shouldNotModifyMappingOfExistingShortcut() {
        // given
        String shortcut = "ags24";
        String currentFullURL = "https://www.test1.com";
        String newFullURL =  "www.youtube.com";
        var shortcutEntity = new Shortcut(shortcut, newFullURL);
        // when
        underTest.registerNewShortcutMapping(shortcutEntity);
        var opt = underTest.getFullURLByShortcut(shortcut);
        // then
        assertThat(opt).isNotEmpty();
        var ret = opt.get();
        assertThat(ret.getShortcut()).isEqualTo(shortcut);
        assertThat(ret.getFullLink()).isEqualTo(currentFullURL);
    }
}
