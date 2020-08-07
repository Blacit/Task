package innotechum.task1.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Departament {

    private String name;
    private List<Employee> employeeList;

    public Departament(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
    }

    public Departament(String name) {
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
        return sum.divide(BigDecimal.valueOf(employeeList.size()), 2);
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}