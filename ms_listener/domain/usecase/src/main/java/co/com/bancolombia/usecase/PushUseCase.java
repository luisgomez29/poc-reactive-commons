package co.com.bancolombia.usecase;


import co.com.bancolombia.model.events.gateways.CommandsGateway;
import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.events.gateways.QueriesGateway;
import co.com.bancolombia.model.push.MessagePush;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PushUseCase {

    private final CommandsGateway commandsGateway;
    private final EventsGateway eventsGateway;
    private final QueriesGateway queriesGateway;

    public Mono<Void> sendPushResponse(String replyID, String correlationID, MessagePush messagePush) {
        return queriesGateway.sendQueryResponse(replyID, correlationID, messagePush);
    }

    public Mono<MessagePush> sendPush(MessagePush messagePush) {
        return commandsGateway.sendPush(messagePush)
                .flatMap(eventsGateway::emit)
                .flatMap(queriesGateway::sendQuery);
    }

}
