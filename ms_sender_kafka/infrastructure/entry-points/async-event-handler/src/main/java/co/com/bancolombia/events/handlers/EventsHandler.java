package co.com.bancolombia.events.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.impl.config.annotations.EnableEventListeners;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
@EnableEventListeners
public class EventsHandler {
//    private final SampleUseCase sampleUseCase;


    public Mono<Void> handleEventA(DomainEvent<Object/*change for proper model*/> event) {
        log.log(Level.INFO, "Event received: {0} -> {1}", new Object[]{event.getName(), event.getData()});
//        return sampleUseCase.doSomething(event.getData());
        return Mono.empty();
    }
}
