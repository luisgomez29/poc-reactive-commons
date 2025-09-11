package co.com.bancolombia.api;

import co.com.bancolombia.api.config.RequestValidator;
import co.com.bancolombia.api.dto.RequestDTO;
import co.com.bancolombia.api.dto.ResponseDTO;
import co.com.bancolombia.model.push.MessagePush;
import co.com.bancolombia.usecase.PushUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RequestValidator validator;
    private final PushUseCase useCase;

    public Mono<ServerResponse> sendPushMessage(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDTO.class)
                .doOnSuccess(validator::validateBody)
                .flatMap(RequestDTO::toModel)
                .flatMap(useCase::sendPush)
//                .doOnNext(e-> System.out.println("Response received: " + e))
                .map(this::toResponseDTO)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private ResponseDTO toResponseDTO(MessagePush model) {
        return ResponseDTO.builder()
                .title(model.getTitle())
                .message(model.getMessage())
                .dateSend(model.getDateSend().toString())
                .build();
    }
}
