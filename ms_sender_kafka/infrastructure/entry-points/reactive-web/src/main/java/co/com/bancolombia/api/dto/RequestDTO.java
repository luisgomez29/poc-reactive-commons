package co.com.bancolombia.api.dto;

import co.com.bancolombia.model.push.MessagePush;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestDTO {

    @Size(min = 1, max = 50, message = "{validator_invalid_size}")
    private String title;

    @NotBlank(message = "{validator_not_blank}")
    @NotNull(message = "{validator_not_blank}")
    @Size(min = 5, max = 500, message = "{validator_invalid_size}")
    private String message;

    public Mono<MessagePush> toModel() {
        return Mono.just(MessagePush.builder()
                .title(this.title)
                .message(this.message)
                .dateSend(LocalDateTime.now())
                .build());
    }

}
