package hello.restaurantmanage.controller.api;

import hello.restaurantmanage.common.CommonApiResponseEntity;
import hello.restaurantmanage.dto.request.RestaurantCreateRequest;
import hello.restaurantmanage.dto.request.RestaurantUpdateRequest;
import hello.restaurantmanage.dto.response.RestaurantResponse;
import hello.restaurantmanage.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.*;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @GetMapping
    public CommonApiResponseEntity<?> getRestaurants() {
        List<RestaurantResponse> restaurantsList = restaurantService.findAll();

        return new CommonApiResponseEntity<>(SC_OK, "GET LIST SUCCESS", restaurantsList);
    }

    @PostMapping
    public CommonApiResponseEntity<?> createRestaurant(@RequestBody RestaurantCreateRequest request) {

        restaurantService.save(request);

        return new CommonApiResponseEntity<>(
                SC_CREATED,
                "CREATE SUCCESS",
                null
        );
    }

    @GetMapping("/{id}")
    public CommonApiResponseEntity<?> showRestaurant(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.findById(id);

        return new CommonApiResponseEntity<>(SC_OK, "FIND SUCCESS", restaurant);
    }

    @PostMapping("/{id}")
    public CommonApiResponseEntity<?>updateRestaurantInfo(@PathVariable Long id, @RequestBody RestaurantUpdateRequest request) {
        request.setId(id);

        restaurantService.modify(request);

        return new CommonApiResponseEntity<>(SC_OK, "UPDATE SUCCESS", null);
    }

    @PostMapping("/{id}/delete")
    public CommonApiResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);

        return new CommonApiResponseEntity<>(SC_OK, "DELETE SUCCESS", null);
    }
}
