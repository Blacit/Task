import java.io.*;
import java.math.BigDecimal;

public class Task {
    public static void main(String[] args) throws IOException {
        String s;
        BufferedReader br;

        String path = args.length > 0 ? args[0] : null;

        try {
            assert path != null; // Углубиться в assert
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251"));

            String regex = "[А-Яа-яЁёA-Za-z\\s]+";
            while ((s = br.readLine()) != null) {
                if (!(s = s.trim()).isEmpty()) {
                    String[] strings = s.split("/");
                    if (strings.length < 3) {
                        System.out.println("Строка " + s + " имеет неверное количество слов");
                        continue;
                    }
                    if (!(strings[0].matches(regex) && strings[2].matches(regex))) {
                        System.out.println("Строка " + s + " неверный формат ввода");
                        continue;
                    }
                    if (!strings[1].matches("\\d{5}(\\.\\d{1,2})?")) {
                        System.out.println("Строка " + s + " некорректный вод цифр, знаков");
                        continue;
                    }
                    Employee emp = new Employee(strings[0], new BigDecimal(strings[1]));
                    Department dep = new Department(strings[2]);
                    dep.addEmployee(emp);
                    System.out.println("Строка " + strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            br.close();
            System.out.println("---------------------------");
            System.out.println("Выбрали корректные варианты");
            System.out.println("---------------------------");

        } catch (FileNotFoundException e) {
            System.out.println("Файл не был найден, проверьте путь");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Исправьте путь к файлу, выходите за массив");
        } catch (NullPointerException e) {
            System.out.println("Вы забыли прописать путь к файлу");
        }
    }
}
// средняя заработная плата = зп1 сотрудника + зп2 сотрудника / количество сотрудников