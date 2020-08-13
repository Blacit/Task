package innotechum.task1.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;
    private List<Employee> employeeList;

    public Department(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
    }

    public Department(String name) {
        this(name, new ArrayList<>());
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee emp) {
        employeeList.add(emp);
    }

    public BigDecimal salaryAvg() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Employee employee : employeeList)
            sum = sum.add(employee.getSalary());
        return sum.divide(BigDecimal.valueOf(employeeList.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalSalary(Department dep) {
        BigDecimal sum = BigDecimal.ZERO;
        List<Employee> EmplSecond = dep.getEmployeeList();
        for (Employee empls : EmplSecond) { // Проходим, чтобы посчитать зп по отделу
            sum = sum.add(empls.getSalary()); // Сохраняем информацию в sum
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}