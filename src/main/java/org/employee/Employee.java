package org.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emp_id;

    private String emp_name;
    private String emp_department;
    private long emp_salary;

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_department() {
        return emp_department;
    }

    public void setEmp_department(String emp_department) {
        this.emp_department = emp_department;
    }

    public long getEmp_salary() {
        return emp_salary;
    }

    public void setEmp_salary(long emp_salary) {
        this.emp_salary = emp_salary;
    }

    @Override
    public String toString() {
        return String.format("+-------------+----------------+-----------------+------------+\n"
                + "| Employee ID | Name           | Department      | Salary     |\n"
                + "+-------------+----------------+-----------------+------------+\n"
                + "| %-10d  | %-14s | %-15s | %-10d |\n"
                + "+-------------+----------------+-----------------+------------+",
                emp_id, emp_name, emp_department, emp_salary);
    }
}