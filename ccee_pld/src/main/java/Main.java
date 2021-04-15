import client.DownloadPld;
import helpers.HtmlToObject;
import helpers.Image;
import models.Pld;

import java.io.IOException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ParseException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime diaDeReferenciaPld = now.plusDays(1);
        LocalDateTime inicioSemana = diaDeReferenciaPld
                .with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));

        LocalDateTime inicioMes = diaDeReferenciaPld
                .withDayOfMonth(1);

        String pldHtml = DownloadPld.download(inicioMes, diaDeReferenciaPld);

        Pld pld = HtmlToObject.htmlToObject(pldHtml);

//        if (pld.temPldNovo(diaDeReferenciaPld)) {
            Image.generate(pld, inicioMes, inicioSemana, diaDeReferenciaPld,
                    "./pld_base2.jpeg", "pld_output.png");
//        }

        // todo
        // alinhar data com horário
        // colocar zero ao final do número
        // salvar arquivo com o dia do pld referência
        // atualizar a planilha com os dados novos
        // salvar um json com os dados
        // implementar um monitoromento para verificar se saiu o PLD
    }
}
