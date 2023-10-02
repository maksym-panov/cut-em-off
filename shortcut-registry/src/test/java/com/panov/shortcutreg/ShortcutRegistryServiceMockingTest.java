package com.panov.shortcutreg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShortcutRegistryServiceMockingTest {
    @Mock
    ShortcutRegistryRepository shortcutRegistryRepository;
    ShortcutRegistryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ShortcutRegistryService(shortcutRegistryRepository);
    }

    @Test
    @Order(1)
    @DisplayName("Should save provided shortcut to URL mapping to the persistence")
    void shouldSaveNewShortcutMappingToThePersistence() {
        // given
        String shortcut = "fAle13";
        String fullLink = "https://google.com";
        Shortcut shortcutEntity =
                new Shortcut(shortcut, fullLink);
        // when
        underTest.registerNewShortcutMapping(shortcutEntity);
        // then
        var shortcutArgumentCaptor = ArgumentCaptor.forClass(Shortcut.class);
        verify(shortcutRegistryRepository).save(shortcutArgumentCaptor.capture());

        var captured = shortcutArgumentCaptor.getValue();
        assertThat(captured).isNotNull();
        assertThat(captured.getShortcut()).isEqualTo(shortcut);
        assertThat(captured.getFullLink()).isEqualTo(fullLink);
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve optional with corresponding full URL if provided shortcut exists")
    void shouldRetrieveOptionalWithFullUrlForShortcut() {
        // given
        String shortcut = "fAle13";
        // when
        underTest.getFullURLByShortcut(shortcut);
        // then
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(shortcutRegistryRepository).findById(stringArgumentCaptor.capture());
        var captured = stringArgumentCaptor.getValue();
        assertThat(captured).isEqualTo(shortcut);
    }

    @Test
    @DisplayName("Should check if provided shortcut is already being used")
    void shouldCheckIfShortcutIsTaken() {
        // given
        var shortcut = new Shortcut("test_shortcut", "test_full_url");
        // when
        underTest.registerNewShortcutMapping(shortcut);
        // then
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(shortcutRegistryRepository).existsById(stringArgumentCaptor.capture());
        var captured = stringArgumentCaptor.getValue();
        assertThat(captured).isEqualTo("test_shortcut");
    }
}
