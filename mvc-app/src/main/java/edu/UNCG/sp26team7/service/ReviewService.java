package edu.UNCG.sp26team7.service;

import edu.UNCG.sp26team7.entity.Review;
import edu.UNCG.sp26team7.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByClassTemplateId(Long classTemplateId) {
        return reviewRepository.findByClassTemplateId(classTemplateId);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id).map(review -> {
            if (reviewDetails.getRating() != null) {
                review.setRating(reviewDetails.getRating());
            }
            if (reviewDetails.getComment() != null) {
                review.setComment(reviewDetails.getComment());
            }
            if (reviewDetails.getReplyText() != null) {
                review.setReplyText(reviewDetails.getReplyText());
            }
            return reviewRepository.save(review);
        }).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
