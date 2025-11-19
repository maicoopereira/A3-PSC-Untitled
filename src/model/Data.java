package model;


import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;


public class Data {
    private static int ano;
    private static int mes;
    private static int dia;
    
    
    public static void formatData(String data) {
        DateTimeFormatter formatador;

        // Detecta automaticamente o formato
        if (Character.isDigit(data.charAt(4)) && data.charAt(4) == '-') {
            // Formato yyyy-M-d
            formatador = DateTimeFormatter.ofPattern("yyyy-M-d");
        } else {
            // Formato d-M-yyyy
            formatador = DateTimeFormatter.ofPattern("d-M-yyyy");
        }

        try {
            LocalDate localDate = LocalDate.parse(data, formatador);

            ano = localDate.getYear();
            mes = localDate.getMonthValue();
            dia = localDate.getDayOfMonth();

        } catch (DateTimeParseException e) {
            System.out.println("Erro ao converter data: " + data);
        }
    }

    public static LocalDate setDate(String dataString) {
        formatData(dataString);

        // Sempre monta corretamente com zero à esquerda
        //DateTimeFormatter saida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //System.out.println(data.format(saida)); // para debug
        LocalDate data = LocalDate.of(ano, mes, dia);

        return data;
    
    }
    
    public static int CurrentDay(){
        LocalDate data = LocalDate.now();
        return data.getDayOfMonth();
    }
    
    public static int CurrentMonth(){
        LocalDate data = LocalDate.now();
        return data.getMonthValue();
    }
    
    public static int CurrentYear(){
        LocalDate data = LocalDate.now();
        return data.getYear();
    }
    
}
