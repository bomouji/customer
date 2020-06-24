package com.digitalAcademy.customer.api;

import com.digitalAcademy.customer.model.response.GetLoanInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanApiTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    LoanApi loanApi;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        loanApi = new LoanApi(restTemplate);
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void getLoanInfo() throws Exception{
        Long reqId = 1L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseSuccess());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfoResponse(reqId);

        assertEquals("1",res.getId().toString());
        assertEquals("Ok",res.getStatus());
        assertEquals("101-220-2200",res.getAccountPayable());
        assertEquals("101-220-2200",res.getAccountReceivable());
        assertEquals(400000.00,res.getPrincipleAmount());

        verify(restTemplate,times(1)).exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any());
    }

    @DisplayName("Test get loan info by id 2 should return LOAN002")
    @Test
    void getLoanInfoLAN002() throws Exception{
        Long reqParam = 2L;

        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4002());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfoResponse(reqParam);

        assertEquals(null,res.getId());
        assertEquals(null,res.getStatus());
        assertEquals(null,res.getAccountPayable());
        assertEquals(null,res.getAccountReceivable());
        assertEquals(0,res.getPrincipleAmount());

    }

    @DisplayName("Test get loan info by id 3 should return LOAN001")
    @Test
    void getLoanInfoLAN003() throws Exception{
        Long reqParam = 3L;

        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4001());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfoResponse(reqParam);

        assertEquals(null,res.getId());
        assertEquals(null,res.getStatus());
        assertEquals(null,res.getAccountPayable());
        assertEquals(null,res.getAccountReceivable());
        assertEquals(0,res.getPrincipleAmount());

    }

    public static ResponseEntity<String> prepareResponseSuccess() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"0\",\n" +
                "        \"message\": \"success\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"id\": 1,\n" +
                "        \"status\": \"Ok\",\n" +
                "        \"account_payable\": \"101-220-2200\",\n" +
                "        \"account_receivable\": \"101-220-2200\",\n" +
                "        \"principle_amount\": 400000.0\n" +
                "    }\n" +
                "}");
    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4002() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN002\",\n" +
                "        \"message\": \"Loan information not found.\"\n" +
                "    }\n" +
                "}");
    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4001() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN001\",\n" +
                "        \"message\": \"Cannot get loan information\"\n" +
                "    },\n" +
                "    \"data\": \"Cannot get loan information\"\n" +
                "}");
    }
}
