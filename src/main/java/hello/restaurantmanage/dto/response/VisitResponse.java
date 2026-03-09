package hello.restaurantmanage.dto.response;

import hello.restaurantmanage.domain.Visit;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitResponse {
    private Long id;
    private Integer rating;
    private String review;
    private LocalDateTime visitDate;
    private Long restaurantId;
    private String restaurantName;
    private LocalDateTime createdAt;

    public static VisitResponse from(Visit visit) {
        return new VisitResponse(
                visit.getId(),
                visit.getRating(),
                visit.getReview(),
                visit.getVisitDate(),
                visit.getRestaurant().getId(),
                visit.getRestaurant().getName(),
                visit.getCreatedAt()
        );
    }

    private VisitResponse(Long id, Integer rating, String review, LocalDateTime visitDate, Long restaurantId, String restaurantName, LocalDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.visitDate = visitDate;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.createdAt = createdAt;
    }
}
