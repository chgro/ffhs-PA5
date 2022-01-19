package ch.ffhs.pa5.backend;

import ch.ffhs.pa5.backend.service.IRandomIdProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("fakeIdProvider")
public class FakeIdProvider implements IRandomIdProvider{
    @Override
    public UUID id() {
        return UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
    }
}
