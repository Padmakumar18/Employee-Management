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

        System.out.println("------------------------------------------");
        System.out.println("1. View all employee details");
        System.out.println("2. View particular employee detail");
        System.out.println("3. Add new employee");
        System.out.println("4. Update a particular employee detail");
        System.out.println("5. Delete a employee");
        System.out.println("6. Delete all employee details");
        System.out.println("7. Exit");
        System.out.println("------------------------------------------");

        int option=0;
        while (option < 7) {
            System.out.println("Enter the choice");
            option= sc.nextInt();
            Employee employee = new Employee();

            switch (option) {
                case 1:
                    displayAll(session);
                    break;
                case 2: {
                    System.out.println("Enter the employeeId : ");
                    int emp_id = sc.nextInt();
                    Employee emp = session.get(Employee.class,emp_id);
                    System.out.println(emp.toString());
                    break;
                }
                case 3:
                    System.out.println("1. Enter employee name : ");
                    employee.setEmp_name(sc.next());
                    System.out.println("2. Enter employee department : ");
                    employee.setEmp_department(sc.next());
                    System.out.println("4. Enter salary : ");
                    employee.setEmp_salary(sc.nextInt());

                    addNewEmployee(session, employee);

                    System.err.println(employee.toString());
                    break;
                case 4:
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
                case 5:
                    delete(session);
                    break;
                case 6:
                    deleteAll(session);
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

    public static void displayAll(Session session) {
        Query query = session.createQuery("FROM Employee");
        List<org.employee.Employee> employees = query.list();

        for (org.employee.Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public static void delete(Session session) {

    }

    public static void deleteAll(Session session) {

    }

    public static void addNewEmployee(Session session, Employee employee) {
        session.save(employee);
    }
}