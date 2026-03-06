package hello.restaurantmanage.service;

import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    EntityManager em;

    @Test
    void 맛집을_등록한다() {
        // Given
        RestaurantCreateRequest request1 = new RestaurantCreateRequest("test2", "test2", FoodCategory.WESTERN, "testDescriptionWestern");
        RestaurantCreateRequest request2 = new RestaurantCreateRequest("test2", "test2", FoodCategory.WESTERN, "testDescriptionWestern");

        // When
        Long save1 = restaurantService.save(request1);
        Long save2 = restaurantService.save(request2);

        em.flush();
        em.clear();

        // Then

        Assertions.assertThat(restaurantService.findAll()).hasSize(2);
    }
}