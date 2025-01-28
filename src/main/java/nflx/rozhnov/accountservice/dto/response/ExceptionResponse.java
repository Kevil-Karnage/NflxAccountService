package nflx.rozhnov.accountservice.dto.response;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private int code;
    private String message;
}
