package hello.restaurantmanage.domain;

import hello.restaurantmanage.common.BaseTimeEntity;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "restaurants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;
    private String description;

    public Restaurant(String name, String address, FoodCategory foodCategory, String description) {
        this.name = name;
        this.address = address;
        this.foodCategory = foodCategory;
        this.description = description;
    }

    public void changeInfo(String name, String address, FoodCategory foodCategory, String description) {
        this.name = name;
        this.address = address;
        this.foodCategory = foodCategory;
        this.description = description;
    }
}
