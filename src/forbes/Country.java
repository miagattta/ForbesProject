package forbes;

// класс для стран (нормализация БД)
public class Country {
    private int id;          // уникальный номер страны
    private String name;     // название страны

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттеры
    public int getId() { return id; }
    public String getName() { return name; }
}
