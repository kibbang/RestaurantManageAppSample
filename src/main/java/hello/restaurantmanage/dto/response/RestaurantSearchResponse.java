package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantSearchResponse {
    private Long id;
    private String name;
    private String address;
    private FoodCategory foodCategory;
    private double averageRate;
    private Long visitCount;
}
