package org.employee;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Configuration cfg = new Configuration();
        cfg.configure();
        cfg.addAnnotatedClass(org.employee.Employee.class);
        SessionFactory sf = cfg.buildSessionFactory();

        int option = 0;
        while (option != 7) {
            System.out.println("------------------------------------------");
            System.out.println("1. View all employee details");
            System.out.println("2. View particular employee detail");
            System.out.println("3. Add new employee");
            System.out.println("4. Update an employee detail");
            System.out.println("5. Delete an employee");
            System.out.println("6. Delete all employee details");
            System.out.println("7. Exit");
            System.out.println("------------------------------------------");

            System.out.print("Enter your choice: ");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    displayAll(sf);
                    break;
                case 2:
                    System.out.print("Enter the employee ID: ");
                    int empId = sc.nextInt();
                    viewEmployee(sf, empId);
                    break;
                case 3:
                    sc.nextLine();
                    Employee newEmployee = new Employee();
                    System.out.print("Enter employee name: ");
                    newEmployee.setEmp_name(sc.nextLine());
                    System.out.print("Enter employee department: ");
                    newEmployee.setEmp_department(sc.nextLine());
                    System.out.print("Enter salary: ");
                    newEmployee.setEmp_salary(sc.nextInt());

                    addNewEmployee(sf, newEmployee);
                    break;
                case 4:
                    System.out.print("Enter employee ID to update details: ");
                    int updateId = sc.nextInt();
                    System.out.println("Enter 1 for Name, 2 for Department, 3 for Salary: ");
                    int updateOption = sc.nextInt();
                    sc.nextLine();
                    update(sf, updateId, updateOption, sc);
                    break;
                case 5:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = sc.nextInt();
                    delete(sf, deleteId);
                    break;
                case 6:
                    deleteAll(sf);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sf.close();
        sc.close();
    }

    public static void displayAll(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

            if (employees.isEmpty()) {
                System.out.println("No records found.");
            } else {
                // Print table header
                System.out.println("+-------------+----------------+-----------------+------------+");
                System.out.println("| Employee ID | Name           | Department      | Salary     |");
                System.out.println("+-------------+----------------+-----------------+------------+");

                // Print each employee record
                for (Employee emp : employees) {
                    System.out.printf("| %-10d  | %-14s | %-15s | %-10d |\n",
                            emp.getEmp_id(), emp.getEmp_name(), emp.getEmp_department(), emp.getEmp_salary());
                    System.out.println("+-------------+----------------+-----------------+------------+");
                }

                // Print table footer
                // System.out.println("+------------+----------------+-----------------+------------+");
            }
        }
    }

    public static void viewEmployee(SessionFactory sf, int empId) {
        try (Session session = sf.openSession()) {
            Employee emp = session.get(Employee.class, empId);
            if (emp == null) {
                System.out.println("Employee not found.");
            } else {
                System.out.println(emp);
            }
        }
    }

    public static void addNewEmployee(SessionFactory sf, Employee employee) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            System.out.println("Employee added successfully.");
        }
    }

    public static void update(SessionFactory sf, int empId, int option, Scanner sc) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee emp = session.get(Employee.class, empId);

            if (emp == null) {
                System.out.println("Employee not found.");
                return;
            }

            switch (option) {
                case 1:
                    System.out.print("Enter new name: ");
                    emp.setEmp_name(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new department: ");
                    emp.setEmp_department(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new salary: ");
                    emp.setEmp_salary(sc.nextInt());
                    break;
                default:
                    System.out.println("Invalid update option.");
                    return;
            }

            session.merge(emp);
            transaction.commit();
            System.out.println("Employee updated successfully.");
        }
    }

    public static void delete(SessionFactory sf, int empId) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee emp = session.get(Employee.class, empId);

            if (emp != null) {
                session.remove(emp);
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }

            transaction.commit();
        }
    }

    public static void deleteAll(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            int deletedCount = session.createQuery("DELETE FROM Employee").executeUpdate();
            transaction.commit();
            System.out.println(deletedCount + " records deleted.");
        }
    }
}
