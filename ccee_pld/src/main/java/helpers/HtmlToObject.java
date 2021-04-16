package helpers;

import models.Pld;
import models.PldData;
import models.PldHora;
import models.Submercado;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class HtmlToObject {
    public static Pld htmlToObject(String source) throws ParseException {
        System.out.println("# iniciando converso de HTML to JSON");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Document doc = Jsoup.parse(source);

        System.out.println("-> decodificando os headers");
        // obtém todos os headers da tabela, primeira linha
        Elements headers = doc.select("th");

        // igonora as duas primeiras colunas, Hora e Submercado,
        // e obtém o ídice em qual cada data está posicionada
        HashMap<Integer, Date> datas = new HashMap<>();
        for (int i = 2; i < headers.size(); i++) {
            datas.put(i, format.parse(headers.get(i).text()));
        }

        System.out.println("-> gerando dados para os submercados");
        // gera o JSON principal
        Pld pldJson = new Pld();
        // le a tabela os valores da linhas (row)
        for (Element row: doc.select("tbody").get(0).select("tr")) {
            // le os valores da linha
            Elements td = row.select("td");

            // primeiro valor é a hora e o segundo é o submercado
            Integer hora = Integer.parseInt(td.get(0).text());
            String submercado = td.get(1).text();

            // checa se já existe o submercado no JSON, caso não, cria um
            Optional<Submercado> mercadoOptional = pldJson.getSubmercados().stream().filter(item-> item.getNome().equals(submercado)).findFirst();
            Submercado mercado;
            if (mercadoOptional.isEmpty()) {
                mercado = new Submercado();
                mercado.setNome(submercado);
                pldJson.setSubmercado(mercado);
            } else {
                mercado = mercadoOptional.get();
            }

            // percorre todos os valores da linha que representa cada data
            for (int i=2; i < td.size(); i++) {
                // checa se já existe a data dentro do submercado, caso não, cria uma
                Date dataAtual = datas.get(i);

                Optional<PldData> mercadoDataOptional = mercado.getPldDatas().stream().filter(item-> item.getData().equals(dataAtual)).findFirst();
                PldData mercadoPldData;
                if (mercadoDataOptional.isEmpty()){
                    mercadoPldData = new PldData();
                    mercadoPldData.setData(dataAtual);
                    mercado.setPldData(mercadoPldData);
                } else {
                    mercadoPldData = mercadoDataOptional.get();
                }

                PldHora pldHora = new PldHora();
                pldHora.setHora(hora);
                pldHora.setValor(Double.parseDouble(td.get(i).text().replaceAll(",", ".")));
                mercadoPldData.setPldHora(pldHora);
            }
        }

        System.out.println("# pronto");
        return pldJson;
    }
}
