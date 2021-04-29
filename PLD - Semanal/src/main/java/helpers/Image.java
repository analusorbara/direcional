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
import java.time.LocalDateTime;
import java.util.Map;

import static helpers.FormatHelper.*;

public class Image {
    public static void generate(Pld pld,
                                LocalDateTime inicioMes, LocalDateTime inicioSemana, LocalDateTime diaDeReferenciaPld,
                                String imgBase, String imgOut) throws IOException {
        Submercado sudeste = pld.getSubmercado(SubmercadoEnum.SUDESTE);
        Submercado sul = pld.getSubmercado(SubmercadoEnum.SUL);
        Submercado nordeste = pld.getSubmercado(SubmercadoEnum.NORDESTE);
        Submercado norte = pld.getSubmercado(SubmercadoEnum.NORTE);

        String numSem = numSemanaMes(diaDeReferenciaPld);
        //String dia = formatInt(diaDeReferenciaPld.getDayOfMonth());
        String mes = formatMes(diaDeReferenciaPld);
        //String data = String.format("%s", numSem);


        PldHora seMinObj = sudeste.minSemana(inicioSemana, diaDeReferenciaPld);
        PldHora seMaxObj = sudeste.maxSemana(inicioSemana, diaDeReferenciaPld);
        String seMin = formatDouble(seMinObj.getValor());
        String seMinHora = formatHora(seMinObj.getHora());
        String seMax = formatDouble(seMaxObj.getValor());
        String seMaxHora = formatHora(seMaxObj.getHora());
        String seDia = formatDouble(sudeste.mediaDia(diaDeReferenciaPld));
        String seSemana = formatDouble(sudeste.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String seMes = formatDouble(sudeste.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora sMinObj = sul.minSemana(inicioSemana, diaDeReferenciaPld);;
        PldHora sMaxObj = sul.maxSemana(inicioSemana, diaDeReferenciaPld);
        String sMin = formatDouble(sMinObj.getValor());
        String sMinHora = formatHora(sMinObj.getHora());
        String sMax = formatDouble(sMaxObj.getValor());
        String sMaxHora = formatHora(sMaxObj.getHora());
        String sDia = formatDouble(sul.mediaDia(diaDeReferenciaPld));
        String sSemana = formatDouble(sul.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String sMes = formatDouble(sul.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora neMinObj = nordeste.minSemana(inicioSemana, diaDeReferenciaPld);;
        PldHora neMaxObj = nordeste.maxSemana(inicioSemana, diaDeReferenciaPld);
        String neMin = formatDouble(neMinObj.getValor());
        String neMinHora = formatHora(neMinObj.getHora());
        String neMax = formatDouble(neMaxObj.getValor());
        String neMaxHora = formatHora(neMaxObj.getHora());
        String neDia = formatDouble(nordeste.mediaDia(diaDeReferenciaPld));
        String neSemana = formatDouble(nordeste.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String neMes = formatDouble(nordeste.mediaMes(inicioMes, diaDeReferenciaPld));

        PldHora nMinObj = norte.minSemana(inicioSemana, diaDeReferenciaPld);;
        PldHora nMaxObj = norte.maxSemana(inicioSemana, diaDeReferenciaPld);
        String nMin = formatDouble(nMinObj.getValor());
        String nMinHora = formatHora(nMinObj.getHora());
        String nMax = formatDouble(nMaxObj.getValor());
        String nMaxHora = formatHora(nMaxObj.getHora());
        String nDia = formatDouble(norte.mediaDia(diaDeReferenciaPld));
        String nSemana = formatDouble(norte.mediaSemana(inicioSemana, diaDeReferenciaPld));
        String nMes = formatDouble(norte.mediaMes(inicioMes, diaDeReferenciaPld));

        generateText(imgBase, imgOut,
                numSem,
                mes,
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
            String numSem,
            String mes,
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
        Font fontData = new Font("Helveticish", Font.BOLD, 45);
        Font fontNumeroMinMax = new Font("Open Sans", Font.PLAIN, 28);
        Font fontNumeroDia = new Font("Open Sans", Font.BOLD, 32);
        Font fontHoraSemanaMes = new Font("Open Sans", Font.BOLD, 25);
        Font fontHoraMinMax = new Font("Open Sans", Font.PLAIN, 15);

        int dataY = 80;
        int diaX = 550;

        int seNumerosY = 908;
        int seHoraY = 930;

        int sNumerosY = seNumerosY + 75;
        int sHoraY = seHoraY + 75;

        int neNumerosY = sNumerosY + 75;
        int neHoraY = sHoraY + 75;

        int nNumerosY = neNumerosY + 75;
        int nHoraY = neHoraY + 75;

        int numerosMinX = 530;
        int numerosMaxX = 910;
        int horaMinX = 545;
        int horaMaxX = 935;
        //int numerosDiaX = 600;
        int numerosSemanaX = 710;
        int numerosMesX = 1195;

        Graphics2D g = image.createGraphics();

        Map<?, ?> desktopHints =
                (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

        if (desktopHints != null) {
            g.setRenderingHints(desktopHints);
        }

        g.setColor(Color.WHITE);

        g.setFont(fontData);

        System.out.println("-> escrevendo dia e mes");
        g.drawString(numSem, diaX-20, dataY);
        g.drawString(mes, diaX+75, dataY+40);

        // SUDESTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do SE");
        System.out.println("-> escrevendo SE min");
        g.drawString(seMin, numerosMinX, seNumerosY);

        g.setFont(fontNumeroDia);
        System.out.println("-> escrevendo SE semana");
        g.drawString(seSemana, numerosSemanaX, seNumerosY);

        g.setFont(fontNumeroMinMax);
        System.out.println("-> escrevendo SE max");
        g.drawString(seMax, numerosMaxX, seNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo SE min hora");
        g.drawString(seMinHora, horaMinX, seHoraY);

        System.out.println("-> escrevendo SE max hora");
        g.drawString(seMaxHora, horaMaxX, seHoraY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo SE mes");
        g.drawString(seMes, numerosMesX, seNumerosY);

        // SUL
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do S");
        System.out.println("-> escrevendo S min");
        g.drawString(sMin, numerosMinX, sNumerosY);

        g.setFont(fontNumeroDia);

        System.out.println("-> escrevendo S semana");
        g.drawString(sSemana, numerosSemanaX, sNumerosY);

        g.setFont(fontNumeroMinMax);
        System.out.println("-> escrevendo S max");
        g.drawString(sMax, numerosMaxX, sNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo S min hora");
        g.drawString(sMinHora, horaMinX, sHoraY);

        System.out.println("-> escrevendo S max hora");
        g.drawString(sMaxHora, horaMaxX, sHoraY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo S mes");
        g.drawString(sMes, numerosMesX, sNumerosY);

        // NORDESTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do NE");
        System.out.println("-> escrevendo NE min");
        g.drawString(neMin, numerosMinX, neNumerosY);

        g.setFont(fontNumeroDia);
        System.out.println("-> escrevendo NE semana");
        g.drawString(neSemana, numerosSemanaX, neNumerosY);

        g.setFont(fontNumeroMinMax);
        System.out.println("-> escrevendo NE max");
        g.drawString(neMax, numerosMaxX, neNumerosY);

        g.setFont(fontHoraMinMax);
        System.out.println("-> escrevendo NE min hora");
        g.drawString(neMinHora, horaMinX, neHoraY);

        System.out.println("-> escrevendo NE max hora");
        g.drawString(neMaxHora, horaMaxX, neHoraY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo NE mes");
        g.drawString(neMes, numerosMesX, neNumerosY);

        // NORTE
        g.setFont(fontNumeroMinMax);
        System.out.println("---> escrevendo dados do N");
        System.out.println("-> escrevendo N min");
        g.drawString(nMin, numerosMinX, nNumerosY);

        g.setFont(fontNumeroDia);
        System.out.println("-> escrevendo N semana");
        g.drawString(nSemana, numerosSemanaX, nNumerosY);

        g.setFont(fontNumeroMinMax);
        System.out.println("-> escrevendo N max");
        g.drawString(nMax, numerosMaxX, nNumerosY);

        g.setFont(fontHoraMinMax);

        System.out.println("-> escrevendo N min hora");
        g.drawString(nMinHora, horaMinX, nHoraY);

        System.out.println("-> escrevendo N max hora");
        g.drawString(nMaxHora, horaMaxX, nHoraY);

        g.setFont(fontHoraSemanaMes);

        System.out.println("-> escrevendo N mes");
        g.drawString(nMes, numerosMesX, nNumerosY);

        // salvando a imagem
        System.out.println("-> salvando imagem");
        ImageIO.write(image, "png", new File(imgOut));
        System.out.println("# pronto");
    }
}
