package client;

import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

public interface CceeClient {
    @POST("portal/faces/oracle/webcenter/portalapp/pages/publico/oquefazemos/produtos/precos/lista_preco_horario.jspx")
    @Streaming
    @FormUrlEncoded
    CompletableFuture<ResponseBody> downloadPld(@Field("periodo") String periodo);

    @GET("portal/faces/oracle/webcenter/portalapp/pages/publico/oquefazemos/produtos/precos/lista_preco_horario.jspx")
    CompletableFuture<ResponseBody> downloadHome();
}
