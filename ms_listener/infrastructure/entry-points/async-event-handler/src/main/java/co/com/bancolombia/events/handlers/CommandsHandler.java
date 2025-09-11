package co.com.bancolombia.events.handlers;

import co.com.bancolombia.model.push.MessagePush;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.impl.config.annotations.EnableCommandListeners;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@AllArgsConstructor
@EnableCommandListeners
public class CommandsHandler {
//    private final SampleUseCase sampleUseCase;

    public Mono<Void> handleCommandA(Command<MessagePush> command) {
        log.log(Level.INFO, "Command received: {0} -> {1}", new Object[]{command.getName(), command.getData()});
        return Mono.empty();
    }

    public Mono<Void> handleCommandError(Command<MessagePush> command) {
        log.log(Level.INFO, "Command received: {0} -> {1}", new Object[]{command.getName(), command.getData()});
        return Mono.error(new Throwable("Error in command"));
    }

}
