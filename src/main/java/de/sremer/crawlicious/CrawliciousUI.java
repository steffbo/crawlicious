package de.sremer.crawlicious;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

//@SpringUI
public class CrawliciousUI extends UI {

    private VerticalLayout root;

    @Autowired
    PostingLayout postingLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        System.out.println("VAADIN INIT");

        setupLayout();
        addHeader();
        addForm();
        addList();
        addDeleteButton();
    }

    private void setupLayout() {
        root = new VerticalLayout();
        setContent(root);
    }

    private void addHeader() {
        Label header = new Label("Crawlicious");
        header.addStyleName(ValoTheme.LABEL_H1);
        root.addComponent(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");

        TextField title = new TextField("Title");
        TextField link = new TextField("Link");
        TextField tags = new TextField("Tags");

        Button addButton = new Button("Add");
        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setIcon(VaadinIcons.PLUS);

        formLayout.addComponentsAndExpand(title, link, tags);
        formLayout.addComponents(addButton);

        root.addComponent(formLayout);
    }

    private void addList() {
        postingLayout.setWidth("80%");
        root.addComponent(postingLayout);
    }

    private void addDeleteButton() {
        root.addComponent(new Button("Delete"));
    }
}
