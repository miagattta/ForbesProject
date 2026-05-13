package forbes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

// класс для чтения CSV файла
public class CSVParser {

    // парсинг строки с учетом кавычек (чтобы запятые внутри не ломали)
    private static String[] parseLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;           // открыли/закрыли кавычку
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim()); // добавили поле
                sb.setLength(0);                 // очистили для следующего
            } else {
                sb.append(c);                    // добавляем символ
            }
        }
        result.add(sb.toString().trim());        // последнее поле
        return result.toArray(new String[0]);
    }

    // читаем CSV и создаем список миллиардеров с id стран и отраслей
    public static List<Billionaire> parse(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        List<Billionaire> list = new ArrayList<>();

        // словари для уникальных id
        Map<String, Integer> countryMap = new HashMap<>();
        Map<String, Integer> industryMap = new HashMap<>();
        int nextCountryId = 1;
        int nextIndustryId = 1;

        // пропускаем заголовок (i=1)
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = parseLine(lines.get(i));
            if (parts.length >= 7) {
                try {
                    int rank = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double networth = Double.parseDouble(parts[2].trim());
                    int age = Integer.parseInt(parts[3].trim());
                    String country = parts[4].trim();
                    String source = parts[5].trim();
                    String industry = parts[6].trim();

                    // назначаем id стране
                    int countryId = countryMap.getOrDefault(country, nextCountryId);
                    if (!countryMap.containsKey(country)) {
                        countryMap.put(country, nextCountryId);
                        nextCountryId++;
                    }

                    // назначаем id отрасли
                    int industryId = industryMap.getOrDefault(industry, nextIndustryId);
                    if (!industryMap.containsKey(industry)) {
                        industryMap.put(industry, nextIndustryId);
                        nextIndustryId++;
                    }

                    list.add(new Billionaire(rank, name, networth, age, countryId, source, industryId));
                } catch (NumberFormatException e) {
                    // пропускаем строки с ошибками
                }
            }
        }
        return list;
    }

    // получаем список уникальных стран
    public static List<Country> getCountries(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        Map<String, Integer> map = new HashMap<>();
        int id = 1;
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = parseLine(lines.get(i));
            if (parts.length >= 7) {
                String country = parts[4].trim();
                if (!map.containsKey(country)) {
                    map.put(country, id++);
                }
            }
        }
        List<Country> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            result.add(new Country(e.getValue(), e.getKey()));
        }
        return result;
    }

    // получаем список уникальных отраслей
    public static List<Industry> getIndustries(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        Map<String, Integer> map = new HashMap<>();
        int id = 1;
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = parseLine(lines.get(i));
            if (parts.length >= 7) {
                String industry = parts[6].trim();
                if (!map.containsKey(industry)) {
                    map.put(industry, id++);
                }
            }
        }
        List<Industry> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            result.add(new Industry(e.getValue(), e.getKey()));
        }
        return result;
    }
}