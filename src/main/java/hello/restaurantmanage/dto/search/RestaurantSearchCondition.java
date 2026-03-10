package hello.restaurantmanage.dto.search;

import hello.restaurantmanage.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSearchCondition {
    private String name;
    private FoodCategory foodCategory;
    private String address;
    private Double minRate;
}
