package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostingRepository extends JpaRepository<Posting, UUID> {

    List<Posting> findAllByUser(User user);

    Page<Posting> findByUser(User user, Pageable pageable);

    @Query(nativeQuery = true, value = """
              SELECT p.*
              FROM posting p
                       JOIN posting_tag pt ON p.id = pt.posting_id
                       JOIN tag t ON pt.tag_id = t.id
              WHERE p.user_id = :userId
                AND lower(t.name) IN (:tags)
              GROUP BY p.id
              HAVING COUNT(DISTINCT t.name) >= :#{#tags.size()}
            """
            , countQuery = """
            SELECT COUNT(p)
              FROM posting p
                       JOIN posting_tag pt ON p.id = pt.posting_id
                       JOIN tag t ON pt.tag_id = t.id
              WHERE p.user_id = :userId
                AND lower(t.name) IN (:tags)
              GROUP BY p.id
              HAVING COUNT(DISTINCT t.name) >= :#{#tags.size()}
            """
    )
    Page<Posting> findByUserAndTags(UUID userId, List<String> tags, Pageable pageable);
}
