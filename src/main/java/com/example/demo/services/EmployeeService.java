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

    public List<Map<String, Integer>> getFinalResult() {
        List<Map<String, Integer>> result = new ArrayList<>();
        var sortedMapHours = getMostWorkedProject();

        for (var entry : sortedMapHours.entrySet()) {
            Map<String, Integer> projectMap = new HashMap<>();
            var projectEmployees = projectsMap.get(entry.getKey());

            projectMap.put("project_id", entry.getKey());
            projectMap.put("days", entry.getValue());

            for (int i = 0; i < projectEmployees.size(); i++) {
                String key = "employee_" + i;
                projectMap.put(key, projectEmployees.get(i).getId());
            }
            result.add(projectMap);
        }

        System.out.println(result);

        return result;
    }

    public Map<Integer, Integer> getMostWorkedProject() {
        Map<Integer, Integer> projectsMapHours = new HashMap<>();

        for (Employee employee : employeeList) {
//            Predicate<Employee> byProjectId = e -> e.getProjectId() == employee.getProjectId();
//            var result = employeeList.stream().filter(byProjectId).collect(Collectors.toList());

            var projectList = projectsMap.get(employee.getProjectId());

            int sumDays = 0;
            for (Employee value : projectList) {
                sumDays += value.getDays();
            }
            projectsMapHours.put(employee.getProjectId(), sumDays);

        }

        LinkedHashMap<Integer, Integer> reverseSortedProjects = new LinkedHashMap<>();


        projectsMapHours.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedProjects.put(x.getKey(), x.getValue()));

        return reverseSortedProjects;
    }
}
