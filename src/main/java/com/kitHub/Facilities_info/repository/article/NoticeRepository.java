package com.kitHub.Facilities_info.repository.article;

import com.kitHub.Facilities_info.domain.article.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findById(Long id);
}
