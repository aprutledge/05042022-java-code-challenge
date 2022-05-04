package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/employee/report-structure/{id}";
    }

    @Test
    public void testReportingStructure() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl,
                ReportingStructure.class, employeeId).getBody();

        assertEquals(4, reportingStructure.getNumberOfReports());
        assertEquals(employeeId, reportingStructure.getEmployee());
    }
}
