package com.e_Commerce.E_CommerceApp.service.review;

import com.e_Commerce.E_CommerceApp.dto.ProductDto;
import com.e_Commerce.E_CommerceApp.dto.ReviewDto;
import com.e_Commerce.E_CommerceApp.model.Product;
import com.e_Commerce.E_CommerceApp.model.Review;
import com.e_Commerce.E_CommerceApp.model.User;
import com.e_Commerce.E_CommerceApp.reposiotory.ProductRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.ReviewRepository;
import com.e_Commerce.E_CommerceApp.request.AddReviewRequest;
import com.e_Commerce.E_CommerceApp.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public ReviewDto addReview(AddReviewRequest request){

        User user = userService.getUserById(request.getUserId());
        Product product = productRepository.findById(request.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());

        Review savedReview = reviewRepository.save(review);

        // تحديث متوسط التقييم للمنتج
        Double productAvgRating = getAverageRatingForProduct(product.getId());
        product.setRating(Math.floor(productAvgRating * 10) / 10);
        product.setRating(productAvgRating);
        productRepository.save(product);

        // تحويل المنتج إلى DTO لتجنب recursion
        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getBrand(),
                product.getQuantity(),
                product.getRating(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getImages() != null ?
                        product.getImages().stream().map(img -> img.getImage()).collect(Collectors.toList())
                        : null
        );

        // إنشاء ReviewDto
        return new ReviewDto(
                savedReview.getId(),
                savedReview.getReviewText(),
                savedReview.getRating(),
                productDto
        );
    }

    public Double getAverageRatingForProduct(Long productId) {
        Double avg = reviewRepository.findAverageRatingByProductId(productId);
        return avg != null ? avg : 0.0;
    }
}
