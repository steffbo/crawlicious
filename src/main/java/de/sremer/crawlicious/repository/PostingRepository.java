package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findAllByUser(User user);

    Page<Posting> findByUser(User user, Pageable pageable);

    @Query(value = "SELECT temp.posting_id, temp.link, temp.title, temp.user_id, temp.date " +
            "FROM (" +
            "select p.posting_id, p.link, p.title, p.user_id, p.date " +
            "from posting as p " +
            "inner join user as u on p.user_id = u.user_id and u.user_id = :userId " +
            "inner join posting_tag as pt on p.posting_id = pt.post_id " +
            "inner join tag as t on pt.tag_id = t.tag_id " +
            "where t.name in :tags " +
            ") as temp " +
            "GROUP BY temp.posting_id " +
            "having count(*) >= :#{#tags.size()}", nativeQuery = true)
    List<Posting> findByUserAndTags(@Param(value = "userId") long userId, @Param(value = "tags") List<String> tags);
}
