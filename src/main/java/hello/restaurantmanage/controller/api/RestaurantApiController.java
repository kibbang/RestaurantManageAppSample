package hello.restaurantmanage.controller.api;

import hello.restaurantmanage.common.ApiResponse;
import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.dto.request.RestaurantUpdateRequest;
import hello.restaurantmanage.dto.response.RestaurantResponse;
import hello.restaurantmanage.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getRestaurants() {
        List<RestaurantResponse> restaurantsList = restaurantService.findAll();

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), "GET LIST SUCCESS", restaurantsList)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createRestaurant(@Valid @RequestBody RestaurantCreateRequest request) {
        restaurantService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "CREATE SUCCESS", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> showRestaurant(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.findById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), "FIND SUCCESS", restaurant)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateRestaurantInfo(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantUpdateRequest request) {
        restaurantService.modify(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), "UPDATE SUCCESS", null)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), "DELETE SUCCESS", null)
        );
    }
}
