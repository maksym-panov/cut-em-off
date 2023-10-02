package com.panov.urireg;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public record URIRegistryServiceTest(
        @Autowired URIRegistryService underTest
) {
    @Test
    @DisplayName("Should retrieve empty optional if provided link was not added previously")
    void shouldReturnEmptyOptionalIfThereIsNoSuchLink() {
        // given
        var invalidFullLink = "DOES_NOT_EXISTS";
        // when
        var emptyOptional = underTest.findExistingURIRecord(invalidFullLink);
        // then
        assertThat(emptyOptional).isEmpty();
    }

    @Test
    @Order(1)
    @DisplayName("Should return optional of FullLinkRecord if provided link was met before")
    void shouldReturnNotEmptyOptionalIfThereIsSuchLink() {
        // given
        String full1 = "https://www.test1.com";
        String shortcut1 = "ags24";
        String full2 = "https://www.test2.ua";
        String shortcut2 = "bql13";
        var fullLinkRecord1 = new FullLinkRecord(full1, shortcut1);
        var fullLinkRecord2 = new FullLinkRecord(full2, shortcut2);
        // when
        underTest.registerNewURIRecord(fullLinkRecord1);
        underTest.registerNewURIRecord(fullLinkRecord2);
        var opt1 = underTest.findExistingURIRecord(full1);
        var opt2 = underTest.findExistingURIRecord(full2);
        // then
        assertThat(opt1).isNotEmpty();
        var ret1 = opt1.get();
        assertThat(ret1.getFullLink()).isEqualTo(full1);
        assertThat(ret1.getShortcut()).isEqualTo(shortcut1);
        assertThat(opt2).isNotEmpty();
        var ret2 = opt2.get();
        assertThat(ret2.getFullLink()).isEqualTo(full2);
        assertThat(ret2.getShortcut()).isEqualTo(shortcut2);
    }

    @Test
    @Order(2)
    @DisplayName("Should not change mapping for existing link")
    void shouldNotChangeMappingForExistingLink() {
        // given
        String full = "https://www.test1.com";
        String currentShortcut = "ags24";
        String newShortcut = "2bbb2";
        var fullLinkRecord = new FullLinkRecord(full, newShortcut);
        // when
        underTest.registerNewURIRecord(fullLinkRecord);
        var opt = underTest.findExistingURIRecord(full);
        // then
        assertThat(opt).isNotEmpty();
        var ret = opt.get();
        assertThat(ret.getShortcut()).isEqualTo(currentShortcut);
        assertThat(ret.getFullLink()).isEqualTo(full);
    }
}