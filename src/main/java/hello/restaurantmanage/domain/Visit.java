package hello.restaurantmanage.domain;

import hello.restaurantmanage.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "visits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Integer rating;
    private String review;
    private LocalDateTime visitDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Visit of(Integer rating, String review, LocalDateTime visitDate) {
        return new Visit(rating, review, visitDate);
    }

    private Visit(Integer rating, String review, LocalDateTime visitDate) {
        this.rating = validRating(rating);
        this.review = review;
        this.visitDate = visitDate;
    }

    /**
     * restaurant 할당
     * @param restaurant
     */
    public void assignRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Visit 정보 변경
     * @param rating
     * @param review
     */
    public void changeInfo(Integer rating, String review) {
        this.rating = validRating(rating);
        this.review = review;
    }

    /**
     * Rating 유효 검증
     * @param rating
     * @return
     */
    private Integer validRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1 ~ 5점 사이여야합니다.");
        }

        return rating;
    }
}
