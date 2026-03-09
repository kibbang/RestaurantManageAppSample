package hello.restaurantmanage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitResponse {
    private Long id;
    private Integer rating;
    private String review;
    private LocalDateTime visitDate;
    private Long restaurantId;
    private String restaurantName;
    private LocalDateTime createdAt;
}
