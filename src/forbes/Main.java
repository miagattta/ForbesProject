package forbes;

import org.knowm.xchart.*;
import javax.swing.*;
import java.util.*;

// главный класс - запуск программы
public class Main {
    public static void main(String[] args) {
        try {
            String path = "data/Forbes.csv";   // путь к файлу

            // загружаем данные из CSV
            List<Billionaire> list = CSVParser.parse(path);
            List<Country> countries = CSVParser.getCountries(path);
            List<Industry> industries = CSVParser.getIndustries(path);

            System.out.println("Загружено: " + list.size());
            System.out.println("Стран: " + countries.size());
            System.out.println("Отраслей: " + industries.size());
            System.out.println();

            // работа с БД
            DatabaseManager db = new DatabaseManager("forbes.db");
            db.createTables();                     // создаем таблицы
            db.saveCountries(countries);            // сохраняем страны
            db.saveIndustries(industries);          // сохраняем отрасли
            db.saveBillionaires(list);              // сохраняем миллиардеров

            db.checkHarold();                       // проверка (отладка)

            drawChart(list, countries);             // задача 1 - график

            db.sqlTask2();                          // задача 2 - SQL запрос
            db.sqlTask3();                          // задача 3 - SQL запрос

            db.close();                             // закрываем БД

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // построение графика (задача 1)
    public static void drawChart(List<Billionaire> list, List<Country> countries) {
        // суммируем капитал по id стран
        Map<Integer, Double> sum = new HashMap<>();
        for (Billionaire b : list) {
            sum.put(b.getCountryId(), sum.getOrDefault(b.getCountryId(), 0.0) + b.getNetworth());
        }

        // сортируем по убыванию
        List<Map.Entry<Integer, Double>> sorted = new ArrayList<>(sum.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // берем топ-10 и подставляем названия стран
        List<String> names = new ArrayList<>();
        List<Double> totals = new ArrayList<>();

        for (int i = 0; i < Math.min(10, sorted.size()); i++) {
            int cid = sorted.get(i).getKey();
            for (Country c : countries) {
                if (c.getId() == cid) {
                    names.add(c.getName());
                    break;
                }
            }
            totals.add(sorted.get(i).getValue());
        }

        // создаем и показываем график
        CategoryChart chart = new CategoryChartBuilder()
                .width(800).height(500)
                .title("Капитал миллиардеров по странам")
                .xAxisTitle("Страна").yAxisTitle("Капитал (млрд $)")
                .build();

        chart.addSeries("Капитал", names, totals);
        chart.getStyler().setXAxisLabelRotation(45);
        new SwingWrapper<>(chart).displayChart();

        System.out.println("=== ЗАДАЧА 1 ===");
        System.out.println("График в новом окне\n");
    }
}
