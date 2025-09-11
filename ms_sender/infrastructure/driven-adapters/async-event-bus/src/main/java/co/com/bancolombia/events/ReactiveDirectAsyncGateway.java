package co.com.bancolombia.events;

import co.com.bancolombia.model.events.gateways.CommandsGateway;
import co.com.bancolombia.model.events.gateways.QueriesGateway;
import co.com.bancolombia.model.push.MessagePush;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.api.AsyncQuery;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.reactivecommons.async.impl.config.annotations.EnableDirectAsyncGateway;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Level;

@Log
@AllArgsConstructor
@EnableDirectAsyncGateway
public class ReactiveDirectAsyncGateway implements CommandsGateway, QueriesGateway {
    public static final String TARGET_NAME = "ms_listener";
    public static final String SOME_COMMAND_NAME = "command.push";
    public static final String SOME_COMMAND_ERROR_NAME = "command.push.error";
    public static final String SOME_QUERY_NAME = "query.push";
    private final DirectAsyncGateway gateway;

    @Override
    public Mono<MessagePush> sendPush(MessagePush messagePush) {
        log.log(Level.INFO, "Sending command: {0}: {1}", new String[]{SOME_COMMAND_NAME, messagePush.toString()});
        return gateway.sendCommand(
                        new Command<>(SOME_COMMAND_NAME, UUID.randomUUID().toString(), messagePush), TARGET_NAME
                )
                .thenReturn(messagePush);
    }

    @Override
    public Mono<MessagePush> sendPushWitError(MessagePush messagePush) {
        log.log(Level.INFO, "Sending command: {0}: {1}", new String[]{SOME_COMMAND_ERROR_NAME, messagePush.toString()});
        return gateway.sendCommand(
                        new Command<>(SOME_COMMAND_ERROR_NAME, UUID.randomUUID().toString(), messagePush), TARGET_NAME
                )
                .thenReturn(messagePush);
    }

    @Override
    public Mono<MessagePush> sendQuery(MessagePush messagePush) {
        log.log(Level.INFO, "Sending query request: {0}: {1}", new String[]{SOME_QUERY_NAME, messagePush.toString()});
        return gateway.requestReply(new AsyncQuery<>(SOME_QUERY_NAME, messagePush), TARGET_NAME, MessagePush.class);
    }
}
