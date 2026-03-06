package hello.restaurantmanage.dto.request;

import hello.restaurantmanage.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantUpdateRequest {
    private Long id;
    private String name;
    private String address;
    private FoodCategory category;
    private String description;
}
