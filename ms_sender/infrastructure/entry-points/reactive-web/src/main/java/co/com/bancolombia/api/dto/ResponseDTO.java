package co.com.bancolombia.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ResponseDTO {
    private String title;
    private String message;
    private String dateSend;
}