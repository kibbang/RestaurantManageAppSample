package hello.restaurantmanage.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonApiResponseEntity<T> {
    private int status;
    private String message;
    private T data;

    public CommonApiResponseEntity(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
