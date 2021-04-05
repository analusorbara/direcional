import client.OnsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import csv.CsvHelper;
import model.historico.HistoricoCarga;
import model.historico.HistoricoCargaResponse;
import model.login.LoginRequest;
import model.login.LoginResponse;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.ons.org.br/avaliacaooperacao/v1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        OnsClient oneClient = retrofit.create(OnsClient.class);

        CompletableFuture<LoginResponse> loginRequest = oneClient.login(new LoginRequest(){{
            setUsuario("analu@direcionalenergia.com.br");
            setSenha("Amper0813*");
        }});
        LoginResponse loginResponse = loginRequest.get();

        System.out.println(loginResponse.getAccess_token());

        List<HistoricoCarga> historicoCargas = new ArrayList<>();

        CompletableFuture<HistoricoCargaResponse> historicoRequest = oneClient.historicoDiario(1, "Bearer " + loginResponse.getAccess_token());
        HistoricoCargaResponse historicoCargaResponse = historicoRequest.get();

        historicoCargas.addAll(historicoCargaResponse.getHistoricoCargaOutputmessage().getResult().getHistoricoCarga());

        for (int i = 2; i < historicoCargaResponse.getHistoricoCargaOutputmessage().getQuantidadePaginas(); i++) {
            System.out.println("Baixando página: " + i);
            try {
                historicoRequest = oneClient.historicoDiario(i, "Bearer " + loginResponse.getAccess_token());
                historicoCargaResponse = historicoRequest.get();
                historicoCargas.addAll(historicoCargaResponse.getHistoricoCargaOutputmessage().getResult().getHistoricoCarga());
            } catch (Exception e) {
                System.err.println("Erro ao obter a página: " + i);
            }
        }

        System.out.println(historicoCargas.size());

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        objectMapper.setDateFormat(df);
        String historicoCargasjson = objectMapper.writeValueAsString(historicoCargas);

        CsvHelper.jsonToCsv(historicoCargasjson);
    }
}
