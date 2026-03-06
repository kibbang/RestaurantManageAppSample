package hello.restaurantmanage.sample;


import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class EntityCreateTest {
    @Autowired
    EntityManager em;


    @Test
    void createRestaurant() {
        Restaurant restaurant = new Restaurant("test1", "test1", FoodCategory.KOREAN, "testDescription");
        em.persist(restaurant);

        em.flush();
        em.clear();

        Restaurant findRestaurant = em.find(Restaurant.class, restaurant.getId());

        Assertions.assertThat(findRestaurant.getId()).isEqualTo(restaurant.getId());
    }
}
