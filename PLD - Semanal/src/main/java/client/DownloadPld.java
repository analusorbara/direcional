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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DownloadPld {
    public static String download(String caminhoNomeDoArquivo, LocalDateTime inicio, LocalDateTime fim) throws ExecutionException, InterruptedException, IOException {
        // download da planilha de dados da CCEE (request)
        // init cookie manager
        System.out.println("# iniciando CCEE request");
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

        System.out.println("-> obtendo cookies");
        CompletableFuture<ResponseBody> downloadHome = oneClient.cookiesHome();
        downloadHome.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        CompletableFuture<ResponseBody> downloadPld = oneClient.downloadPld(
                String.format("%s - %s", inicio.format(formatter), fim.format(formatter))
        );
        System.out.println("-> baixando planilha");
        ResponseBody downloadPldResponse = downloadPld.get();

        System.out.println("-> salvando em disco");
        String content = downloadPldResponse.string();
        writeResponseBodyToDisk(caminhoNomeDoArquivo, content);

        System.out.println("# pronto");

        return content;
    }

    private static void writeResponseBodyToDisk(String caminhoNomeDoArquivo, String inputStream) throws IOException {
        FileUtils.writeStringToFile(new File(String.format("%s.xls", caminhoNomeDoArquivo)), inputStream, "utf-8");
        FileUtils.writeStringToFile(new File(String.format("%s.html", caminhoNomeDoArquivo)), inputStream, "utf-8");
    }
}
