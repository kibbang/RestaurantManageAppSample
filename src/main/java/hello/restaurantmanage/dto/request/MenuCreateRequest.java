package hello.restaurantmanage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuCreateRequest {
    @NotBlank
    private String name;
    @NotNull
    @Min(10000)
    private Integer price;
    @NotBlank
    private String description;
    @NotNull
    private Boolean isPopular;
}
