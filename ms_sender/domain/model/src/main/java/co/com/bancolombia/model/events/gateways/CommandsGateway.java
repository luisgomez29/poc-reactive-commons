package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.push.MessagePush;
import reactor.core.publisher.Mono;

public interface CommandsGateway {
    Mono<MessagePush> sendPush(MessagePush messagePush);
    Mono<MessagePush> sendPushWitError(MessagePush messagePush);
    Mono<MessagePush> sendPushCloud(MessagePush messagePush);
}
