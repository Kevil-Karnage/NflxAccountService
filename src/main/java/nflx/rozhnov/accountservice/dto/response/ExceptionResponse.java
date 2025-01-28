package nflx.rozhnov.accountservice.dto.response;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Integer code;
    private String message;
}
