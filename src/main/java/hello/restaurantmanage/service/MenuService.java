package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Menu;
import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.dto.request.MenuCreateRequest;
import hello.restaurantmanage.dto.request.MenuUpdateRequest;
import hello.restaurantmanage.dto.response.MenuResponse;
import hello.restaurantmanage.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final RestaurantService restaurantService;
    private final MenuRepository menuRepository;

    @Transactional
    public void registerMenu(Long restaurantId, MenuCreateRequest request) {

        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        Menu menu = Menu.of(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getIsPopular()
        );

        menu.assignRestaurant(restaurant);

        menuRepository.save(menu);
    }

    /**
     * dto 조립용
     * @param restaurantId
     * @return
     */
    public List<MenuResponse> getMenuList(Long restaurantId) {
        List<Menu> allMenus = getAllMenus(restaurantId);

        return allMenus.stream().map(MenuResponse::from).toList();
    }

    /**
     * dto 조립용
     * @param id
     * @return
     */
    public MenuResponse getMenu(Long id) {
        Menu menu = showMenu(id);

        return MenuResponse.from(menu);
    }

    /**
     * 내부 조회용 순수 엔티티 반환용
     * @param restaurantId
     * @return
     */
    public List<Menu> getAllMenus(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId);
    }


    /**
     * 내부 조회용 순수 엔티티 반환용
     * @param id
     * @return
     */
    public Menu showMenu(Long id) {

        return menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: id=" + id));
    }

    /**
     * 수정
     * @param id
     * @param request
     */
    @Transactional
    public void modifyMenu(Long id, MenuUpdateRequest request) {
        Menu menu = showMenu(id);

        menu.changeInfo(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getIsPopular()
        );
    }

    /**
     * 삭제
     * @param id
     */
    @Transactional
    public void removeMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
