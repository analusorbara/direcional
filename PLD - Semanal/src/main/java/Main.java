import client.DownloadPld;
import helpers.Excel;
import helpers.FileHelper;
import helpers.HtmlToObject;
import helpers.Image;
import models.Pld;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Main {
    public static void main(String[] args) {
        System.out.println("**** Iniciando CCEE PLD auto ****");
        // configurações
        String imagemBasePld = "./config/pld_base.png";
        String planilhaPld = "./config/info PLD horario v 2.0.xlsx";
        String planilhaPldSaida = "./gerados/info PLD horario v 2.0 gerada.xlsx";

        // configurações do programa
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String pldHtml = "";

        // gera as datas necessárias:
        // diaDeReferenciaPld: Hoje mais 1 dia, referência de dia para o PLD
        // inicio da semana: primeiro sábado anterior ao dia de hoje
        // incio do mês: primeiro dia do mês
        LocalDateTime diaDeReferenciaPld = now.plusDays(1);
        LocalDateTime inicioSemana = diaDeReferenciaPld
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        LocalDateTime inicioMes = diaDeReferenciaPld.withDayOfMonth(1); //diaDeReferenciaPld.minusDays(1);

        // incio do processo
        try {
            // novo do arquivo que será utilizado para savar o XLS, JSON e imagem
            String camihoNomeDoArquivo = String.format("./gerados/%s", FileHelper.gerarNome(diaDeReferenciaPld));

            // faz download do XLS e retorna seu contéudo em HTML (String)
            pldHtml = DownloadPld.download(camihoNomeDoArquivo, inicioMes, diaDeReferenciaPld);
            // converto o HTML para um objeto Java, isso facilita o tratamento de dados
            Pld pld = HtmlToObject.htmlToObject(pldHtml);
            // sava o conteúdo do objeto em JSON para análises posteriores
            FileHelper.salvarJson(String.format("%s.json", camihoNomeDoArquivo), pld);

            // caso tenha um PLD com a data de referência, inicia a geração da imagem
            // isso é feito para verificar se a CCEE publicou o PLD, as vezes o site retorna o dado
            // sem a dada de referência do PLD
            if (pld.temPldNovo(diaDeReferenciaPld)) {
                // coloca os textos na imagem configurada como Imagem de bse
                // a imagem gerada possuirá o nome: DIA_DE_HOJE-referencia-DIA_REFERENCIA_PLD
                Image.generate(pld, inicioMes, inicioSemana, diaDeReferenciaPld,
                        imagemBasePld,
                        String.format("%s.png", camihoNomeDoArquivo));

                // atualiza planilha
//                Pld pld = FileHelper.lerJson("./gerados/2021-04-16T16-31-04-pld-2021-04-16.json"); // somente para testes
                Excel.atualizarPld(planilhaPld, planilhaPldSaida,
                        inicioMes, diaDeReferenciaPld, pld);
            } else {
                // informa caso dê algum problema
                System.err.printf("# ERRO: não foi publicado o PLD para o dia %s \n", diaDeReferenciaPld.format(formatter));
            }
        } catch (Exception e) {
            // caso haja algum tipo de execeção, mostra o erro e mostra o conteúdo do dado retornado
            // pela CCEE, com isso pode-se identificar se o site está fora do ar
            System.err.printf("# ERRO: ocorreu um erro ao gerar o PLD para o dia %s, provavelmente, a CCEE está fora \n", diaDeReferenciaPld.format(formatter));
            System.err.println(e.getMessage());
            System.err.println("-> HTML recevido da CCEE:");
            System.err.println(pldHtml);
        }

        System.out.println("**** Fim ****");
    }
}
