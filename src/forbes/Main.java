package forbes;

import org.knowm.xchart.*;
import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // читаем данные из CSV
            List<Billionaire> list = CSVParser.parse("data/Forbes.csv");
            System.out.println("Загружено: " + list.size());
            System.out.println();

            // работа с БД
            DatabaseManager db = new DatabaseManager("forbes.db");
            db.createTable();
            db.saveAll(list);

            // задача 1 - график
            drawChart(list);

            // задачи 2 и 3 через SQL
            db.sqlTask2();
            System.out.println();
            db.sqlTask3();

            db.close();

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // график (задача 1)
    public static void drawChart(List<Billionaire> list) {
        Map<String, Double> map = new HashMap<>();

        for (Billionaire b : list) {
            map.put(b.getCountry(), map.getOrDefault(b.getCountry(), 0.0) + b.getNetworth());
        }

        List<Map.Entry<String, Double>> sorted = new ArrayList<>(map.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        List<String> countries = new ArrayList<>();
        List<Double> totals = new ArrayList<>();

        for (int i = 0; i < Math.min(10, sorted.size()); i++) {
            countries.add(sorted.get(i).getKey());
            totals.add(sorted.get(i).getValue());
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800).height(500)
                .title("Капитал миллиардеров по странам")
                .xAxisTitle("Страна").yAxisTitle("Капитал (млрд $)")
                .build();

        chart.addSeries("Капитал", countries, totals);
        chart.getStyler().setXAxisLabelRotation(45);
        new SwingWrapper<>(chart).displayChart();

        System.out.println("=== ЗАДАЧА 1 ===");
        System.out.println("График в новом окне\n");
    }
}