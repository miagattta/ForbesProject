**Forbes Project**

**Как запустить проект**
1. Открыть проект в IntelliJ IDEA
2. Проверить, что файл data/Forbes.csv есть
3. Запустить класс Main

**Что делает программа**
- Реализует вариант Forbes (задачи 1, 2, 3 из таблицы)
- Содержит 6 классов: Billionaire, Country, Industry, CSVParser, DatabaseManager, Main
- Парсит CSV файл с учетом кавычек, создает 2600 объектов
- Создает базу данных SQLite в 3 нормальной форме (таблицы countries, industries, billionaires)
- Сохраняет всех миллиардеров в БД
- Выполняет SQL-запросы по задачам 2 и 3
- Выводит результаты в консоль
- Строит график по задаче 1

**Результаты**

**Задача 1 - График**
<img width="721" height="478" alt="image" src="https://github.com/user-attachments/assets/92d13863-20ca-4217-8c9b-f32864bedb22" />
 
**Задача 2 - Самый молодой миллиардер из Франции, капитал которого превышает 10 млрд**
<img width="730" height="370" alt="image" src="https://github.com/user-attachments/assets/efa6c75f-a1ff-4054-9fac-8f0410d2538a" />
 
**Задача 3 - Имя и компания бизнесмена из США, имеющего самый большой капитал в сфере Energy**
<img width="734" height="382" alt="image" src="https://github.com/user-attachments/assets/2e08d4c4-e7be-4e4d-b4f9-5da4b5a3e146" />
 
**Вывод в консоль**
<img width="974" height="1005" alt="image" src="https://github.com/user-attachments/assets/d33b647e-9610-4df0-b42d-e1dfe4667c41" />

 
"C:\Program Files\Java\jdk-21.0.10\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.1\lib\idea_rt.jar=60873" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "C:\Users\rosee\OneDrive\Рабочий стол\JAVA2\untitled\out\production\untitled;C:\Users\rosee\OneDrive\Рабочий стол\JAVA2\xchart-3.8.8.jar;C:\Users\rosee\OneDrive\Рабочий стол\JAVA2\sqlite-jdbc-3.42.0.0.jar" forbes.Main
Загружено: 2600
Стран: 75
Отраслей: 18

Подключено к БД
Таблицы созданы
Сохранено стран: 75
Сохранено отраслей: 18
Сохранено миллиардеров: 2600
=== Проверка Harold Hamm ===
  Имя: Harold Hamm & family
  industry_id: 16
  Отрасль: 'Energy'
=== ЗАДАЧА 1 ===
График в новом окне

=== ЗАДАЧА 2 (SQL) ===
Имя: Emmanuel Besnier
Возраст: 51
Капитал: 23.5 млрд $
=== ЗАДАЧА 3 (SQL) ===
Имя: Harold Hamm & family
Компания: oil & gas
Капитал: 17.2 млрд $





