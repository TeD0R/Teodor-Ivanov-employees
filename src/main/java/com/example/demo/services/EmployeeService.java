package com.example.demo.services;

import com.example.demo.models.Employee;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeService implements com.example.demo.services.interfaces.EmployeeService {

    //employee repo
    //....


    private final List<Employee> employeeList;
    private final Map<Integer, List<Employee>> projectsMap;

    public EmployeeService() {
        employeeList = new ArrayList<Employee>();
        projectsMap = new HashMap<>();
    }

    @Override
    public void handleLine(String line) {
        String[] parts = line.replaceAll("\\s+", "").split(",");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int employeeId = 0, projectId = 0;
        Date dateFrom = null, dateTo = null;

        if (parts[0] != null) {
            employeeId = Integer.parseInt(parts[0]);
        }

        if (parts[1] != null) {
            projectId = Integer.parseInt(parts[1]);
        }

        if (parts[2] != null) {
            try {
                dateFrom = format.parse(parts[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (!parts[3].toUpperCase().equals("NULL")) {
            try {
                dateTo = format.parse(parts[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            dateTo = new Date();
            format.format(dateTo);
        }

        if (employeeId != 0 && projectId != 0 && dateTo != null && dateFrom != null) {
            Employee employee = new Employee(employeeId, projectId, dateFrom, dateTo);
            employeeList.add(employee);

            if (!projectsMap.containsKey(projectId)) {
                projectsMap.put(projectId, new ArrayList<Employee>());
            }
            projectsMap.get(projectId).add(employee);
        } else {
            throw new RuntimeException("Wrong Line Format Exception");
        }

    }

    public Map<Integer, Integer> getMostWorkedProject() {
        Map<Integer, Integer> projectsMapHours = new HashMap<>();

        for (Employee employee : employeeList) {
//            Predicate<Employee> byProjectId = e -> e.getProjectId() == employee.getProjectId();
//            var result = employeeList.stream().filter(byProjectId).collect(Collectors.toList());

            var projectList = projectsMap.get(employee.getProjectId());
            int sum_hours = 0;
            for (int i = 0; i < projectList.size(); i++) {
                sum_hours += projectList.get(i).getHours();
            }
            projectsMapHours.put(employee.getProjectId(), sum_hours);
        }

        LinkedHashMap<Integer, Integer> reverseSortedProjects = new LinkedHashMap<>();


        projectsMapHours.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedProjects.put(x.getKey(), x.getValue()));


        System.out.println(reverseSortedProjects);

        return projectsMapHours;
    }

    public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList);
    }
}
