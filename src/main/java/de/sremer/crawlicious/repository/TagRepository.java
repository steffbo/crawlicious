package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag findTagByName(String name);

    List<Tag> findByNameIn(Collection<String> names);

    List<Tag> findByNameIn(String[] names);

    @Query(nativeQuery = true, value = """
            select DISTINCT t.name, t.id from tag as t
                join posting_tag as pt on t.id = pt.tag_id
                join posting as p on pt.post_id = p.id
                join userdata as u on p.user_id = u.id
                where u.id = :userId order by t.name
            """)
    List<Tag> findEverythingForUserId(UUID userId);

    @Query(nativeQuery = true, value = """
            select DISTINCT t.name from tag as t
                join posting_tag as pt on t.id = pt.tag_id
                join posting as p on pt.post_id = p.id
                join userdata as u on p.user_id = u.id
                where u.id = :userId order by t.name
            """)
    List<String> findAllTagNamesForUserId(UUID userId);

    @Query(nativeQuery = true, value = """
            select DISTINCT t.name, t.id
                from tag t
                left join posting_tag pt on t.id = pt.tag_id
                where pt.post_id IN (
                    select DISTINCT p.id
                    from posting p
                    left join posting_tag pt on p.id = pt.post_id
                    left join tag t on pt.tag_id = t.id
                    left join userdata u on p.user_id = u.id
                    where u.id = :userId
                    and t.name = :tag
                )
            """)
    List<Tag> findPossibleForUserIdAndSelectedTag(UUID userId, String tag);

}
