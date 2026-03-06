package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private FoodCategory foodCategory;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getFoodCategory(),
                restaurant.getDescription(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }
}
