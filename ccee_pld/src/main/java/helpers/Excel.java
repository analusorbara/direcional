package helpers;

import models.Pld;
import models.PldHora;
import models.SubmercadoEnum;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Excel {
    public static void atualizarPld(String planilha, String planilhaGerada,
                                    LocalDateTime primeiroDiaDoMes,
                                    LocalDateTime diaDeReferenciaPld, Pld pld) throws IOException {
        System.out.printf("# atualizando planilha %s \n", planilha);

        System.out.println("-> abrindo a planilha");
        FileInputStream planilhaArquivo = new FileInputStream(planilha);
        XSSFWorkbook planilhaPld = new XSSFWorkbook(planilha);
        planilhaPld.setForceFormulaRecalculation(true);
        XSSFSheet aba = planilhaPld.getSheet("BD+FUNC");

        System.out.println("-> procurando a primeira do primeiro dia do mês");
        int colunaPrimeiroDiaDoMes = procurarColunaPrimeiroDiaDoMes(aba, primeiroDiaDoMes);
        System.out.println("-> procurando a última linha com dado na aba");
        int ultimaLinhaComDados = procurarUltimaLinhaComDados(aba);

        System.out.println("-> colocando os dados do PLD na planilha");
        for (var coluna = colunaPrimeiroDiaDoMes; coluna < aba.getRow(0).getLastCellNum(); coluna++) {
            int diasDepoisDoPrimeiroDiaDoMes = coluna - colunaPrimeiroDiaDoMes;
            LocalDateTime diaReferenciaDoLaco = primeiroDiaDoMes.plusDays(diasDepoisDoPrimeiroDiaDoMes);

            if (diaReferenciaDoLaco.isAfter(diaDeReferenciaPld)) {
                break;
            }

            XSSFCell dataCelula = aba.getRow(0).getCell(colunaPrimeiroDiaDoMes);
            String dataStr = dataCelula.toString();

            if (dataStr == null || dataStr.equals("")) {
                dataCelula.setCellValue(diaReferenciaDoLaco);
            }

            for (var linha = 1; linha <= ultimaLinhaComDados; linha++) {
                XSSFRow linhaPlanilha = aba.getRow(linha);
                int hora = (int) linhaPlanilha.getCell(0).getNumericCellValue();
                String submercado = linhaPlanilha.getCell(1).toString();

                PldHora pldHora = pld
                        .getSubmercado(SubmercadoEnum.valueOf(submercado))
                        .procuraData(diaReferenciaDoLaco)
                        .procuraHora(hora);

                linhaPlanilha.getCell(coluna).setCellValue(pldHora.getValor());
            }
        }

        System.out.println("-> fechando o stream do arquivo principal");
        planilhaArquivo.close();

        System.out.printf("-> salvando as alterações na planilha: %s\n", planilhaGerada);
        FileOutputStream planilhaArquivoSaido = new FileOutputStream(planilhaGerada);
        planilhaPld.write(planilhaArquivoSaido);
        System.out.println("-> fechando os streams");
        planilhaArquivoSaido.close();

        System.out.println("# planilha atualizada");
    }

    private static int procurarColunaPrimeiroDiaDoMes(XSSFSheet aba, LocalDateTime primeiroDiaDoMes) {
        XSSFRow row = aba.getRow(0);
        int colunaPrimeiroDiaDoMes;
        for (colunaPrimeiroDiaDoMes = row.getFirstCellNum(); colunaPrimeiroDiaDoMes < row.getLastCellNum(); colunaPrimeiroDiaDoMes++) {
            XSSFCell cell = row.getCell(colunaPrimeiroDiaDoMes);
            CellType cellType = cell.getCellType();
            if (cellType == CellType.NUMERIC) {
                Date data = cell.getDateCellValue();
                LocalDateTime localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (primeiroDiaDoMes.getDayOfMonth() == localDate.getDayOfMonth()
                        && primeiroDiaDoMes.getMonthValue() == localDate.getMonthValue()
                        && primeiroDiaDoMes.getYear() == localDate.getYear()) {
                    break;
                }
            }
        }
        return colunaPrimeiroDiaDoMes;
    }

    private static int procurarUltimaLinhaComDados(XSSFSheet aba) {
        int linha;
        for (linha = 1; linha < aba.getLastRowNum(); linha++) {
            XSSFRow linhaPlanilha = aba.getRow(linha);
            String submercado = linhaPlanilha.getCell(1).toString();
            if (submercado == null || submercado.equals("")) {
                break;
            }
        }
        return linha - 1;
    }
}
