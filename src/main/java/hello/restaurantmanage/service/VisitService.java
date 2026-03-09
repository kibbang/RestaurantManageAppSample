package hello.restaurantmanage.service;

import hello.restaurantmanage.domain.Restaurant;
import hello.restaurantmanage.domain.Visit;
import hello.restaurantmanage.dto.request.VisitCreateRequest;
import hello.restaurantmanage.dto.request.VisitUpdateRequest;
import hello.restaurantmanage.dto.response.VisitResponse;
import hello.restaurantmanage.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitService {

    private final RestaurantService restaurantService;
    private final VisitRepository visitRepository;

    /**
     * DTO 변환 visit 목록 조회
     * @param restaurantId
     * @return
     */
    public List<VisitResponse> getVisitList(Long restaurantId) {
        List<Visit> visitList = visitRepository.findByRestaurantId(restaurantId);

        return visitList.stream()
                .map(VisitResponse::from)
                .toList();
    }

    /**
     * Visit 등록
     * @param restaurantId
     * @param request
     */
    @Transactional
    public void saveVisit(Long restaurantId, VisitCreateRequest request) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        Visit visit = Visit.of(
                request.getRating(),
                request.getReview(),
                request.getVisitDate()
        );

        visit.assignRestaurant(restaurant);

        visitRepository.save(visit);
    }

    /**
     * Visit 단건 조회
     * @param id
     * @return
     */
    public VisitResponse showVisit(Long id) {
        Visit visit = getVisit(id);

        return VisitResponse.from(visit);
    }

    /**
     * Visit 수정
     * @param id
     * @param request
     */
    @Transactional
    public void modifyVisit(Long id, VisitUpdateRequest request) {
        Visit visit = getVisit(id);

        visit.changeInfo(
                request.getRating(),
                request.getReview()
        );
    }

    /**
     * 삭제
     * @param id
     */
    @Transactional
    public void removeVisit(Long id) {
        Visit visit = getVisit(id);
        visitRepository.delete(visit);
    }

    private Visit getVisit(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 visit 입니다"));
    }
}
