package com.ravunana.longonkelo.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "pt-pt";
    public static final ZonedDateTime DATE_TIME = ZonedDateTime.now();
    public static final int DIA = ZonedDateTime.now().getDayOfMonth();
    public static final int MES = ZonedDateTime.now().getMonthValue();
    public static final int ANO = ZonedDateTime.now().getYear();
    public static final String ASSINATURA_RAVUNANA = "ravunana,lda";

    public static String currencyFormat(BigDecimal valor) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');

        return new DecimalFormat("#,##0.000", decimalFormatSymbols).format(valor);
    }

    public static String getDateFormat(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return data.format(formatter);
    }

    public static String getMoneyFormat(BigDecimal input) {
        Locale ptLocale = new Locale("pt", "PT");
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(ptLocale);
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setCurrency(Currency.getInstance(ptLocale));

        //        DecimalFormat df = new DecimalFormat("#,###.00");

        return numberFormat.format(input);
    }

    public static String getMesPT(LocalDate data) {
        String mes = "";
        switch (data.getMonth()) {
            case JANUARY:
                mes = "JANEIRO";
                break;
            case FEBRUARY:
                mes = "FEVEREIRO";
                break;
            case MARCH:
                mes = "MARÇO";
                break;
            case APRIL:
                mes = "ABRIL";
                break;
            case MAY:
                mes = "MAIO";
                break;
            case JUNE:
                mes = "JUNHO";
                break;
            case JULY:
                mes = "JULHO";
                break;
            case AUGUST:
                mes = "AGOSTO";
                break;
            case SEPTEMBER:
                mes = "SETEMBRO";
                break;
            case OCTOBER:
                mes = "OUTURBO";
                break;
            case NOVEMBER:
                mes = "NOVEMBRO";
                break;
            case DECEMBER:
                mes = "DEZEMBRO";
                break;
        }

        return mes;
    }

    private Constants() {}
}
