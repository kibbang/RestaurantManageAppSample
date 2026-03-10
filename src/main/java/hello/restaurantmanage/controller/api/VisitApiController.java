package hello.restaurantmanage.controller.api;

import hello.restaurantmanage.common.ApiResponse;
import hello.restaurantmanage.dto.request.VisitCreateRequest;
import hello.restaurantmanage.dto.request.VisitUpdateRequest;
import hello.restaurantmanage.dto.response.VisitResponse;
import hello.restaurantmanage.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VisitApiController {
    private final VisitService visitService;
    /**
     * 해당 맛집의 방문기록 목록 조회
     */
    @GetMapping("/restaurants/{restaurantId}/visits")
    public ApiResponse<List<VisitResponse>> getVisitList(@PathVariable Long restaurantId) {
        List<VisitResponse> visitList = visitService.getVisitList(restaurantId);

        return new ApiResponse<>(200, "GET LIST SUCCESS", visitList);
    }

    /**
     * 방문기록 등록
     */
    @PostMapping("/restaurants/{restaurantId}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createVisit(@PathVariable Long restaurantId, @Valid @RequestBody VisitCreateRequest request) {
        visitService.saveVisit(restaurantId, request);

        return new ApiResponse<>(201, "CREATE SUCCESS", null);
    }

    /**
     * 방문기록 단건 조회
     */
    @GetMapping("/visits/{id}")
    public ApiResponse<VisitResponse> showVisit(@PathVariable Long id) {
        VisitResponse visitResponse = visitService.showVisit(id);

        return new ApiResponse<>(200, "GET SUCCESS", visitResponse);
    }

    /**
     * 방문기록 수정
     */
    @PutMapping("/visits/{id}")
    public ApiResponse<Void> updateVisit(@PathVariable Long id, @Valid @RequestBody VisitUpdateRequest request) {
        visitService.modifyVisit(id, request);

        return new ApiResponse<>(200, "MODIFY VISIT SUCCESS", null);
    }

    /**
     * 방문기록 삭제
     */
    @DeleteMapping("/visits/{id}")
    public ApiResponse<Void> deleteVisit(@PathVariable Long id) {
        visitService.removeVisit(id);

        return new ApiResponse<>(200, "DELETE VISIT SUCCESS", null);
    }
}
