package hello.restaurantmanage.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitUpdateRequest {
    @Min(1)
    @Max(5)
    private Integer rating;
    private String review;
}
