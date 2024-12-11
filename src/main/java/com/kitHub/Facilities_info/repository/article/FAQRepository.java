package com.kitHub.Facilities_info.repository.article;

import com.kitHub.Facilities_info.domain.article.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    Optional<FAQ> findById(Long id);
}
