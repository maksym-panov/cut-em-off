package com.panov;

import com.panov.resolver.ShortcutRegistryFeign;
import com.panov.resolver.ShortcutResolverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShortcutResolverServiceMockingTest {
    @Mock
    ShortcutRegistryFeign shortcutRegistryFeign;
    ShortcutResolverService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ShortcutResolverService(shortcutRegistryFeign);
    }

    @Test
    @DisplayName("Should call #getLinkForShortcut method in ShortcutRegistryFeign")
    void shouldCallLinkSearchMethod() {
        // given
        String shortcut = "a523sdf";
        // when
        underTest.resolveShortcut(shortcut);
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(shortcutRegistryFeign).getLinkForShortcut(stringArgumentCaptor.capture());
        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(shortcut);
    }
}
