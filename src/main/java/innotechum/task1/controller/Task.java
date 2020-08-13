package innotechum.task1.controller;

import innotechum.task1.entity.Department;
import innotechum.task1.entity.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task implements AutoCloseable {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Не хватает аргументов");
            return;
        }

        Map<String, Department> departments = read(args[0]);
        if (!departments.isEmpty()) {
            systemMessage("Выбрали корректные варианты");
            List<String> strList = transferToDepartment(departments);
            uploadToFile(strList, args[1]);
            systemMessage("Перевели из отдела в отдел");
        }
    }

    public static List<String> transferToDepartment(Map<String, Department> departments) {
        List<String> download = new ArrayList<>();

        for (Department depFrom : departments.values()) {
            for (Department depTo : departments.values()) {
                if (depFrom.equals(depTo))
                    continue;
                List<Employee> employeeList = new ArrayList<>();
                if (depFrom.salaryAvg().compareTo(depTo.salaryAvg()) > 0) {
                    employeeList.addAll(
                            depFrom.getEmployeeList().stream()
                                    .filter(emp -> emp.getSalary().compareTo(depFrom.salaryAvg()) < 0
                                            && emp.getSalary().compareTo(depTo.salaryAvg()) > 0)
                                    .collect(Collectors.toList()));
                    // Берём лист с зп, одну зп прибавляем к отделу, у которого меньше средняя зп
                    // выясняем сколько работников в отделе, делим сумму на количество работников
                    // Результат сохраняем в newAvgSalary результат
                    //BigDecimal totalSalFrom = depFrom.getTotalSalary(depFrom);
                    //BigDecimal totalSalTo = depFrom.getTotalSalary(depTo);
                    for(Employee employee : employeeList) {
                        List<Employee> EmplSecond = depFrom.getEmployeeList();
                        BigDecimal totalSalFrom = depFrom.getTotalSalary(depFrom);
                        totalSalFrom = totalSalFrom.add(employee.getSalary());
                        totalSalFrom = totalSalFrom.divide(BigDecimal.valueOf(EmplSecond.size()), 2, RoundingMode.HALF_UP); // Не то кол-во сотрудников
                        System.out.println("Перевод из " + depFrom.getName() + " в " + depTo.getName() +
                                " сотрудника " + employee.getName() + ". Средняя зп отдела была: " +
                                depFrom.salaryAvg() + " Стала: " + totalSalFrom);
                        download.add("Перевод из " + depFrom.getName() + " в " + depTo.getName() +
                                " сотрудника " + employee.getName());
                    }
                }
            }
        }
        return download;
    }

    private static void uploadToFile(List<String> download, String path) {
        try (FileWriter writer = new FileWriter(path, false)) {
            for (String str : download) {
                writer.write(str + '\n');
            }
            System.out.println("Читаемый файл закрыт");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Map<String, Department> read(String path) {
        Map<String, Department> departments = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            String line;
            while ((line = br.readLine()) != null) {
                if (check(line)) {
                    String[] strings = line.split("/");
                    Employee emp = new Employee(strings[0].trim(), new BigDecimal(strings[1].trim()));
                    if (!departments.containsKey(strings[2].trim())) {
                        departments.put(strings[2].trim(), new Department(strings[2].trim()));
                    }
                    departments.get(strings[2].trim()).addEmployee(emp);
                    System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " - корректна, обрабатываем");
                }
            }
            System.out.println("Читаемый файл закрыт");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не был найден, проверьте путь");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
        return departments;
    }

    private static boolean check(String line) {
        String regex = "[А-Яа-яЁёA-Za-z\\s]+";
        if (!(line = line.trim()).isEmpty()) {
            String[] strings = line.split("/");
            if (strings.length < 3) {
                System.out.println(line + " - неверный формат строки, ожидаем ФИО/10000.12/Департамент");
                return false;
            }
            if (!(strings[0].trim().matches(regex) && strings[2].trim().matches(regex))) {
                System.out.println(line + " - неверный формат ввода, нужно: Буквы/Цифры/Буквы");
                return false;
            }
            if (!strings[1].trim().matches("\\d+(\\.\\d{1,2})?")) {
                System.out.println(line + " - некорректный ввод цифр, знаков. Зарплата не может быть отрицательной");
                return false;
            }
            return true;
        }
        return false;
    }

    public static void systemMessage(String message) {
        System.out.println("-".repeat(message.length()));
        System.out.println(message);
        System.out.println("-".repeat(message.length()));
    }

    @Override
    public void close() {
        System.out.println("Читаемый файл закрыт");
    }
}