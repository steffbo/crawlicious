import java.util.ArrayList;
import java.util.List;

public class SheetItemRow {

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    Item item;

    List<Object> getValueRow() {

        List list = new ArrayList();
        list.add(item.title);
        list.add(item.url);
        list.add(item.website);
        list.add(item.date);
        list.add(item.tags);

        return list;
    }

    SheetItemRow(Item item) {
        this.item = item;
    }
}
