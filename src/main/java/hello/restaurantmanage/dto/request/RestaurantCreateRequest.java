package hello.restaurantmanage.dto.request;

import hello.restaurantmanage.enums.FoodCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    private FoodCategory foodCategory;
    private String description;
}
