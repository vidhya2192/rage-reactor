package com.learnings.service;

import com.learnings.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    public Flux<ReceiverRecord<UUID, UserEvent>> processUser(ReceiverRecord<UUID, UserEvent> receiverRecord) {
        log.info("Processing user information :{}", receiverRecord.value());
        return Flux.empty();
    }
}
