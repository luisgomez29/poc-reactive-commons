package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.push.MessagePush;
import reactor.core.publisher.Mono;

public interface QueriesGateway {
    Mono<MessagePush> sendQuery(MessagePush messagePush);
    Mono<Void> sendQueryResponse(String replyID, String correlationID, MessagePush messagePush);
}
