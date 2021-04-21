package com.example.demo.controllers;

import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/readFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Map<String, String> readFile(@RequestPart("file") MultipartFile file) {
        Map<String, String> json = new HashMap<String, String>();


        try {
            InputStream inputStream = file.getInputStream();
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .forEach(line -> employeeService.handleLine(line));
        } catch (Exception e) {
            e.printStackTrace();
        }

        var projects_ordered = employeeService.getMostWorkedProject();

        return json;
    }



}
