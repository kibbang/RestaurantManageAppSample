package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.domain.Visit;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VisitServiceTest {

    @Autowired
    VisitService visitService;

    @Autowired
    EntityManager em;

    @Test
    void 평점이_1미만이면_예외발생() {
        assertThatThrownBy(
                () -> Visit.of(0, "test", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 평점이_5초과이면_예외발생() {
        assertThatThrownBy(
                () -> Visit.of(55, "test", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 방문기록_수정시_방문일은_변경되지_않는다() {

        Visit visit = Visit.of(2, "test", LocalDateTime.now());

        visit.changeInfo(3, "test2");

        assertThat(visit.getVisitDate()).isNotEqualTo(LocalDateTime.now());
    }

}