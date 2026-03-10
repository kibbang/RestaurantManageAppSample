package hello.restaurantmanage.controller.api;

import hello.restaurantmanage.common.ApiResponse;
import hello.restaurantmanage.dto.request.MenuCreateRequest;
import hello.restaurantmanage.dto.request.MenuUpdateRequest;
import hello.restaurantmanage.dto.response.MenuResponse;
import hello.restaurantmanage.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @GetMapping("/restaurants/{restaurantId}/menus")
    public ApiResponse<List<MenuResponse>> getMenus(@PathVariable Long restaurantId) {
        List<MenuResponse> menuList = menuService.getMenuList(restaurantId);

        return new ApiResponse<>(200, "GET LIST SUCCESS", menuList);
    }

    @PostMapping("/restaurants/{restaurantId}/menus")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuResponse> createMenu(@PathVariable Long restaurantId, @Valid @RequestBody MenuCreateRequest request) {
        menuService.registerMenu(restaurantId, request);

        return new ApiResponse<>(201, "CREATE MENU SUCCESS", null);
    }

    @GetMapping("/menus/{id}")
    public ApiResponse<MenuResponse> showMenu(@PathVariable Long id) {
        MenuResponse menu = menuService.getMenu(id);

        return new ApiResponse<>(200, "GET MENU SUCCESS", menu);
    }

    @PutMapping("/menus/{id}")
    public ApiResponse<MenuResponse> modifyMenu(@PathVariable Long id, @Valid @RequestBody MenuUpdateRequest request) {

        menuService.modifyMenu(id, request);

        return new ApiResponse<>(200, "MODIFY MENU SUCCESS", null);
    }

    @DeleteMapping("/menus/{id}")
    public ApiResponse<MenuResponse> removeMenu(@PathVariable Long id) {
        menuService.removeMenu(id);
        return new ApiResponse<>(200, "DELETE MENU SUCCESS", null);
    }
}
