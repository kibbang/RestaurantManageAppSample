package hello.restaurantmanage.repository;

import hello.restaurantmanage.dto.response.RestaurantSearchResponse;
import hello.restaurantmanage.dto.search.RestaurantSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RestaurantCustomRepository {
    Page<RestaurantSearchResponse> search(RestaurantSearchCondition condition, Pageable pageable);
}
