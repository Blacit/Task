package innotechum.task1.entity;

import java.math.BigDecimal;
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

    public Department() {
    }

    public  List<Employee> getEmployeeList() {
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

    public Double salaryAvg() {
        return employeeList.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
    }

        @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}