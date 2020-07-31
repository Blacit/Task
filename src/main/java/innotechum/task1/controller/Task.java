package innotechum.task1.controller;
import innotechum.task1.entity.Department;
import innotechum.task1.entity.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Task implements AutoCloseable{
    public static void main(String[] args)  throws IOException{
        Map<Object, String> DepEmployee = new HashMap<>();

        String path = args.length > 0 ? args[0] : null;

        assert path != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                if (Check(line) != null) {
                    String[] strings = line.split("/");
                    Employee emp = new Employee(strings[0], new BigDecimal(strings[1]));
                    Department dep = new Department(strings[2]);

                    if (strings[2].equals("Первый")) {
                        DepEmployee.put(emp, "Первый");
                    } else {
                        DepEmployee.put(emp, "Второй");
                    }
                    dep.addEmployee(emp);
                    System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            for (Map.Entry<Object, String> entry : DepEmployee.entrySet())
                System.out.println(entry.getKey() + " - " + entry.getValue());

            System.out.println("---------------------------");
            System.out.println("Выбрали корректные варианты");
            System.out.println("---------------------------");

        } catch (FileNotFoundException e) {
            System.out.println("Файл не был найден, проверьте путь");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Исправьте путь к файлу, выходите за массив");
        } catch (NullPointerException e) {
            System.out.println("Вы забыли прописать путь к файлу");
        } finally {
            System.out.println("Читаемый файл закрыт");
        }
    }

    public static String[] Check(String line) {
        String regex = "[А-Яа-яЁёA-Za-z\\s]+";
        if (!(line = line.trim()).isEmpty()) {
            String[] strings = line.split("/");
            if (strings.length < 3) {
                System.out.println(line + " - неверный формат строки, ожидаем ФИО/10000.12/Департамент");
                return null;
            }
            if (!(strings[0].matches(regex) && strings[2].matches(regex))) {
                System.out.println(line + " - неверный формат ввода, нужно: Буквы/Цифры/Буквы");
                return null;
            }
            if (!strings[1].matches("\\d{5}(\\.\\d{1,2})?")) {
                System.out.println(line + " - некорректный ввод цифр, знаков. Зарплата не может быть отрицательной и иметь меньше 5 знаков");
                return null;
            }
            return strings;
        }
        return null;
    }

    @Override
    public void close() {
        System.out.println("Читаемый файл закрыт");
    }
}