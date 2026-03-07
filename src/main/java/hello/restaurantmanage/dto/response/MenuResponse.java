package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MenuResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private Boolean isPopular;
    private Long restaurantId;
    private String restaurantName;

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription(),
                menu.getIsPopular(),
                menu.getRestaurant().getId(),
                menu.getRestaurant().getName()
        );
    }

    private MenuResponse(Long id, String name, Integer price, String description, Boolean isPopular, Long restaurantId, String restaurantName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isPopular = isPopular;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
