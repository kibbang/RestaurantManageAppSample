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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantDetailService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VisitRepository visitRepository;

    public Page<RestaurantSearchResponse> search(RestaurantSearchCondition condition, Pageable pageable) {
        return restaurantRepository.search(condition, pageable);
    }

    public RestaurantDetailResponse showDetails(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맛집입니다: id=" + id));

        List<MenuResponse> menuList = menuRepository.findByRestaurantIdIn(List.of(id))
                .stream()
                .map(MenuResponse::from)
                .toList();

        List<VisitResponse> visitList = visitRepository.findByRestaurantIdIn(List.of(id))
                .stream()
                .map(VisitResponse::from)
                .toList();

        int visitCount = visitList.size();
        double averageRating = visitList.stream()
                .mapToInt(VisitResponse::getRating)
                .average()
                .orElse(0.0);

        return RestaurantDetailResponse.from(restaurant, menuList, visitList, averageRating, visitCount);
    }
}
