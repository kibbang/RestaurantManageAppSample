package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.dto.request.RestaurantUpdateRequest;
import hello.restaurantmanage.dto.response.RestaurantResponse;
import hello.restaurantmanage.enums.FoodCategory;
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
                FoodCategory.valueOf(String.valueOf(request.getFoodCategory())),
                request.getDescription()
        );

        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = getRestaurant(id);

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getFoodCategory(),
                restaurant.getDescription(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    public List<RestaurantResponse> findAll() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        return restaurantList.stream().map(restaurant -> new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getFoodCategory(),
                restaurant.getDescription(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        )).toList();
    }

    @Transactional
    public void modify(RestaurantUpdateRequest request) {
        Restaurant restaurant = getRestaurant(request.getId());

        restaurant.changeInfo(
                request.getName(),
                request.getAddress(),
                request.getCategory(),
                request.getDescription()
        );
    }

    @Transactional
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow();
    }
}
