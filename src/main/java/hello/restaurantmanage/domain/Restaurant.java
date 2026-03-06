package hello.restaurantmanage.domain;

import hello.restaurantmanage.common.BaseTimeEntity;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "restaurants")
@NoArgsConstructor
public class Restaurant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    public Restaurant(String name, String address, FoodCategory category) {
        this.name = name;
        this.address = address;
        this.category = category;
    }
}
