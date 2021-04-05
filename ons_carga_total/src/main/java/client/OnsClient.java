package client;

import model.historico.HistoricoCargaResponse;
import model.login.LoginRequest;
import model.login.LoginResponse;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

public interface OnsClient {
    @POST("autenticar")
    CompletableFuture<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("historicoCargaGlobal/Diario")
    CompletableFuture<HistoricoCargaResponse> historicoDiario(@Query("pagina") int pagina, @Header("Authorization") String authHeader);
}
