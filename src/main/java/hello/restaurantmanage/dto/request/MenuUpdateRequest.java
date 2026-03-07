package hello.restaurantmanage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Min(10000)
    private Integer price;
    @NotBlank
    private String description;
    @NotBlank
    private Boolean isPopular;
}
