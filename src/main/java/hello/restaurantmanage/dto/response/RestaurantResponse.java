package hello.restaurantmanage.dto.response;

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
    private FoodCategory category;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
