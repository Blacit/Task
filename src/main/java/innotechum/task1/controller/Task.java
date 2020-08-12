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
        String DownloadFile = "C:\\Users\\VGilenko\\IdeaProjects\\Task\\src\\main\\resources\\Out.txt";
        Map<String, Departament> departments = new HashMap<>();
        String path = args.length > 0 ? args[0] : null;

        try {
            read(path, departments);
            systemMessage("Выбрали корректные варианты");
            transferToDepartment(departments, DownloadFile);
            systemMessage("Перевели из отдела в отдел");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void transferToDepartment(Map<String, Departament> departments, String downloadFile) {
        List<String> download = new ArrayList<>();
        for (Departament depFrom : departments.values()) {
            for (Departament depTo : departments.values()) {
                if (depFrom.equals(depTo))
                    continue;
                List<Employee> employeeList = new ArrayList<>();
                List<BigDecimal> newAvgSalary = new ArrayList<>();
                if (depFrom.salaryAvg().compareTo(depTo.salaryAvg()) > 0) {
                    employeeList.addAll(
                            depFrom.getEmployeeList().stream()
                                    .filter(emp -> emp.getSalary().compareTo(depFrom.salaryAvg()) < 0 && emp.getSalary().compareTo(depTo.salaryAvg()) > 0)
                                    .collect(Collectors.toList()));

                }
                for (Employee employee : employeeList) {
                    System.out.println("Перевод из " + depFrom.getName() + " в " + depTo.getName() + " сотрудника " + employee.getName() + ". Средняя зп отдела была: " + depFrom.salaryAvg() + " Стала: " + "Новая ср. зп.");
                    download.add("Перевод из " + depFrom.getName() + " в " + depTo.getName() + " сотрудника " + employee.getName());
                }
            }
        }
        uploadToFile(download, downloadFile);
    }

    private static void uploadToFile(List download, String path) {
        int i = 0;
        try (FileWriter writer = new FileWriter(path, false)) {
            while (i < download.size()) {
                writer.write((String) download.get(i));
                writer.write('\n');
                i++;
            }
            System.out.println("Читаемый файл закрыт");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void read(String path, Map<String, Departament> departments) throws IOException {
        assert path != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1251")); br) {
            String line;
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
            System.out.println("Читаемый файл закрыт");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не был найден, проверьте путь");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Исправьте путь к файлу, выходите за массив");
        } catch (NullPointerException e) {
            throw new RuntimeException("Вы забыли прописать путь к файлу");
        }
    }

    private static boolean check(String line) {
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
            if (!strings[1].matches("\\d+(\\.\\d{1,2})?")) {
                System.out.println(line + " - некорректный ввод цифр, знаков. Зарплата не может быть отрицательной и иметь меньше 5 знаков");
                return false;
            }
            return true;
        }
        return false;
    }

    public static void systemMessage(String message) {
        int line = message.length();
        int i = 0;
        while (i <= line) {
            System.out.print("-");
            i++;
        }
        System.out.println();
        System.out.println(message);
        i = 0;
        while (i <= line) {
            System.out.print("-");
            i++;
        }
        System.out.println();
    }

    @Override
    public void close() {
        System.out.println("Читаемый файл закрыт");
    }
}