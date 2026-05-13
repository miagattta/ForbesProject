package forbes;

// класс для хранения данных одного миллиардера
public class Billionaire {
    private int rank;
    private String name;
    private double networth;
    private int age;
    private String country;
    private String source;
    private String industry;

    // конструктор
    public Billionaire(int rank, String name, double networth, int age, String country, String source, String industry) {
        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.country = country;
        this.source = source;
        this.industry = industry;
    }

    // геттеры
    public int getRank() { return rank; }
    public String getName() { return name; }
    public double getNetworth() { return networth; }
    public int getAge() { return age; }
    public String getCountry() { return country; }
    public String getSource() { return source; }
    public String getIndustry() { return industry; }
}