package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByName(String name);

    List<Tag> findByNameIn(Collection<String> names);

    List<Tag> findByNameIn(String[] names);
}
