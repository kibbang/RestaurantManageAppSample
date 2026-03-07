package hello.restaurantmanage.domain;

import hello.restaurantmanage.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private Boolean isPopular;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Menu of(String name, Integer price, String description, Boolean isPopular) {
        return new Menu(name, price, description, isPopular);
    }

    public void assignRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void changeInfo(String name, Integer price, String description, Boolean isPopular) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isPopular = isPopular;
    }

    private Menu(String name, Integer price, String description, Boolean isPopular) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isPopular = isPopular;
    }
}
