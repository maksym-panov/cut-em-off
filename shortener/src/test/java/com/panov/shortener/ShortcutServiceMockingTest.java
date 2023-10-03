package com.panov.shortener;

import com.panov.shortcutreg.Shortcut;
import com.panov.shortener.utils.Encoder;
import com.panov.urireg.FullLinkRecord;
import com.panov.urireg.LookupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShortcutServiceMockingTest {
    @Mock URLRegistryFeign urlRegistryFeign;
    @Mock ShortcutRegistryFeign shortcutRegistryFeign;
    @Mock Encoder encoder;
    @Mock Environment environment;

    ShortcutService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ShortcutService(
            urlRegistryFeign,
            shortcutRegistryFeign,
            encoder,
            environment
        );
    }

    @Test
    @DisplayName("Should get environment shortener.domain property")
    void shouldUseDomainProperty() {
        // given
        String url = "sampleurl.in";
        // when
        underTest.generateShortcutAndSave(url);
        // then
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(environment).getProperty(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("shortener.domain");
    }

    @Test
    @DisplayName("Should check if there is a record for given URL")
    void shouldCheckIfThereIsARecordForGivenURL() {
        // given
        String url = "randomurl.com.ua";
        // when
        underTest.generateShortcutAndSave(url);
        // then
        var lookupRequestArgumentCaptor =
                ArgumentCaptor.forClass(LookupRequest.class);
        verify(urlRegistryFeign).getExistingURLMapping(lookupRequestArgumentCaptor.capture());
        assertThat(lookupRequestArgumentCaptor.getValue().getUrl()).isEqualTo(url);
    }

    @Test
    @DisplayName("Should use encoder for shortcut generation")
    void shouldUseEncoderForShortcutGeneration() {
        // given
        String url = "sampleurllll.com";
        // when
        underTest.generateShortcutAndSave(url);
        // then
        verify(encoder).encode(anyLong());
    }

    @Test
    @DisplayName("Should register new URL mapping")
    void shouldRegisterNewURLMapping() {
        // given
        String url = "sample111.uk";
        // when
        underTest.generateShortcutAndSave(url);
        var fullLinkRecordArgumentCaptor =
                ArgumentCaptor.forClass(FullLinkRecord.class);
        verify(urlRegistryFeign).registerURLMapping(fullLinkRecordArgumentCaptor.capture());
        // then
        assertThat(fullLinkRecordArgumentCaptor.getValue().getFullLink()).isEqualTo(url);
    }

    @Test
    @DisplayName("Should save new shortcut")
    void shouldSaveNewShortcut() {
        // given
        String url = "url.com.fr";
        // when
        underTest.generateShortcutAndSave(url);
        var shortcutArgumentCaptor = ArgumentCaptor.forClass(Shortcut.class);
        verify(shortcutRegistryFeign).registerNewShortcut(shortcutArgumentCaptor.capture());
        // then
        assertThat(shortcutArgumentCaptor.getValue().getFullLink()).isEqualTo(url);
    }
}
