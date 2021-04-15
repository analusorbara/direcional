package client;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DownloadPld {
    public static String download(LocalDateTime inicio, LocalDateTime fim) throws ExecutionException, InterruptedException, IOException {
        // download da planilha de dados da CCEE (request)
        // init cookie manager
        System.out.println("# Iniciando CCEE request: ");
        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://www.ccee.org.br/")
                .build();

        CceeClient oneClient = retrofit.create(CceeClient.class);

        System.out.println("-> Obtendo cookies");
        CompletableFuture<ResponseBody> downloadHome = oneClient.downloadHome();
        downloadHome.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        CompletableFuture<ResponseBody> downloadPld = oneClient.downloadPld(
                String.format("%s - %s", inicio.format(formatter), fim.format(formatter))
        );
        System.out.println("-> Baixando planilha");
        ResponseBody downloadPldResponse = downloadPld.get();

        System.out.println("-> Salvando em disco");
        String content = downloadPldResponse.string();
        writeResponseBodyToDisk(content);

        System.out.println("# Pronto");

        return content;
    }

    private static void writeResponseBodyToDisk(String inputStream) throws IOException {
        FileUtils.writeStringToFile(new File("./pld.xls"), inputStream, "utf-8");
        FileUtils.writeStringToFile(new File("./pld.html"), inputStream, "utf-8");
    }
}
