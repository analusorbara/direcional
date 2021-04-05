package csv;

import com.github.opendevl.JFlat;

public class CsvHelper {
    public static void jsonToCsv(String json) throws Exception {
        JFlat flatMe = new JFlat(json);

        //get the 2D representation of JSON document
        flatMe.json2Sheet().headerSeparator("_").getJsonAsSheet();

        //write the 2D representation in csv format
        flatMe.write2csv("output.csv");
    }
}
