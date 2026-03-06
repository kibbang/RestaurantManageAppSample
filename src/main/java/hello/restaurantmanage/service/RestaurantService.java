package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.dto.request.RestaurantUpdateRequest;
import hello.restaurantmanage.dto.response.RestaurantResponse;
import hello.restaurantmanage.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long save(RestaurantCreateRequest request) {
        Restaurant restaurant = new Restaurant(
                request.getName(),
                request.getAddress(),
                request.getFoodCategory(),
                request.getDescription()
        );

        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = getRestaurant(id);
        return RestaurantResponse.from(restaurant);
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantResponse::from)
                .toList();
    }

    @Transactional
    public void modify(Long id, RestaurantUpdateRequest request) {
        Restaurant restaurant = getRestaurant(id);

        restaurant.changeInfo(
                request.getName(),
                request.getAddress(),
                request.getFoodCategory(),
                request.getDescription()
        );
    }

    @Transactional
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: id=" + id));
    }
}
