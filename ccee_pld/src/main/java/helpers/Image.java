package helpers;

import models.Pld;
import models.PldHora;
import models.Submercado;
import models.SubmercadoEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class Image {
    private static String formatDouble(double value) {
        NumberFormat df = DecimalFormat.getInstance(new Locale("pt", "BR"));
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(value);
    }

    private static String formatInt(int value) {
        return String.format("%02d", value);
    }

    private static String formatHora(int value) {
        return String.format("%02d:00", value);
    }

    private static String formatMes(LocalDateTime localDateTime) {
        return localDateTime.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();
    }

    public static void generate(Pld pld,
                                LocalDateTime inicioMes, LocalDateTime inicioSemana, LocalDateTime diaDeReferenciaPld,
                                String imgBase, String imgOut) throws IOException {
        Submercado sudeste = pld.getSubmercado(SubmercadoEnum.SUDESTE);
        Submercado sul = pld.getSubmercado(SubmercadoEnum.SUL);
        Submercado nordeste = pld.getSubmercado(SubmercadoEnum.NORDESTE);
        Submercado norte = pld.getSubmercado(SubmercadoEnum.NORTE);

        String dia = formatInt(diaDeReferenciaPld.getDayOfMonth());
        String mes = formatMes(diaDeReferenciaPld);
        String data = String.format("%s/%s", dia, mes);

        PldHora seMinObj = sudeste.minDia(diaDeReferenciaPld);
        PldHora seMaxObj = sudeste.maxDia(diaDeReferenciaPld);
        String seMin = formatDouble(seMinObj.getValor());
        String seMinHora = formatHora(seMinObj.getHora());
        String seMax = formatDouble(seMaxObj.getValor());
        String seMaxHora = formatHora(seMaxObj.getHora());
        String seDia = formatDouble(sudeste.mediaDia(diaDeReferenciaPld));
        String seSemana = formatDouble(sudeste.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String seMes = formatDouble(sudeste.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora sMinObj = sul.minDia(diaDeReferenciaPld);
        PldHora sMaxObj = sul.maxDia(diaDeReferenciaPld);
        String sMin = formatDouble(sMinObj.getValor());
        String sMinHora = formatHora(sMinObj.getHora());
        String sMax = formatDouble(sMaxObj.getValor());
        String sMaxHora = formatHora(sMaxObj.getHora());
        String sDia = formatDouble(sul.mediaDia(diaDeReferenciaPld));
        String sSemana = formatDouble(sul.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String sMes = formatDouble(sul.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora neMinObj = nordeste.minDia(diaDeReferenciaPld);
        PldHora neMaxObj = nordeste.maxDia(diaDeReferenciaPld);
        String neMin = formatDouble(neMinObj.getValor());
        String neMinHora = formatHora(neMinObj.getHora());
        String neMax = formatDouble(neMaxObj.getValor());
        String neMaxHora = formatHora(neMaxObj.getHora());
        String neDia = formatDouble(nordeste.mediaDia(diaDeReferenciaPld));
        String neSemana = formatDouble(nordeste.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String neMes = formatDouble(nordeste.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora nMinObj = norte.minDia(diaDeReferenciaPld);
        PldHora nMaxObj = norte.maxDia(diaDeReferenciaPld);
        String nMin = formatDouble(nMinObj.getValor());
        String nMinHora = formatHora(nMinObj.getHora());
        String nMax = formatDouble(nMaxObj.getValor());
        String nMaxHora = formatHora(nMaxObj.getHora());
        String nDia = formatDouble(norte.mediaDia(diaDeReferenciaPld));
        String nSemana = formatDouble(norte.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String nMes = formatDouble(norte.mediaMes(inicioMes, diaDeReferenciaPld));

        generateText(imgBase, imgOut,
                data,
                seMin,
                seMinHora,
                seMax,
                seMaxHora,
                seDia,
                seSemana,
                seMes,
                sMin,
                sMinHora,
                sMax,
                sMaxHora,
                sDia,
                sSemana,
                sMes,
                neMin,
                neMinHora,
                neMax,
                neMaxHora,
                neDia,
                neSemana,
                neMes,
                nMin,
                nMinHora,
                nMax,
                nMaxHora,
                nDia,
                nSemana,
                nMes);
    }

    private static void generateText(String imgBase, String imgOut,
            String data,
            String seMin,
            String seMinHora,
            String seMax,
            String seMaxHora,
            String seDia,
            String seSemana,
            String seMes,
            String sMin,
            String sMinHora,
            String sMax,
            String sMaxHora,
            String sDia,
            String sSemana,
            String sMes,
            String neMin,
            String neMinHora,
            String neMax,
            String neMaxHora,
            String neDia,
            String neSemana,
            String neMes,
            String nMin,
            String nMinHora,
            String nMax,
            String nMaxHora,
            String nDia,
            String nSemana,
            String nMes) throws IOException {
        System.out.println("# iniciando geração da imagem");

        System.out.println("-> abrindo imagem");
        BufferedImage image = ImageIO.read(new File(imgBase));

        System.out.println("-> configurando font");
        Font fontData = new Font("Helveticish", Font.BOLD, 30);
        Font fontNumeroMinMax = new Font("Open Sans", Font.PLAIN, 21);
        Font fontNumeroDia = new Font("Open Sans", Font.BOLD, 21);
        Font fontHoraSemanaMes = new Font("Open Sans", Font.BOLD, 18);
        Font fontHoraMinMax = new Font("Open Sans", Font.PLAIN, 11);

        int dataY = 136;
        int diaX = 1035;

        int seNumerosY = 318;
        int seHoraY = 335;

        int sNumerosY = seNumerosY + 59;
        int sHoraY = seHoraY + 59;

        int neNumerosY = sNumerosY + 59;
        int neHoraY = sHoraY + 59;

        int nNumerosY = neNumerosY + 59;
        int nHoraY = neHoraY + 59;

        int numerosMinX = 300;
        int numerosMaxX = 450;
        int horaMinX = 320;
        int horaMaxX = 470;
        int numerosDiaX = 590;
        int numerosSemanaX = 832;
        int numerosMesX = 1025;

        Graphics2D g = image.createGraphics();

        Map<?, ?> desktopHints =
                (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

        if (desktopHints != null) {
            g.setRenderingHints(desktopHints);
        }

        g.setColor(Color.WHITE);

        g.setFont(fontData);

        System.out.println("-> escrevendo dia e mes");
        g.drawString(data, diaX, dataY);

        // SUDESTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do SE");
        System.out.println("-> escrevendo SE min");
        g.drawString(seMin, numerosMinX, seNumerosY);

        System.out.println("-> escrevendo SE max");
        g.drawString(seMax, numerosMaxX, seNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo SE min hora");
        g.drawString(seMinHora, horaMinX, seHoraY);

        System.out.println("-> escrevendo SE max hora");
        g.drawString(seMaxHora, horaMaxX, seHoraY);

        g.setFont(fontNumeroDia);

        System.out.println("-> escrevendo SE dia");
        g.drawString(seDia, numerosDiaX, seNumerosY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo SE semana");
        g.drawString(seSemana, numerosSemanaX, seNumerosY);

        System.out.println("-> escrevendo SE mes");
        g.drawString(seMes, numerosMesX, seNumerosY);

        // SUL
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do S");
        System.out.println("-> escrevendo S min");
        g.drawString(sMin, numerosMinX, sNumerosY);

        System.out.println("-> escrevendo S max");
        g.drawString(sMax, numerosMaxX, sNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo S min hora");
        g.drawString(sMinHora, horaMinX, sHoraY);

        System.out.println("-> escrevendo S max hora");
        g.drawString(sMaxHora, horaMaxX, sHoraY);

        g.setFont(fontNumeroDia);

        System.out.println("-> escrevendo S dia");
        g.drawString(sDia, numerosDiaX, sNumerosY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo S semana");
        g.drawString(sSemana, numerosSemanaX, sNumerosY);

        System.out.println("-> escrevendo S mes");
        g.drawString(sMes, numerosMesX, sNumerosY);

        // NORDESTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do NE");
        System.out.println("-> escrevendo NE min");
        g.drawString(neMin, numerosMinX, neNumerosY);

        System.out.println("-> escrevendo NE max");
        g.drawString(neMax, numerosMaxX, neNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo NE min hora");
        g.drawString(neMinHora, horaMinX, neHoraY);

        System.out.println("-> escrevendo NE max hora");
        g.drawString(neMaxHora, horaMaxX, neHoraY);

        g.setFont(fontNumeroDia);

        System.out.println("-> escrevendo NE dia");
        g.drawString(neDia, numerosDiaX, neNumerosY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo NE semana");
        g.drawString(neSemana, numerosSemanaX, neNumerosY);

        System.out.println("-> escrevendo NE mes");
        g.drawString(neMes, numerosMesX, neNumerosY);

        // NORTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do N");
        System.out.println("-> escrevendo N min");
        g.drawString(nMin, numerosMinX, nNumerosY);

        System.out.println("-> escrevendo N max");
        g.drawString(nMax, numerosMaxX, nNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo N min hora");
        g.drawString(nMinHora, horaMinX, nHoraY);

        System.out.println("-> escrevendo N max hora");
        g.drawString(nMaxHora, horaMaxX, nHoraY);

        g.setFont(fontNumeroDia);

        System.out.println("-> escrevendo N dia");
        g.drawString(nDia, numerosDiaX, nNumerosY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo N semana");
        g.drawString(nSemana, numerosSemanaX, nNumerosY);

        System.out.println("-> escrevendo N mes");
        g.drawString(nMes, numerosMesX, nNumerosY);

        // salvando a imagem
        System.out.println("-> salvando imagem");
        ImageIO.write(image, "png", new File(imgOut));
        System.out.println("# pronto");
    }
}
