package com.learnings.listener;

import com.learnings.event.IEvent;
import java.util.function.BiConsumer;

public interface ReactiveKafkaConsumer<K, V extends IEvent> {
    boolean shouldSubscribe(K key, V event);
}
