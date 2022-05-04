package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeService employeeService;

    public ReportingStructureServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @param employeeId the id of the employee to get the number of reports
     * @return a filled out ReportingStructure Object
     */
    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Starting recursive traverse for {}.", employeeId);
        // Subtract 1 to not count top level employee
        return new ReportingStructure(employeeId, countReportNodes(employeeId) - 1);
    }

    private int countReportNodes(String employeeId) {
        Employee rootEmployee = employeeService.read(employeeId);

        if (rootEmployee.getDirectReports() != null) {
            int count = 0;
            for (Employee employee: rootEmployee.getDirectReports()) {
                count += countReportNodes(employeeService.read(employee.getEmployeeId()).getEmployeeId());
            }
            return count + 1;
        } else {
            // Leaf Node
            return 1;
        }
    }
}
