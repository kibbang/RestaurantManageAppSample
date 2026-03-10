package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.dto.response.MenuResponse;
import hello.restaurantmanage.dto.response.RestaurantDetailResponse;
import hello.restaurantmanage.dto.response.RestaurantSearchResponse;
import hello.restaurantmanage.dto.response.VisitResponse;
import hello.restaurantmanage.dto.search.RestaurantSearchCondition;
import hello.restaurantmanage.repository.MenuRepository;
import hello.restaurantmanage.repository.RestaurantRepository;
import hello.restaurantmanage.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantDetailService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VisitRepository visitRepository;


    public Page<RestaurantSearchResponse> search(RestaurantSearchCondition condition, Pageable pageable) {

        return restaurantRepository.search(condition, pageable);
    }

    public RestaurantDetailResponse showDetails(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();

        List<MenuResponse> menuList = menuRepository.findByRestaurantId(id)
                .stream()
                .map(MenuResponse::from)
                .toList();

        List<VisitResponse> visitList = visitRepository.findByRestaurantId(id)
                .stream()
                .map(VisitResponse::from)
                .toList();

        int visitCount = visitList.size();
        double averageRating = visitList.stream().mapToInt(VisitResponse::getRating).average().orElse(0.0);

        return RestaurantDetailResponse.from(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getFoodCategory(),
                restaurant.getDescription(),
                averageRating,
                visitCount,
                menuList,
                visitList,
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }
}
