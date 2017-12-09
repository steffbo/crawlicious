package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    Page<Posting> findByUser(User user, Pageable pageable);

    List<Posting> findAllByUser(User user);
}
