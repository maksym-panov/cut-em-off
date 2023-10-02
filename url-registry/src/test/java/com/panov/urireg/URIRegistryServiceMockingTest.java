package com.panov.urireg;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class URIRegistryServiceMockingTest {
    @Mock
    URIRegistryRepository uriRegistryRepository;
    URIRegistryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new URIRegistryService(uriRegistryRepository);
    }

    @Test
    @Order(1)
    @DisplayName("Should save provided URL mapping to the persistence")
    void shouldSaveNewUriToThePersistence() {
        // given
        String fullLink = "https://google.com";
        String shortcut = "fAle13";
        FullLinkRecord fullLinkRecord =
                new FullLinkRecord(fullLink, shortcut);
        // when
        underTest.registerNewURIRecord(fullLinkRecord);
        // then
        var fullLinkRecordArgumentCaptor = ArgumentCaptor.forClass(FullLinkRecord.class);
        verify(uriRegistryRepository).save(fullLinkRecordArgumentCaptor.capture());

        var captured = fullLinkRecordArgumentCaptor.getValue();
        assertThat(captured).isNotNull();
        assertThat(captured.getFullLink()).isEqualTo(fullLink);
        assertThat(captured.getShortcut()).isEqualTo(shortcut);
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve optional with corresponding shortcut if provided link was met before")
    void shouldRetrieveOptionalWithShortcutOfExistingLink() {
        // given
        String fullLink = "https://google.com";
        // when
        underTest.findExistingURIRecord(fullLink);
        // then
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(uriRegistryRepository).findById(stringArgumentCaptor.capture());
        var captured = stringArgumentCaptor.getValue();
        assertThat(captured).isEqualTo(fullLink);
    }

    @Test
    @DisplayName("Should check if there is mapping for provided link")
    void shouldCheckIfThereIsMappingForLink() {
        // given
        var fullLinkRecord = new FullLinkRecord("testid", "test");
        // when
        underTest.registerNewURIRecord(fullLinkRecord);
        // then
        var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(uriRegistryRepository).existsById(stringArgumentCaptor.capture());
        var captured = stringArgumentCaptor.getValue();
        assertThat(captured).isEqualTo("testid");
    }
}
