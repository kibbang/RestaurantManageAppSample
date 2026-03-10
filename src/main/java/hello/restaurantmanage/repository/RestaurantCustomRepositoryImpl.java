package hello.restaurantmanage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.restaurantmanage.dto.response.RestaurantSearchResponse;
import hello.restaurantmanage.dto.search.RestaurantSearchCondition;
import hello.restaurantmanage.enums.FoodCategory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static hello.restaurantmanage.domain.QRestaurant.*;
import static hello.restaurantmanage.domain.QVisit.*;

public class RestaurantCustomRepositoryImpl implements RestaurantCustomRepository {

    private final JPAQueryFactory query;

    public RestaurantCustomRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<RestaurantSearchResponse> search(RestaurantSearchCondition condition, Pageable pageable) {
        List<RestaurantSearchResponse> content = query
                .select(
                        Projections.constructor(RestaurantSearchResponse.class,
                                restaurant.id,
                                restaurant.name,
                                restaurant.address,
                                restaurant.foodCategory,
                                visit.rating.avg().coalesce(0.0).as("averageRating"),
                                visit.count().as("visitCount")
                        )
                )
                .from(restaurant)
                .leftJoin(visit).on(visit.restaurant.eq(restaurant))
                .where(
                        nameLike(condition.getName()),
                        foodCategoryEq(condition.getFoodCategory()),
                        addressLike(condition.getAddress())
                )
                .groupBy(restaurant.id)
                .having(
                        minRatingGoe(condition.getMinRate())
                )
                .orderBy(visit.rating.avg().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = query
                .select(restaurant.id)
                .from(restaurant)
                .leftJoin(visit).on(visit.restaurant.eq(restaurant))
                .where(
                        nameLike(condition.getName()),
                        foodCategoryEq(condition.getFoodCategory()),
                        addressLike(condition.getAddress())
                )
                .groupBy(restaurant.id)
                .having(
                        minRatingGoe(condition.getMinRate())
                )
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression nameLike(String name) {
        return name != null ? restaurant.name.contains(name) : null;
    }

    private BooleanExpression foodCategoryEq(FoodCategory foodCategory) {
        return foodCategory != null ? restaurant.foodCategory.eq(foodCategory) : null;
    }

    private BooleanExpression addressLike(String address) {
        return address != null ? restaurant.address.contains(address) : null;
    }

    private BooleanExpression minRatingGoe(Double minRate) {
        return minRate != null ? visit.rating.avg().goe(minRate) : null;
    }
}
