package forbes;

import java.sql.*;
import java.util.List;

// класс для работы с SQLite
public class DatabaseManager {

    private Connection conn;  // соединение с БД

    // конструктор - подключаемся к файлу БД
    public DatabaseManager(String dbPath) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Подключено к БД");
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // создаем таблицы (3 нормальная форма)
    public void createTables() {
        String sql1 = "CREATE TABLE IF NOT EXISTS countries (id INTEGER PRIMARY KEY, name TEXT UNIQUE)";
        String sql2 = "CREATE TABLE IF NOT EXISTS industries (id INTEGER PRIMARY KEY, name TEXT UNIQUE)";
        String sql3 = "CREATE TABLE IF NOT EXISTS billionaires (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "rank INTEGER, name TEXT, networth REAL, age INTEGER," +
                "country_id INTEGER, source TEXT, industry_id INTEGER," +
                "FOREIGN KEY (country_id) REFERENCES countries(id)," +
                "FOREIGN KEY (industry_id) REFERENCES industries(id))";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            System.out.println("Таблицы созданы");
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // сохраняем список стран
    public void saveCountries(List<Country> list) {
        String sql = "INSERT OR IGNORE INTO countries (id, name) VALUES (?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Country c : list) {
                pstmt.setInt(1, c.getId());
                pstmt.setString(2, c.getName());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Сохранено стран: " + list.size());
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // сохраняем список отраслей
    public void saveIndustries(List<Industry> list) {
        String sql = "INSERT OR IGNORE INTO industries (id, name) VALUES (?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Industry i : list) {
                pstmt.setInt(1, i.getId());
                pstmt.setString(2, i.getName());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Сохранено отраслей: " + list.size());
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // сохраняем миллиардеров
    public void saveBillionaires(List<Billionaire> list) {
        String sql = "INSERT INTO billionaires (rank, name, networth, age, country_id, source, industry_id) VALUES (?,?,?,?,?,?,?)";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM billionaires");  // очищаем перед вставкой
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Billionaire b : list) {
                pstmt.setInt(1, b.getRank());
                pstmt.setString(2, b.getName());
                pstmt.setDouble(3, b.getNetworth());
                pstmt.setInt(4, b.getAge());
                pstmt.setInt(5, b.getCountryId());
                pstmt.setString(6, b.getSource());
                pstmt.setInt(7, b.getIndustryId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Сохранено миллиардеров: " + list.size());
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // проверка: какой industry_id у Harold Hamm (для отладки)
    public void checkHarold() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT b.name, b.industry_id, i.name as ind_name FROM billionaires b " +
                            "LEFT JOIN industries i ON b.industry_id = i.id " +
                            "WHERE b.name LIKE '%Harold%'"
            );
            System.out.println("=== Проверка Harold Hamm ===");
            while (rs.next()) {
                System.out.println("  Имя: " + rs.getString("name"));
                System.out.println("  industry_id: " + rs.getInt("industry_id"));
                System.out.println("  Отрасль: '" + rs.getString("ind_name") + "'");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // задача 2: самый молодой француз с капиталом > 10 млрд
    public void sqlTask2() {
        String sql = "SELECT b.name, b.age, b.networth FROM billionaires b " +
                "JOIN countries c ON b.country_id = c.id " +
                "WHERE c.name = 'France' AND b.networth > 10 " +
                "ORDER BY b.age LIMIT 1";
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

    // задача 3: самый богатый в Energy из США
    public void sqlTask3() {
        String sql = "SELECT b.name, b.source, b.networth FROM billionaires b " +
                "JOIN countries c ON b.country_id = c.id " +
                "JOIN industries i ON b.industry_id = i.id " +
                "WHERE c.name = 'United States' AND LOWER(i.name) LIKE '%energy%' " +
                "ORDER BY b.networth DESC LIMIT 1";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("=== ЗАДАЧА 3 (SQL) ===");
            if (rs.next()) {
                System.out.println("Имя: " + rs.getString("name"));
                System.out.println("Компания: " + rs.getString("source"));
                System.out.println("Капитал: " + rs.getDouble("networth") + " млрд $");
            } else {
                System.out.println("Не найдено");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // закрываем соединение
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {}
    }
}
