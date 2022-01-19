package ch.ffhs.pa5.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RandomIdProviderTest {
    RandomIdProvider randomIdProvider = new RandomIdProvider();

    @Test
    void testId() {
        UUID result = randomIdProvider.id();
        Assertions.assertNotNull(result);
    }
}