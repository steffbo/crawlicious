package de.sremer.crawlicious;

import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import de.sremer.crawlicious.model.Posting;

public class PostingItemLayout extends HorizontalLayout {

    private final TextField title;
    private final TextField link;
    private final TextField tags;

    public PostingItemLayout(Posting e, PostingChangeListener changeListener) {
        title = new TextField("Title");
        link = new TextField("Link");
        tags = new TextField("Tags");

        Binder<Posting> binder = new Binder<>(Posting.class);
        //binder.bindInstanceFields(this);
//        binder.bind(title, Posting::getTitle, Posting::setTitle);
//        binder.bind(link, Posting::getLink, Posting::setLink);
//        binder.bind(tags, Posting::getTags, Posting::setTags);
//        binder.setBean(e);

        addComponents(title, link, tags);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        binder.addValueChangeListener(valueChangeEvent -> changeListener.entryChanged(e));

    }
}
