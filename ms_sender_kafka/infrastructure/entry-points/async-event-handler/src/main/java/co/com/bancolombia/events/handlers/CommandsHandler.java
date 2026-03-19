package co.com.bancolombia.events.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.impl.config.annotations.EnableCommandListeners;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
@EnableCommandListeners
public class CommandsHandler {
//    private final SampleUseCase sampleUseCase;

    public Mono<Void> handleCommandA(Command<Object/*change for proper model*/> command) {
        log.log(Level.INFO, "Command received: {0} -> {1}", new Object[]{command.getName(), command.getData()});
//        return sampleUseCase.doSomething(command.getData());
        return Mono.empty();
    }

}
