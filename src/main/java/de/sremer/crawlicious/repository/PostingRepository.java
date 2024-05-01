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
            SELECT temp.id, temp.link, temp.title, temp.user_id, temp.date, temp.secret
                FROM (
                select p.id, p.link, p.title, p.user_id, p.date, p.secret
                from posting as p
                inner join userdata as u on p.user_id = u.id and u.id = :userId
                inner join posting_tag as pt on p.id = pt.posting_id
                inner join tag as t on pt.tag_id = t.id
                where lower(t.name) in :tags
                ) as temp
                GROUP BY temp.id
                having count(*) >= :#{#tags.size()}
                ORDER BY temp.date desc
            """
    )
    List<Posting> findByUserAndTags(UUID userId, List<String> tags);
}
