package ch.ffhs.pa5.backend.service;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface IRandomIdProvider {
    UUID id();
}
