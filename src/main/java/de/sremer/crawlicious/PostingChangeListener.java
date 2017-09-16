package de.sremer.crawlicious;

import de.sremer.crawlicious.model.Posting;

public interface PostingChangeListener {
    void entryChanged(Posting posting);
}
