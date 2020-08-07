package innotechum.task1.controller;

import innotechum.task1.entity.Departament;
import innotechum.task1.entity.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task implements AutoCloseable {

    public static void main(String[] args) throws IOException {
        Map<String, Departament> departments = new HashMap<>();
        List<BigDecimal> avgEmp = new ArrayList<>();
        String path = args.length > 0 ? args[0] : null;

        assert path != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            String line; // Как мне объявить line в while
            while ((line = br.readLine()) != null) {
                if (check(line)) {
                    String[] strings = line.split("/");
                    Employee emp = new Employee(strings[0], new BigDecimal(strings[1]));
                    if (!departments.containsKey(strings[2])) {
                        departments.put(strings[2], new Departament(strings[2]));
                    }
                    departments.get(strings[2]).addEmployee(emp);
                    System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            systemMessage(0);
            avg(departments, avgEmp);

            /*String key;
            if (avgEmp.get(0).compareTo(avgEmp.get(1)) > 0) {
                key = "Первый отдел имеет большую среднюю зп";
            } else {
                key = "Второй отдел имеет большую среднюю зп";
            }
            System.out.println(empswap(departments, key, avgEmp));*/
            Departament department1= departments.get("Первый");
            Departament department2= departments.get("Второй");

            List<Employee> empl = new ArrayList<>(); // лист, в котором будут храниться сотрудники для перевода
                if (avgEmp.get(0).compareTo(avgEmp.get(1)) > 0) { // если средняя ЗП в первом отделе больше
                    empl.addAll(
                        department1.getEmployeeList().stream() // значит проходим по первому листу и сравниваем
                                .filter(emp -> emp.getSalary().compareTo(avgEmp.get(0)) > 0 && emp.getSalary().compareTo(avgEmp.get(1)) < 0)
                                .collect(Collectors.toList()));
            } else if (avgEmp.get(0).compareTo(avgEmp.get(1)) < 0) {
                    System.out.println("hello");
            } else {
                    System.out.println("hello");
            }
            for (Employee name : empl) {
                System.out.println(name);
            }

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
            // Есть какие-то правила по порядку написания методов?
    public static boolean check(String line) {
        String regex = "[А-Яа-яЁёA-Za-z\\s]+";
        if (!(line = line.trim()).isEmpty()) {
            String[] strings = line.split("/");
            if (strings.length < 3) {
                System.out.println(line + " - неверный формат строки, ожидаем ФИО/10000.12/Департамент");
                return false;
            }
            if (!(strings[0].matches(regex) && strings[2].matches(regex))) {
                System.out.println(line + " - неверный формат ввода, нужно: Буквы/Цифры/Буквы");
                return false;
            }
            if (!strings[1].matches("\\d{5}(\\.\\d{1,2})?")) {
                System.out.println(line + " - некорректный ввод цифр, знаков. Зарплата не может быть отрицательной и иметь меньше 5 знаков");
                return false;
            }
            return true;
        }
        return false;
    }

    public static void systemMessage(int i) {
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

    public static BigDecimal empswap(Map<String, Departament> departments, String key, List<BigDecimal> avgEmp) {
        BigDecimal sal = new BigDecimal(0);
        List<BigDecimal> temp = new ArrayList<>();

        for (Departament dep : departments.values()) {
            if (key.equals("Первый отдел имеет большую среднюю зп") && dep.getName().equals("Первый")) {
                int i = 0;
                // Сохраняю в List данные о зп первого отдела
                dep.getEmployeeList().forEach((c) -> temp.add(c.getSalary()));
                for (BigDecimal number : temp) {
                    // Ищу макс. зп, которая находится между двух средних зп по отделам
                    if (temp.get(i).compareTo(avgEmp.get(0)) > 0 && temp.get(i).compareTo(avgEmp.get(1)) > 0) {
                        sal = temp.get(i);
                    }
                    i++;
                }
                break;
            }
            if (key.equals("Второй отдел имеет большую среднюю зп") && dep.getName().equals("Второй")) {
                int i = 0;
                // Сохраняю в List данные о зп первого отдела
                dep.getEmployeeList().forEach((c) -> temp.add(c.getSalary()));
                for (BigDecimal number : temp) {
                    // Ищу макс. зп, которая находится между двух средних зп по отделам
                    if (temp.get(i).compareTo(avgEmp.get(0)) > 0 && temp.get(i).compareTo(avgEmp.get(1)) > 0) {
                        sal = temp.get(i);
                    }
                    i++;
                }
                break;
            }
            //else continue;
        }
        return sal;
    }

    public static void avg(Map<String, Departament> departments, List<BigDecimal> avgEmp) {
        for (Departament dep : departments.values()) {
            BigDecimal avg = dep.salaryAvg();
            avgEmp.add(avg);
            System.out.println("Средняя заработная плата отдела " + dep.getName() + ": " + avg);
        }
        systemMessage(1);
    }

    @Override
    public void close() {
        System.out.println("Читаемый файл закрыт");
    }
}