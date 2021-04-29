package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class FormatHelper {
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String formatDouble(double value) {
        NumberFormat df = DecimalFormat.getInstance(new Locale("pt", "BR"));
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(round(value, 2));
    }

    public static String formatInt(int value) {
        return String.format("%02d", value);
    }

    public static String formatHora(int value) {
        return String.format("%02d:00", value);
    }

    public static String formatMes(LocalDateTime localDateTime) {
        return localDateTime.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();
    }

    public static String numSemanaMes(LocalDateTime dia){

        WeekFields wf = WeekFields.of(Locale.getDefault()) ;                    // Use week fields appropriate to your locale. People in different places define a week and week-number differently, such as starting on a Monday or a Sunday, and so on.
        TemporalField weekNum = wf.weekOfMonth();                       // Represent the idea of this locale’s definition of week number as a `TemporalField`.

        return String.format("%d° Semana",dia.get(weekNum));
    }
}


