package com.example.guardianmessenger.accounts;

import java.util.HashSet;
import java.util.Set;

public class EmployeeDB {
    private Set<Employee> db = new HashSet<>();

    public static EmployeeDB getDB() {
        return new EmployeeDB();
    }

    public void addEmployee(Employee employee) {
        db.add(employee);
    }

    public void updateEmployee(Employee employee) {
        for (Employee e : db) {
            if (e.getID() == employee.getID()) {
                e.updateInfo(employee.getInfo());
                return;
            }
        }
    }

    public void removeEmployee(String employeeId) {
        for (Employee e : db) {
            if (e.getID() == employeeId) {
                db.remove(e);
                return;
            }
        }
    }

    public Employee getEmployee(String id) {
        for (Employee e : db) {
            if (e.getID() == id) {
                return e;
            }
        }
        return null;
    }
}
