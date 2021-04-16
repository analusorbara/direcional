package client;

import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

public interface CceeClient {
    @GET("/portal/faces/pages_publico/o-que-fazemos/como_ccee_atua/precos/preco_horario")
    CompletableFuture<ResponseBody> cookiesHome();

    @POST("portal/faces/oracle/webcenter/portalapp/pages/publico/oquefazemos/produtos/precos/lista_preco_horario.jspx")
    @Streaming
    @FormUrlEncoded
    CompletableFuture<ResponseBody> downloadPld(@Field("periodo") String periodo);
}
