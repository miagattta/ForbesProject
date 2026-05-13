package forbes;

import java.sql.*;
import java.util.List;

public class DatabaseManager {

    private Connection conn;

    // конструктор - подключается к БД
    public DatabaseManager(String dbPath) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Подключено к БД: " + dbPath);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        }
    }

    // создает таблицу
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS billionaires (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "rank INTEGER," +
                "name TEXT," +
                "networth REAL," +
                "age INTEGER," +
                "country TEXT," +
                "source TEXT," +
                "industry TEXT" +
                ")";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // очищает таблицу
    public void clearTable() {
        String sql = "DELETE FROM billionaires";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // сохраняет всех миллиардеров в БД
    public void saveAll(List<Billionaire> list) {
        String sql = "INSERT INTO billionaires (rank, name, networth, age, country, source, industry) VALUES (?,?,?,?,?,?,?)";

        try {
            clearTable();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Billionaire b : list) {
                pstmt.setInt(1, b.getRank());
                pstmt.setString(2, b.getName());
                pstmt.setDouble(3, b.getNetworth());
                pstmt.setInt(4, b.getAge());
                pstmt.setString(5, b.getCountry());
                pstmt.setString(6, b.getSource());
                pstmt.setString(7, b.getIndustry());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Сохранено " + list.size() + " записей");

        } catch (SQLException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    // задача 2 через SQL
    public void sqlTask2() {
        String sql = "SELECT name, age, networth FROM billionaires " +
                "WHERE country = 'France' AND networth > 10 " +
                "ORDER BY age LIMIT 1";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("=== ЗАДАЧА 2 (SQL) ===");
            if (rs.next()) {
                System.out.println("Имя: " + rs.getString("name"));
                System.out.println("Возраст: " + rs.getInt("age"));
                System.out.println("Капитал: " + rs.getDouble("networth") + " млрд $");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // задача 3 через SQL
    public void sqlTask3() {
        String sql = "SELECT name, source, networth FROM billionaires " +
                "WHERE country = 'United States' AND industry LIKE '%Energy%' " +
                "ORDER BY networth DESC LIMIT 1";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("=== ЗАДАЧА 3 (SQL) ===");
            if (rs.next()) {
                System.out.println("Имя: " + rs.getString("name"));
                System.out.println("Компания: " + rs.getString("source"));
                System.out.println("Капитал: " + rs.getDouble("networth") + " млрд $");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // закрытие соединения
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
