import client.DownloadPld;
import helpers.HtmlToObject;
import models.Pld;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String pldHtml = DownloadPld.download(format.parse("01/04/2021"), format.parse("15/04/2021"));

        Pld pld = HtmlToObject.htmlToObject(pldHtml);
    }
}
