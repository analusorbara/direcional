package helpers;

import models.Pld;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHelper {
    public static String gerarNome(LocalDateTime dataReferencia) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s-pld-%s", gerarNome(), dataReferencia.format(formatter));
    }

    public static String gerarNome() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime agora = LocalDateTime.now();
        return agora.format(formatter);
    }

    public static void salvarJson(String path, Pld pld) throws IOException {
        String json = JsonHelper.objectToJsonString(pld);
        FileUtils.writeStringToFile(new File(path), json);
    }

    public static Pld lerJson(String path) throws IOException {
        String json = FileUtils.readFileToString(new File(path));
        return JsonHelper.jsonStringToObject(json, Pld.class);
    }
}
