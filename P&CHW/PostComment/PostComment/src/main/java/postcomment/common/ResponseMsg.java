package postcomment.common;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMsg {
    private int httpStatusCode;
    private String message;
    private Object results;
}
