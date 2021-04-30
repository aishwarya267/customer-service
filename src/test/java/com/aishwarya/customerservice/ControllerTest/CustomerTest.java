package com.aishwarya.customerservice.ControllerTest;

import com.aishwarya.customerservice.CustomerServiceApplication;
import com.aishwarya.customerservice.model.Customer;
import com.aishwarya.customerservice.repository.CustomerRepository;
import com.aishwarya.util.AbstractTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CustomerTest extends AbstractTestConfig {

    private final Logger logger = LoggerFactory.getLogger(CustomerTest.class);
    private final String error_message = "Error occured while testing..!";

    @Autowired
    CustomerRepository customerRepository;

    MvcResult mvcResult;

    MockHttpServletResponse result;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @After
    public void tearDown() {
        final File file = new File(System.getProperty("user.dir") + "//data//test.mv");
        file.delete();
    }

    @Before
    public void setUp() {
        super.setUp();
        customerRepository.deleteAll();
    }


    @Test
    public void testSaveCustomerSuccess() {
        final String url = "/customers";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO());

            mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }


    @Test
    public void testSaveCustomerFail() {
        final String url = "/customers";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO1());
            customerRepository.save(createCustomerDTO1());
            mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(400, result.getStatus());
    }


    @Test
    public void testUpdateCustomerSuccess() {
        final String url = "/customers/3";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO());
            customerRepository.save(createCustomerDTO1());
            mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testUpdateCustomerFail() {
        final String url = "/customers/3";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO());
            mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testFetchAllCustomer() {
        final String url = "/customers";

        try {
            String inputJson = mapToJson(createCustomerDTO1());
            customerRepository.save(createCustomerDTO1());
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testFetchAllCustomerNotFound() {
        final String url = "/customers";

        try {
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testFetchSpecificCustomer() {
        final String url = "/customers?customerNumber=3";
        try {
            customerRepository.save(createCustomerDTO1());
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testFetchSpecificCustomerNotFound() {
        final String url = "/customers?customerNumber=30";
        try {
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }


    @Test
    public void testDeleteCustomerRecord() {
        final String url = "/customers?customerNumber=3";
        try {
            customerRepository.save(createCustomerDTO1());
            mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("accept-language", "en")).andReturn();
        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testDeleteCustomerRecordNotFound() {
        final String url = "/customers?customerNumber=300";
        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("accept-language", "en")).andReturn();
        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }

    private Customer createCustomerDTO() {
        final Customer customerDTO = new Customer();
        customerDTO.setCustomerName("abcd");
        customerDTO.setCountryCode("IN");
        customerDTO.setCustomerNumber(2);
        customerDTO.setAddress("afhedigh");
        return customerDTO;
    }

    private Customer createCustomerDTO1() {
        final Customer customerDTO = new Customer();
        customerDTO.setCustomerName("afdsf");
        customerDTO.setCountryCode("US");
        customerDTO.setCustomerNumber(3);
        customerDTO.setAddress("ncvxmndfk");
        return customerDTO;
    }
}
