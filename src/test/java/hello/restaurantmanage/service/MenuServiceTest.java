package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Menu;
import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.dto.request.MenuCreateRequest;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MenuServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantService restaurantService;

    @Test
    public void 존재하지_않는_맛집에_메뉴_등록시_예외발생() {
        // Given
        Long restaurantId = 20L;
        MenuCreateRequest menuCreateRequest = new MenuCreateRequest(
                "testMenu",
                100000,
                "testMenuDescription",
                false
        );

        // When

        // Then
        Assertions.assertThatThrownBy(() -> menuService.registerMenu(restaurantId, menuCreateRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 맛집의_메뉴_목록을_조회한다() {
        // Given
        Restaurant restaurant = new Restaurant("test", "test", FoodCategory.CHINESE, "test");
        em.persist(restaurant);

        Menu menu1 = Menu.of("testMenu1", 100000, "testMenuDescription1", false);
        menu1.assignRestaurant(restaurant);
        Menu menu2 = Menu.of("testMenu2", 200000, "testMenuDescription2", true);
        menu2.assignRestaurant(restaurant);

        em.persist(menu1);
        em.persist(menu2);

        em.flush();
        em.clear();

        // When
        Restaurant restaurant1 = restaurantService.getRestaurant(restaurant.getId());
        List<Menu> allMenus = menuService.getAllMenus(restaurant1.getId());

        // Then

        assertEquals(2, allMenus.size());
        Assertions.assertThat(allMenus.get(0).getName()).isEqualTo("testMenu1");
        Assertions.assertThat(allMenus.get(1).getName()).isEqualTo("testMenu2");
    }
}