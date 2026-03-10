package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Menu;
import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.domain.Visit;
import hello.restaurantmanage.dto.response.RestaurantDetailResponse;
import hello.restaurantmanage.dto.response.RestaurantSearchResponse;
import hello.restaurantmanage.dto.search.RestaurantSearchCondition;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SearchTest {
    @Autowired
    RestaurantDetailService restaurantDetailService;

    @Autowired
    EntityManager em;

    @Test
    public void 카테고리로_검색한다() {
        Restaurant restaurant1 = new Restaurant("test", "주소1", FoodCategory.KOREAN, "test");
        Restaurant restaurant2 = new Restaurant("2번 레스토랑", "어드레스2", FoodCategory.JAPANESE, "test");
        Restaurant restaurant3 = new Restaurant("맛집3", "test3", FoodCategory.WESTERN, "test");

        em.persist(restaurant1);
        em.persist(restaurant2);
        em.persist(restaurant3);

        em.flush();
        em.clear();

        RestaurantSearchCondition searchCondition = new RestaurantSearchCondition(null, FoodCategory.JAPANESE, null, null);

        Pageable pageable = PageRequest.of(0, 10);

        Page<RestaurantSearchResponse> result = restaurantDetailService.search(searchCondition, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("2번 레스토랑");
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void 이름_부분일치로_검색한다() {
        Restaurant restaurant1 = new Restaurant("test", "주소1", FoodCategory.KOREAN, "test");
        Restaurant restaurant2 = new Restaurant("2번 레스토랑", "어드레스2", FoodCategory.JAPANESE, "test");
        Restaurant restaurant3 = new Restaurant("맛집3", "test3", FoodCategory.WESTERN, "test");

        em.persist(restaurant1);
        em.persist(restaurant2);
        em.persist(restaurant3);

        em.flush();
        em.clear();

        RestaurantSearchCondition searchCondition = new RestaurantSearchCondition("2번", null, null, null);

        Pageable pageable = PageRequest.of(0, 10);

        Page<RestaurantSearchResponse> result = restaurantDetailService.search(searchCondition, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).contains("2번");
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void 조건_없으면_전체_조회된다() {
        Restaurant restaurant1 = new Restaurant("test", "주소1", FoodCategory.KOREAN, "test");
        Restaurant restaurant2 = new Restaurant("2번 레스토랑", "어드레스2", FoodCategory.JAPANESE, "test");
        Restaurant restaurant3 = new Restaurant("맛집3", "test3", FoodCategory.WESTERN, "test");

        em.persist(restaurant1);
        em.persist(restaurant2);
        em.persist(restaurant3);

        em.flush();
        em.clear();

        RestaurantSearchCondition searchCondition = new RestaurantSearchCondition(null, null, null, null);

        Pageable pageable = PageRequest.of(0, 10);

        Page<RestaurantSearchResponse> result = restaurantDetailService.search(searchCondition, pageable);

        assertThat(result.getContent()).hasSize(3);
//        assertThat(result.getContent().get(0).getName()).contains("2번");
        assertThat(result.getTotalElements()).isEqualTo(3);
    }

    @Test
    void 평균평점_이상_맛집만_조회한다() {
        // given
        Restaurant restaurant1 = new Restaurant("맛집1", "서울", FoodCategory.KOREAN, "설명");
        Restaurant restaurant2 = new Restaurant("맛집2", "서울", FoodCategory.KOREAN, "설명");
        Restaurant restaurant3 = new Restaurant("맛집3", "서울", FoodCategory.KOREAN, "설명");

        em.persist(restaurant1);
        em.persist(restaurant2);
        em.persist(restaurant3);

        // 맛집1: 평균 4.5
        Visit v1 = Visit.of(5, "좋아요", LocalDateTime.now());
        v1.assignRestaurant(restaurant1);
        em.persist(v1);

        Visit v2 = Visit.of(4, "괜찮아요", LocalDateTime.now());
        v2.assignRestaurant(restaurant1);
        em.persist(v2);

        // 맛집2: 평균 2.0
        Visit v3 = Visit.of(2, "별로", LocalDateTime.now());
        v3.assignRestaurant(restaurant2);
        em.persist(v3);

        Visit v4 = Visit.of(2, "그저그럼", LocalDateTime.now());
        v4.assignRestaurant(restaurant2);
        em.persist(v4);

        // 맛집3: 평균 4.0
        Visit v5 = Visit.of(4, "맛있다", LocalDateTime.now());
        v5.assignRestaurant(restaurant3);
        em.persist(v5);

        Visit v6 = Visit.of(4, "또올게", LocalDateTime.now());
        v6.assignRestaurant(restaurant3);
        em.persist(v6);

        em.flush();
        em.clear();

        // when
        RestaurantSearchCondition condition = new RestaurantSearchCondition(null, null, null, 4.0);
        Pageable pageable = PageRequest.of(0, 10);
        Page<RestaurantSearchResponse> result = restaurantDetailService.search(condition, pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting("name")
                .containsExactlyInAnyOrder("맛집1", "맛집3");
    }

    @Test
    void 조건_복합_검색한다() {
        // given
        Restaurant restaurant1 = new Restaurant("서울맛집", "서울 강남구", FoodCategory.KOREAN, "설명");
        Restaurant restaurant2 = new Restaurant("서울분식", "서울 마포구", FoodCategory.KOREAN, "설명");
        Restaurant restaurant3 = new Restaurant("부산초밥", "부산 해운대구", FoodCategory.JAPANESE, "설명");
        Restaurant restaurant4 = new Restaurant("서울스시", "서울 종로구", FoodCategory.JAPANESE, "설명");

        em.persist(restaurant1);
        em.persist(restaurant2);
        em.persist(restaurant3);
        em.persist(restaurant4);

        // 서울맛집: 평균 5.0
        Visit v1 = Visit.of(5, "최고", LocalDateTime.now());
        v1.assignRestaurant(restaurant1);
        em.persist(v1);

        // 서울분식: 평균 2.0
        Visit v2 = Visit.of(2, "별로", LocalDateTime.now());
        v2.assignRestaurant(restaurant2);
        em.persist(v2);

        // 부산초밥: 평균 5.0
        Visit v3 = Visit.of(5, "맛있다", LocalDateTime.now());
        v3.assignRestaurant(restaurant3);
        em.persist(v3);

        // 서울스시: 평균 4.0
        Visit v4 = Visit.of(4, "괜찮아요", LocalDateTime.now());
        v4.assignRestaurant(restaurant4);
        em.persist(v4);

        em.flush();
        em.clear();

        // when - 이름에 "서울" 포함 + 한식 + 평균 3.0 이상
        RestaurantSearchCondition condition = new RestaurantSearchCondition("서울", FoodCategory.KOREAN, null, 3.0);
        Pageable pageable = PageRequest.of(0, 10);
        Page<RestaurantSearchResponse> result = restaurantDetailService.search(condition, pageable);

        // then - "서울맛집"만 해당 (서울분식은 평점 미달, 나머지는 카테고리 미달)
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("서울맛집");
    }

    @Test
    void 맛집_상세_조회한다() {
        // given
        Restaurant restaurant = new Restaurant("서울맛집", "서울 강남구", FoodCategory.KOREAN, "맛있는 한식집");
        em.persist(restaurant);

        Menu m1 = Menu.of("김치찌개", 9000, "얼큰한 김치찌개", true);
        m1.assignRestaurant(restaurant);
        em.persist(m1);

        Menu m2 = Menu.of("된장찌개", 8000, "구수한 된장찌개", false);
        m2.assignRestaurant(restaurant);
        em.persist(m2);

        Visit v1 = Visit.of(5, "최고의 맛집", LocalDateTime.now());
        v1.assignRestaurant(restaurant);
        em.persist(v1);

        Visit v2 = Visit.of(4, "또 올게요", LocalDateTime.now());
        v2.assignRestaurant(restaurant);
        em.persist(v2);

        Visit v3 = Visit.of(3, "보통이에요", LocalDateTime.now());
        v3.assignRestaurant(restaurant);
        em.persist(v3);

        em.flush();
        em.clear();

        // when
        RestaurantDetailResponse result = restaurantDetailService.showDetails(restaurant.getId());

        // then
        assertThat(result.getName()).isEqualTo("서울맛집");
        assertThat(result.getFoodCategory()).isEqualTo(FoodCategory.KOREAN);
        assertThat(result.getMenus()).hasSize(2);
        assertThat(result.getVisits()).hasSize(3);
        assertThat(result.getVisitCount()).isEqualTo(3);
        assertThat(result.getAverageRate()).isEqualTo(4.0); // (5+4+3) / 3
    }

}
