package com.digitalAcademy.customer.controller;

import com.digitalAcademy.customer.api.LoanApi;
import com.digitalAcademy.customer.contoller.LoanController;
import com.digitalAcademy.customer.model.response.GetLoanInfoResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    LoanApi loanApi;

    @InjectMocks
    LoanController loanController;

    private MockMvc mockMvc;

    private static final String urlLoan = "/loan/";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void getLoanInfo() throws Exception{
        GetLoanInfoResponse loanInfoModel = new GetLoanInfoResponse();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("Ok");
        loanInfoModel.setAccountPayable("101-220-2200");
        loanInfoModel.setAccountReceivable("101-220-2200");
        loanInfoModel.setPrincipleAmount(400000.00);

        when(loanApi.getLoanInfoResponse(1L)).thenReturn(loanInfoModel);

        MvcResult mvcResult= mockMvc.perform(get(urlLoan + ""+ 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp= new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(1,resp.get("id"));
        assertEquals("Ok",resp.get("status"));
        assertEquals("101-220-2200",resp.get("account_payable"));
        assertEquals("101-220-2200",resp.get("account_receivable"));
        assertEquals(400000.0,resp.get("principle_amount"));

    }


}
