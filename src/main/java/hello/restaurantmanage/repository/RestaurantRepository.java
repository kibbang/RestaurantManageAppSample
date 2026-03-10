package hello.restaurantmanage.repository;

import hello.restaurantmanage.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantCustomRepository {
}
