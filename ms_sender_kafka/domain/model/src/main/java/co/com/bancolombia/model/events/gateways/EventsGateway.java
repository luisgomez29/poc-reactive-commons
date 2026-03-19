package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.push.MessagePush;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<MessagePush> emit(MessagePush messagePush);
}
