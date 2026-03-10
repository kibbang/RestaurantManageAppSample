package hello.restaurantmanage.repository;

import hello.restaurantmanage.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByRestaurantId(Long restaurantId);

    List<Visit> findByRestaurantIdIn(List<Long> restaurantIds);
}
