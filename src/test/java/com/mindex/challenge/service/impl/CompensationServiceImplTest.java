package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
        Compensation compensation = Compensation.builder()
                .employee("16a596ae-edd3-4847-99fe-c4518e82c86f")
                .salary(95000)
                .effectiveDate("12/31/99")
                .build();

        // Test Create
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, compensation,
                Compensation.class).getBody();
        assertNotNull(createdCompensation);
        assertCompensationEquivalence(compensation, createdCompensation);

        // Test Read
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                createdCompensation.getEmployee()).getBody();
        assertNotNull(readCompensation);
        assertCompensationEquivalence(compensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
