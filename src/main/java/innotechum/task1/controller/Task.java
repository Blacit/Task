package innotechum.task1.controller;

import innotechum.task1.entity.Department;
import innotechum.task1.entity.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task implements AutoCloseable {

    public static List<Double> avgEmp = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Map<String, Department> departments = new HashMap<>();

        String path = args.length > 0 ? args[0] : null;

        assert path != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                if (Check(line) != null) {
                    String[] strings = line.split("/");
                    Employee emp = new Employee(strings[0], new Double(strings[1]));
                    if (!departments.containsKey(strings[2])) {
                        departments.put(strings[2], new Department(strings[2]));
                    }
                    departments.get(strings[2]).addEmployee(emp);
                    System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            SystemMessage(0);
            avg(departments);

            List<Double> salarys1 = new ArrayList<>();
            List<Double> salarys2 = new ArrayList<>();
            salarys2.addAll(salarys1);
            for (Map.Entry<String, Department> entry : departments.entrySet()) {
                String key = entry.getKey();
                entry.getValue().getEmployeeList().forEach((c) -> salarys1.add(c.getSalary()));
                if (key.equals("Первый")) {
                    for (Double number : salarys1) {
                        System.out.println(number);
                    }
                }
                if (key.equals("Второй")) {

                    //}
                    SystemMessage(2);

                }
            }
            //for (Double number : salarys) {
            //    System.out.println(number);

            for (Map.Entry<String, Department> entry : departments.entrySet())
               System.out.println(entry.getKey() + " - " + entry.getValue());

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

    public static void SystemMessage(int i) {
        switch (i) {
            case 0:
                System.out.println("---------------------------");
                System.out.println("Выбрали корректные варианты");
                System.out.println("---------------------------");
                break;
            case 1:
                System.out.println("------------------------------------------");
                System.out.println("Посчитали среднюю заработную плату отделов");
                System.out.println("------------------------------------------");
                break;
            case 2:
                System.out.println("--------------------");
                System.out.println("Перевели сотрудников");
                System.out.println("--------------------");
                break;
        }
    }

    public static void avg(Map<String, Department> departments) {
        for (Map.Entry<String, Department> entry : departments.entrySet()) {
            Double avg = entry.getValue().salaryAvg();
            avgEmp.add(avg);
            System.out.println("Средняя заработная плата отдела " + entry.getKey() + ": " + avg);
        }
        SystemMessage(1);
    }

    @Override
    public void close() {
        System.out.println("Читаемый файл закрыт");
    }
}