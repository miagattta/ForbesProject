package forbes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

// класс для чтения CSV файла
public class CSVParser {

    // читает файл и возвращает список миллиардеров
    public static List<Billionaire> parse(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        List<Billionaire> list = new ArrayList<>();

        // пропускаем заголовок (первая строка)
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");

            if (parts.length >= 7) {
                try {
                    int rank = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double networth = Double.parseDouble(parts[2].trim());
                    int age = Integer.parseInt(parts[3].trim());
                    String country = parts[4].trim();
                    String source = parts[5].trim();
                    String industry = parts[6].trim();

                    list.add(new Billionaire(rank, name, networth, age, country, source, industry));
                } catch (NumberFormatException e) {
                    // пропускаем строки с ошибками
                }
            }
        }
        return list;
    }
}
