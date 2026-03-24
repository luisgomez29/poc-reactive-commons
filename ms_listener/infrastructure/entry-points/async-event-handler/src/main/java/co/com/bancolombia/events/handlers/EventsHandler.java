package co.com.bancolombia.events.handlers;

import co.com.bancolombia.model.push.MessagePush;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.RawMessage;
import org.reactivecommons.async.impl.config.annotations.EnableEventListeners;
import org.reactivecommons.async.impl.config.annotations.EnableNotificationListener;
import org.reactivecommons.async.rabbit.RabbitMessage;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@EnableEventListeners
@EnableNotificationListener
@Log4j2
public class EventsHandler {
//    private final SampleUseCase sampleUseCase;


    public Mono<Void> handleEventA(DomainEvent<MessagePush> event) {
        log.info("Event received: {0} -> {1}", new Object[]{event.getName(), event.getData()});
//        return sampleUseCase.doSomething(event.getData());
        return Mono.empty();
    }

    public Mono<Void> handleRawEventOrNotification(RawMessage event) {
        RabbitMessage rawMessage = (RabbitMessage) event;
        log.info("RawEvent received: {}",  new String(rawMessage.getBody()));
        log.info("Content Type: {}", rawMessage.getProperties().getContentType());
        log.info("Headers: {}", rawMessage.getProperties().getHeaders());
        // Process the raw event or notification
        return Mono.empty();
    }
}
