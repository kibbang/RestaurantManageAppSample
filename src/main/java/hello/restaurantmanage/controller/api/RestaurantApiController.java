package hello.restaurantmanage.controller.api;

import hello.restaurantmanage.common.ApiResponse;
import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.dto.request.RestaurantUpdateRequest;
import hello.restaurantmanage.dto.response.RestaurantDetailResponse;
import hello.restaurantmanage.dto.response.RestaurantResponse;
import hello.restaurantmanage.dto.response.RestaurantSearchResponse;
import hello.restaurantmanage.dto.search.RestaurantSearchCondition;
import hello.restaurantmanage.service.RestaurantDetailService;
import hello.restaurantmanage.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantService restaurantService;
    private final RestaurantDetailService restaurantDetailService;

    @GetMapping
    public ApiResponse<List<RestaurantResponse>> getRestaurants() {
        List<RestaurantResponse> restaurantsList = restaurantService.findAll();
        return new ApiResponse<>(200, "GET LIST SUCCESS", restaurantsList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createRestaurant(@Valid @RequestBody RestaurantCreateRequest request) {
        restaurantService.save(request);
        return new ApiResponse<>(201, "CREATE SUCCESS", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<RestaurantResponse> showRestaurant(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.findById(id);
        return new ApiResponse<>(200, "FIND SUCCESS", restaurant);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateRestaurantInfo(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantUpdateRequest request) {
        restaurantService.modify(id, request);
        return new ApiResponse<>(200, "UPDATE SUCCESS", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);
        return new ApiResponse<>(200, "DELETE SUCCESS", null);
    }

    @GetMapping("/search")
    public ApiResponse<Page<RestaurantSearchResponse>> searchRestaurant(
            @ModelAttribute RestaurantSearchCondition condition,
            Pageable pageable) {
        Page<RestaurantSearchResponse> search = restaurantDetailService.search(condition, pageable);
        return new ApiResponse<>(200, "SEARCH SUCCESS", search);
    }

    @GetMapping("/{id}/detail")
    public ApiResponse<RestaurantDetailResponse> showDetail(@PathVariable Long id) {

        RestaurantDetailResponse restaurantDetailResponse = restaurantDetailService.showDetails(id);

        return new ApiResponse<>(200, "RESTAURANT DETAIL LOAD SUCCESS", restaurantDetailResponse);
    }
}
