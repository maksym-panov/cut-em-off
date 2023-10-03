package com.panov.shortener.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public record Encoder62Test(@Autowired Encoder62 underTest) {
    @Test
    @DisplayName("Should have 62-based encoding")
    void shouldHave62BasedEncoding() {
        // given
        long to_enc_upper_bound = 61;
        long to_enc_over_upper_bound = 62;
        // when
        String enc1 = underTest.encode(to_enc_upper_bound);
        String enc2 = underTest.encode(to_enc_over_upper_bound);
        // then
        assertThat(enc1.length()).isEqualTo(1);
        assertThat(enc2.length()).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("provideFirst26ValuesToCheckAlphabet")
    @DisplayName("First 26 signs in alphabet are consistent upper case English letters")
    void firstAlphabetTest(Long num) {
        // when
        String encoded = underTest().encode(num);
        // then
        assertThat(encoded).isEqualTo(Character.valueOf((char) (num + 'A')).toString());
    }

    @ParameterizedTest
    @MethodSource("provideSecond26ValuesToCheckAlphabet")
    @DisplayName("Second 26 signs in alphabet are consistent lower case English letters")
    void secondAlphabetTest(Long num) {
        // when
        String encoded = underTest().encode(num);
        // then
        assertThat(encoded).isEqualTo(Character.valueOf((char) (num - 26 + 'a')).toString());
    }

    @ParameterizedTest
    @MethodSource("provideLast10ValuesToCheckAlphabet")
    @DisplayName("Last 10 signs in alphabet are consistent numbers from 0 to 9")
    void thirdAlphabetTest(Long num) {
        // when
        String encoded = underTest().encode(num);
        // then
        assertThat(encoded).isEqualTo(Character.valueOf((char) (num - 52 + '0')).toString());
    }

    static LongStream provideFirst26ValuesToCheckAlphabet() {
        return LongStream.range(0, 26);
    }

    static LongStream provideSecond26ValuesToCheckAlphabet() {
        return LongStream.range(26, 52);
    }

    static LongStream provideLast10ValuesToCheckAlphabet() {
        return LongStream.range(52, 62);
    }
}
