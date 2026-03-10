package hello.restaurantmanage.repository;

import hello.restaurantmanage.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);

    List<Menu> findByRestaurantIdIn(List<Long> restaurantIds);
}
