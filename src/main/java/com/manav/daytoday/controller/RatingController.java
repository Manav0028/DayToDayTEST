package com.manav.daytoday.controller;

import com.manav.daytoday.response_model.ProductRatings;
import com.manav.daytoday.response_model.RatingResponse;
import com.manav.daytoday.response_model.UserRating;
import com.manav.daytoday.auth_model.CustomUserDetails;
import com.manav.daytoday.db_model.Product;
import com.manav.daytoday.db_model.ProductRating;
import com.manav.daytoday.db_model.User;
import com.manav.daytoday.repository.ProductRepository;
import com.manav.daytoday.repository.RatingRepository;
import com.manav.daytoday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/addRating")
    public @ResponseBody String addRating(Authentication authentication, @RequestBody RatingResponse ratingResponse) {
        String message;
        Optional<Product> prod = productRepository.findByProductName(ratingResponse.getName());
        if(prod.isPresent()) {
            Product product = prod.get();
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            if (ratingRepository.findByProductIdAndUserId(product.getId(), user.getId()).isPresent()) {
                message = "Already Rated";
            } else {
                ProductRating rating = new ProductRating();
                rating.setProductId(product.getId());
                rating.setUserId(user.getId());
                rating.setRating(ratingResponse.getRating());
                ratingRepository.save(rating);
                message = "Added";
            }
        } else {
            message = "Product Not Found To Rate";
        }
        return message;
    }

    @PutMapping("/updateRating")
    public @ResponseBody String updateRating(Authentication authentication, @RequestBody RatingResponse ratingResponse) {
        String message = null;
        try {
            Optional<Product> prod = productRepository.findByProductName(ratingResponse.getName());
            if (prod.isPresent()) {
                Product product = prod.get();
                CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
                ProductRating rating = ratingRepository.findByProductIdAndUserId(product.getId(), user.getId()).get();
                rating.setRating(ratingResponse.getRating());
                ratingRepository.save(rating);
                message = "Updated";
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            message = "Product Not Found To Update";
        }
        return message;
    }

    @GetMapping("/getRatings/{productName}")
    public @ResponseBody
    ResponseEntity<Object> productRating(@PathVariable String productName) {
        ProductRatings response = new ProductRatings();
        response.setProductName(productName);
        List<UserRating> users= new ArrayList<>();
        AtomicReference<Double> avg = new AtomicReference<>(0.00);
        AtomicInteger count = new AtomicInteger();
        try {
            Optional<Product> prod = productRepository.findByProductName(productName);
            if (prod.isPresent()) {
                Product product = prod.get();

                Iterable<ProductRating> ratings = ratingRepository.findAllByProductId(product.getId());
                ratings.forEach(r -> {
                    User user = userRepo.findById(r.getUserId()).get();
                    users.add(new UserRating(user.getUsername(), r.getRating()));
                    count.getAndIncrement();
                    avg.updateAndGet(v -> (v + r.getRating()));
                });

                avg.updateAndGet(v -> v / count.get());
                response.setAvgRating(avg.get());
                response.setUsers(users);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
