package co.com.bancolombia.events.handlers;

import co.com.bancolombia.model.push.MessagePush;
import co.com.bancolombia.usecase.PushUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.async.api.From;
import org.reactivecommons.async.impl.config.annotations.EnableQueryListeners;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
@EnableQueryListeners
public class QueriesHandler {
    private final PushUseCase pushUseCase;

    public Mono<Void> handleQueryA(From from, MessagePush messagePush) {
        log.log(Level.INFO, "Query received -> FROM= " + from + " QUERY= " + messagePush);
        return pushUseCase.sendPushResponse(from.getReplyID(), from.getCorrelationID(), messagePush);
    }

    public Mono<MessagePush> handleQueryB(MessagePush messagePush) {
        log.log(Level.INFO, "Query received= " + messagePush);
        return pushUseCase.sendPush(messagePush)
                .thenReturn(messagePush);
    }

}
