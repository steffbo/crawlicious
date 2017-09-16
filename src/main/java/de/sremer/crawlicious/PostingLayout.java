package de.sremer.crawlicious;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import de.sremer.crawlicious.model.Posting;
import de.sremer.crawlicious.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
class PostingLayout extends VerticalLayout implements PostingChangeListener {

    @Autowired
    PostingRepository repo;
    private List<Posting> entries;

    @PostConstruct
    void init() {
        update();
    }

    private void update() {
        setEntries(repo.findAll());
    }

    public void setEntries(List<Posting> entries) {

        this.entries = entries;
        removeAllComponents();
        entries.forEach(e -> addComponent(new PostingItemLayout(e, this)));
    }

    void addEntry(Posting posting) {
        repo.save(posting);
        update();
    }

    @Override
    public void entryChanged(Posting posting) {
        addEntry(posting);
    }
}
