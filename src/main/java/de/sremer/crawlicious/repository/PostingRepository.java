package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findAllByUser(User user);

    Page<Posting> findByUser(User user, Pageable pageable);

    //    @Query(value = "SELECT temp.posting_id, temp.link, temp.title, temp.user_id, temp.date" +
//            "FROM (" +
//            "select p.posting_id, p.link, p.title, p.user_id, p.date" +
//            "from posting as p" +
//            "inner join user as u on p.user_id = u.user_id and u.user_id = :userId" +
//            "inner join posting_tag as pt on p.posting_id = pt.post_id" +
//            "inner join tag as t on pt.tag_id = t.tag_id" +
//            "where t.name in (:tags)" +
//            ") as temp" +
//            "GROUP BY temp.posting_id" +
//            "having count(*) >= 2")
//    @Query(value = "SELECT temp.posting_id as posting_id, temp.link as link, temp.title as title, temp.user_id as user_id, temp.date as date " +
//    @Query(value = "SELECT * from (select rownum() as RN, temp.*" +
//            "FROM (" +
//            "select p.posting_id, p.link, p.title, p.user_id, p.date " +
//            "from posting as p " +
//            "inner join user as u on p.user_id = u.user_id and u.user_id = :userId " +
//            "inner join posting_tag as pt on p.posting_id = pt.post_id " +
//            "inner join tag as t on pt.tag_id = t.tag_id " +
//            "where t.name in (:tags)" +
//            ") as temp " +
//            "GROUP BY temp.posting_id " +
//            "having count(*) >= 2 " +
//            "where RN between ?#{ #pageable.offset -1} and ?#{ #pageable.offset + #pageable.pageSize}"
////            "\n#pageable\n",
//            ,countQuery = "SELECT count(*) temp.posting_id as posting_id, temp.link as link, temp.title as title, temp.user_id as user_id, temp.date as date " +
//                    "FROM (" +
//                    "select p.posting_id, p.link, p.title, p.user_id, p.date " +
//                    "from posting as p " +
//                    "inner join user as u on p.user_id = u.user_id and u.user_id = :userId " +
//                    "inner join posting_tag as pt on p.posting_id = pt.post_id " +
//                    "inner join tag as t on pt.tag_id = t.tag_id " +
//                    "where t.name in (:tags)" +
//                    ") as temp " +
//                    "GROUP BY temp.posting_id " +
//                    "having count(*) >= 2",
//            nativeQuery = true)
//    Page<Posting> findByUserAndTags(@Param("userId") long userId, @Param("tags") List<Tag> tags, Pageable pageable);

    Page<Posting> findByUserAndTags(User user, List<Tag> tags, Pageable pageable);

    @Query(value = "select * from posting as p\n" +
            "inner join posting_tag as pt on pt.post_id = p.posting_id\n" +
            "inner join tag as t on pt.tag_id = t.tag_id\n" +
            "where p.user_id = ?1 and t.name = ?2", nativeQuery = true)
    List<Posting> findByUserAndTags(@Param(value = "userId") long userId, @Param(value = "tag") String tag);

    List<Posting> findByUserAndTags(User user, Set<Tag> tags);
}
