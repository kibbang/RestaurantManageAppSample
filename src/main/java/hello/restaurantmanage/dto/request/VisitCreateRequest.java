package hello.restaurantmanage.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitCreateRequest {
    @NotNull
    private LocalDateTime visitDate;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    private String review;
}
