package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class VisitServiceTest {

    @Autowired
    VisitService visitService;

    @Test
    void 평점이_1미만이면_예외발생() {
        assertThatThrownBy(
                () -> Visit.of(0, "test", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 평점이_5초과이면_예외발생() {
        assertThatThrownBy(
                () -> Visit.of(55, "test", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 평점이_null이면_예외발생() {
        assertThatThrownBy(
                () -> Visit.of(null, "test", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 방문기록_수정시_방문일은_변경되지_않는다() {
        LocalDateTime visitDate = LocalDateTime.of(2025, 1, 1, 12, 0);
        Visit visit = Visit.of(2, "test", visitDate);

        visit.changeInfo(3, "test2");

        assertThat(visit.getVisitDate()).isEqualTo(visitDate);
        assertThat(visit.getRating()).isEqualTo(3);
        assertThat(visit.getReview()).isEqualTo("test2");
    }
}
