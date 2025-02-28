package org.employee;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Configuration cfg = new Configuration();

        cfg.configure();
        cfg.addAnnotatedClass(org.employee.Employee.class);

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

        int option = 0;
        Employee employee = new Employee();
        while (option < 7) {
            System.out.println("------------------------------------------");
            System.out.println("1. View all employee details");
            System.out.println("2. View particular employee detail");
            System.out.println("3. Add new employee");
            System.out.println("4. Update a employee detail");
            System.out.println("5. Delete a employee");
            System.out.println("6. Delete all employee details");
            System.out.println("7. Exit");
            System.out.println("------------------------------------------");

            System.out.println("Enter the choice");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    displayAll(session);
                    break;
                case 2: {
                    System.out.println("Enter the employeeId : ");
                    int emp_id = sc.nextInt();
                    viewEmployee(session, employee, emp_id);
                    break;
                }
                case 3:
                    System.out.println("1. Enter employee name : ");
                    employee.setEmp_name(sc.next());
                    System.out.println("2. Enter employee department : ");
                    employee.setEmp_department(sc.next());
                    System.out.println("4. Enter salary : ");
                    employee.setEmp_salary(sc.nextInt());

                    addNewEmployee(session, employee, transaction);

                    System.err.println(employee.toString());
                    break;
                case 4: {
                    System.out.println("Enter the employeeID to update details ");
                    int emp_id = sc.nextInt();
                    System.out.println("Enter 1 for Name , 2 for Department , 3 for salary ");
                    int updateOption = sc.nextInt();
                    switch (updateOption) {
                        case 1:
                            employee.setEmp_name(sc.nextLine());
                            break;
                        case 2:
                            employee.setEmp_department(sc.nextLine());
                            break;
                        case 3:
                            employee.setEmp_salary(sc.nextInt());
                            break;
                    }
                    update(session, employee, emp_id);
                    break;
                }
                case 5:
                    System.out.println("Enter employee ID to delete");
                    int emp_id = sc.nextInt();
                    delete(session, transaction, emp_id);
                    break;
                case 6:
                    deleteAll(session, transaction);
                    break;
                default:
                    break;
            }
        }

        transaction.commit();
        session.close();
        sf.close();
    }

    public static void update(Session session, Employee employee, int Id) {

    }

    public static void viewEmployee(Session session, Employee employee, int emp_id) {
        Employee emp = session.get(Employee.class, emp_id);
        if (emp == null) {
            System.out.println("DB Empty");
        } else {
            System.out.println(emp.toString());
        }
    }

    public static void displayAll(Session session) {
        Query query = session.createQuery("FROM Employee");
        List<org.employee.Employee> employees = query.list();

        if (employees == null) {
            System.out.println("DB Empty");
        } else {
            for (org.employee.Employee emp : employees) {
                System.out.println(emp);
            }
            // System.out.println(employees.toString());
        }

    }

    public static void delete(Session session, Transaction transaction, int emp_id) {

        Employee emp = session.get(Employee.class, emp_id);
        if (emp != null) {
            session.remove(emp);
        }
        transaction.commit();
        transaction = session.beginTransaction();
    }

    public static void deleteAll(Session session, Transaction transaction) {
        session.createQuery("DELETE FROM Employee").executeUpdate();
        transaction.commit();
        transaction = session.beginTransaction();
    }

    public static void addNewEmployee(Session session, Employee employee, Transaction transaction) {
        session.save(employee);
        transaction.commit();
        transaction = session.beginTransaction();
    }
}
