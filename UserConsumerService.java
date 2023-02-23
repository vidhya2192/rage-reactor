package com.learnings.listener;

import com.learnings.event.UserEvent;
import com.learnings.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumerService implements ReactiveKafkaConsumer<UUID, UserEvent> {

    private final ReactiveKafkaConsumerTemplate<UUID, UserEvent> reactiveKafkaConsumerTemplate;
    private final UserService userService;

    @EventListener(ApplicationStartedEvent.class)
    public Disposable consumeUserEvents() {
        log.info("----------- Consuming events ------------");
        return reactiveKafkaConsumerTemplate.receive()
                .doOnError(error -> log.error("Error occurred while receiving, retrying", error))
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofMinutes(1)))
                .doOnNext(record -> log.debug("Received event: key {} {}", record.key(), record.value()))
                .concatMap(userService::processUser)
                .subscribe(record -> record.receiverOffset().acknowledge());
    }

    @Override
    public boolean shouldSubscribe(UUID key, UserEvent event) {
        return true;
    }
}
