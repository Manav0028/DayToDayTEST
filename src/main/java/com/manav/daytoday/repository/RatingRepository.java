package com.manav.daytoday.repository;

import com.manav.daytoday.db_model.ProductRating;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingRepository extends CrudRepository<ProductRating, Integer> {
    Iterable<ProductRating> findAllByProductId(Integer id);

    Optional<ProductRating> findByProductIdAndUserId(Integer productId, Integer userId);

}
