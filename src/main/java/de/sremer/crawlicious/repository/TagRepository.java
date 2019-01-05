package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByName(String name);

    List<Tag> findByNameIn(Collection<String> names);

    List<Tag> findByNameIn(String[] names);

    @Query(value = "select DISTINCT t.name, t.tag_id from tag as t\n" +
            "join posting_tag as pt on t.tag_id = pt.tag_id\n" +
            "join posting as p on pt.post_id = p.posting_id\n" +
            "join user as u on p.user_id = u.user_id\n" +
            "where u.user_id = :userId order by t.name", nativeQuery = true)
    List<Tag> findEverythingForUserId(@Param(value = "userId") long userId);

    @Query(value = "select DISTINCT t.name from tag as t\n" +
            "join posting_tag as pt on t.tag_id = pt.tag_id\n" +
            "join posting as p on pt.post_id = p.posting_id\n" +
            "join user as u on p.user_id = u.user_id\n" +
            "where u.user_id = :userId order by t.name", nativeQuery = true)
    List<String> findAllTagNamesForUserId(@Param(value = "userId") long userId);

    @Query(value = "select DISTINCT t.name, t.tag_id\n" +
            "from tag t\n" +
            "left join posting_tag pt on t.tag_id = pt.tag_id\n" +
            "where pt.post_id IN (\n" +
            "\tselect DISTINCT p.posting_id \n" +
            "\tfrom posting p\n" +
            "\tleft join posting_tag pt on p.posting_id = pt.post_id\n" +
            "\tleft join tag t on pt.tag_id = t.tag_id\n" +
            "\tleft join user u on p.user_id = u.user_id\n" +
            "\twhere u.user_id = :userId\n" +
            "\tand t.name = :tag\n" +
            ")", nativeQuery = true)
    List<Tag> findPossibleForUserIdAndSelectedTag(@Param(value = "userId") long userId,
                                                  @Param(value = "tag") String tag);
}
