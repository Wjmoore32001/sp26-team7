package edu.UNCG.sp26team7.repository;

import java.util.List;
import edu.UNCG.sp26team7.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT r.* FROM reviews r WHERE r.instructor_id = :instructorId", nativeQuery = true)
    List<Review> findByInstructorId(Long instructorId);

    @Query(value = "SELECT r.* FROM reviews r WHERE r.class_template_id = :classTemplateId", nativeQuery = true)
    List<Review> findByClassTemplateId(Long classTemplateId);

}
