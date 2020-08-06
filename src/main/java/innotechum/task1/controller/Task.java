package innotechum.task1.controller;

import innotechum.task1.entity.Department;
import innotechum.task1.entity.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task implements AutoCloseable {

    public static List<BigDecimal> avgEmp = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Map<String, Department> departments = new HashMap<>();

        String path = args.length > 0 ? args[0] : null;

        assert path != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            String line; // Как мне объявить line в while
            while ((line = br.readLine()) != null) {
                if (check(line)) {
                    String[] strings = line.split("/");
                    Employee emp = new Employee(strings[0], new BigDecimal(strings[1]));
                    if (!departments.containsKey(strings[2])) {
                        departments.put(strings[2], new Department(strings[2]));
                    }
                    departments.get(strings[2]).addEmployee(emp);
                    System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            systemMessage(0);
            avg(departments);

            String key;
            if (avgEmp.get(0).compareTo(avgEmp.get(1)) > 0) {
                key = "Первый отдел имеет большую среднюю зп";
            } else {
                key = "Второй отдел имеет большую среднюю зп";
            }
            System.out.println(empswap(departments, key));

            /*List<BigDecimal> temp = new ArrayList<BigDecimal>();
            BigDecimal sal = new BigDecimal(0);
                for (Department dep : departments.values()) {
                    // делаем список зп первого отдела
                    dep.getEmployeeList().forEach((c) -> temp.add(c.getSalary()));
                    // открываем первый отдел
                    if (avgEmp.get(0).compareTo(avgEmp.get(1)) > 0) {
                        // сверяем зп
                        if (sal.compareTo(temp.get(0)) < 0){
                            sal = temp.get(0);
                    }
                }
            }
            System.out.println(sal);*/
            // Находим сотрудника в отделе с наибольшей средней зп,
            // который будет иметь зп между двумя средними отделов



            /*if (avgEmp.get(0) > avgEmp.get(1)) {
                for (Department dep : departments.values()) {
                    if (avgEmployee > departments.values().getEmployeeList(){
                        Double avgEmployee = dep.salaryAvg();
                    }
                    avgEmp.add(avg);
                    System.out.println("Средняя заработная плата отдела " + dep.getName   () + ": " + avg);
                }
            }*/

            //for (Map.Entry<String, Department> entry : departments.entrySet()) {
            //    if(avgEmployee > entry.getValue().getEmployeeList().forEach((c)));
            // тут продолжение
            //}

            /*List<BigDecimal> salarys1 = new ArrayList<>();
            Double avgEmployee = 0.0;
            for (Map.Entry<String, Department> entry : departments.entrySet()) {
                String key = entry.getKey();
                entry.getValue().getEmployeeList().forEach((c) -> salarys1.add(c.getSalary()));
                if (key.equals("Первый")) {
                    for (BigDecimal number : salarys1) {
                        System.out.println(number);
                    }
                }
                if (key.equals("Второй")) {*/

            //}
            //systemMessage(2);

            //}
            //}
            //for (Double number : salarys) {
            //    System.out.println(number);

            //for (Map.Entry<String, Department> entry : departments.entrySet())
            //   System.out.println(entry.getKey() + " - " + entry.getValue());

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

    public static BigDecimal empswap(Map<String, Department> departments, String key) {
        BigDecimal sal = new BigDecimal(0);
        List<BigDecimal> temp = new ArrayList<BigDecimal>();

        for (Department dep : departments.values()) {
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
                if (temp.get(i).compareTo(avgEmp.get(1)) > 0 && temp.get(i).compareTo(avgEmp.get(0)) > 0) {
                    sal = temp.get(i);
                    i++;
                }
                break;
            }
            //else continue;
        }
        return sal;
    }

    public static void avg(Map<String, Department> departments) {
        for (Department dep : departments.values()) {
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