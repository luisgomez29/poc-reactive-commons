package co.com.bancolombia.usecase;


import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.push.MessagePush;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PushUseCase {

    private final EventsGateway eventsGateway;

//    public Mono<Void> sendPush(String replyID, String correlationID, MessagePush messagePush) {
//        return queriesGateway.emit(replyID, correlationID, messagePush);
//    }

    public Mono<MessagePush> sendPush(MessagePush messagePush) {
        return eventsGateway.emit(messagePush)
                .thenReturn(messagePush);
    }

}
