package forbes;

// класс для хранения данных одного миллиардера
public class Billionaire {
    private int rank;           // место в рейтинге
    private String name;        // имя
    private double networth;    // состояние в млрд $
    private int age;            // возраст
    private int countryId;      // ссылка на таблицу countries
    private String source;      // источник дохода / компания
    private int industryId;     // ссылка на таблицу industries

    // конструктор
    public Billionaire(int rank, String name, double networth, int age, int countryId, String source, int industryId) {
        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.countryId = countryId;
        this.source = source;
        this.industryId = industryId;
    }

    // геттеры
    public int getRank() { return rank; }
    public String getName() { return name; }
    public double getNetworth() { return networth; }
    public int getAge() { return age; }
    public int getCountryId() { return countryId; }
    public String getSource() { return source; }
    public int getIndustryId() { return industryId; }
}