package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.enums.FoodCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RestaurantDetailResponse {
    private Long id;
    private String name;
    private String address;
    private FoodCategory foodCategory;
    private String description;
    private double averageRate; // 방문  기록 평점 평균
    private Integer visitCount; // 총 방문 횟수
    private List<MenuResponse> menus; // 메뉴 목록
    private List<VisitResponse> visits; // 방문기록 목록
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantDetailResponse from(Long id, String name, String address, FoodCategory foodCategory, String description, double averageRate, Integer visitCount, List<MenuResponse> menus, List<VisitResponse> visits, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new RestaurantDetailResponse(id, name, address, foodCategory, description, averageRate, visitCount, menus, visits, createdAt, updatedAt);
    }

    private RestaurantDetailResponse(Long id, String name, String address, FoodCategory foodCategory, String description, double averageRate, Integer visitCount, List<MenuResponse> menus, List<VisitResponse> visits, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
