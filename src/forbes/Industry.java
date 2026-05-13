package forbes;

// класс для отраслей (нормализация БД)
public class Industry {
    private int id;          // уникальный номер отрасли
    private String name;     // название отрасли

    public Industry(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттеры
    public int getId() { return id; }
    public String getName() { return name; }
}