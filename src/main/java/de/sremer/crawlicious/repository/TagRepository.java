package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByName(String name);
}
