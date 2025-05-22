package com.ApiRestMiBus.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    private static SimpleDateFormat formatter;

    public static String generateDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(); // Puedes pasar formato si quieres
        return formatter.format(date);
    }

    public static Date getToday() {
        setFormatter(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        return new Date();
    }

    public static SimpleDateFormat getFormatter() {
        return formatter;
    }

    public static void setFormatter(SimpleDateFormat formatter) {
        Utils.formatter = formatter;
    }

    /**
     * Convierte un String con formato de fecha (yyyy-MM-dd) a LocalDateTime con hora 00:00:00.
     * Retorna null si el String es null o vacío o inválido.
     *
     * @param fechaStr fecha en formato yyyy-MM-dd
     * @return LocalDateTime a medianoche o null
     */
    public static LocalDate parseDateOnlyString(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método general para convertir String a LocalDateTime según un patrón.
     * Si el patrón no incluye hora, retorna LocalDateTime a medianoche.
     *
     * @param fechaStr fecha como String
     * @param formato formato esperado (ej: "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss")
     * @return LocalDateTime o null
     */
    public static LocalDateTime parseStringToLocalDateTime(String fechaStr, String formato) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
            if (formato.contains("H") || formato.contains("m") || formato.contains("s")) {
                return LocalDateTime.parse(fechaStr, formatter);
            } else {
                LocalDate date = LocalDate.parse(fechaStr, formatter);
                return date.atStartOfDay();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
