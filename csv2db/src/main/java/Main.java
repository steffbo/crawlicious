import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Item> itemsFromCsv = getItemsFromCsv();

        String sheetId = "1gqI-3pnGVPzPvhUwHSSoL2jbA4zfPR07PKoJUks9iXA";
        insertIntoSheet(sheetId, itemsFromCsv);

    }

    private static void insertIntoSheet(String sheetId, List<Item> items) throws IOException {

        SheetsConnector sheetsConnector = new SheetsConnector();

        List<List<Object>> list = new ArrayList<>();
        for (Item i : items) {
            list.add(new SheetItemRow(i).getValueRow());
        }
        String range = "A1:E"+items.size();
        ValueRange valueRange = new ValueRange();
        valueRange.setValues(list);
        UpdateValuesResponse updateValuesResponse = sheetsConnector.updateSheet(sheetId, range, valueRange);
        System.out.println("updateValuesResponse = " + updateValuesResponse);
    }


    private static List<Item> getItemsFromCsv() throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/crawlicious-franzi.csv");
        String content = readFromInputStream(inputStream);

        String[] lines = content.split("\n");
        List<Item> items = new ArrayList<>();

        for (String l : lines) {

            Item item = new Item();
            String[] line = l.split(";");

            item.setTitle(line[0]);
            item.setUrl(line[1]);
            item.setWebsite(line[2]);
            item.setDate(line[3]);
            item.setTags(line[4]);

            items.add(item);
        }

        return items;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
