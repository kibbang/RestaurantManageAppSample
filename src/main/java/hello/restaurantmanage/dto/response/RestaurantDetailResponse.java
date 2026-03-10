package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.enums.FoodCategory;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RestaurantDetailResponse {
    private final Long id;
    private final String name;
    private final String address;
    private final FoodCategory foodCategory;
    private final String description;
    private final double averageRate;
    private final int visitCount;
    private final List<MenuResponse> menus;
    private final List<VisitResponse> visits;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static RestaurantDetailResponse from(
            Restaurant restaurant,
            List<MenuResponse> menus,
            List<VisitResponse> visits,
            double averageRate,
            int visitCount) {
        return new RestaurantDetailResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getFoodCategory(),
                restaurant.getDescription(),
                averageRate,
                visitCount,
                menus,
                visits,
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    private RestaurantDetailResponse(Long id, String name, String address, FoodCategory foodCategory,
                                     String description, double averageRate, int visitCount,
                                     List<MenuResponse> menus, List<VisitResponse> visits,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.foodCategory = foodCategory;
        this.description = description;
        this.averageRate = averageRate;
        this.visitCount = visitCount;
        this.menus = menus;
        this.visits = visits;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
