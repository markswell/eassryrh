package com.markswell.easyrh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markswell.easyrh.model.Setor;
import com.markswell.easyrh.resource.SetorResource;
import com.markswell.easyrh.service.SetorService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
public class SetorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private SetorResource setorResource;

    @Autowired
    private SetorService service;

    private final String URI = "/setor";

    @Test
    public void contextLoads() {
        assertThat(setorResource).isNotNull();
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Deu bom!")));
    }
    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void postSetorTest() throws Exception {
        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getSetor("vendas"))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void getSetorTest() throws Exception {
        createData();
        MockHttpServletResponse response = createGet(URI);
        assertEquals(getResponseContentEqual(), response.getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void getByIdSetorTest() throws Exception {
        createData();
        MockHttpServletResponse response = createGet(URI.concat("/2"));
        assertEquals("{\"id\":2,\"nome\":\"compras\"}", response.getContentAsString());
    }

    private MockHttpServletResponse createGet(String uri) throws Exception {
        return mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    private Setor getSetor(String setor) {
        return new Setor(setor);
    }

    private String getResponseContentEqual() {
        return "[{\"id\":1,\"nome\":\"vendas\"}," +
                "{\"id\":2,\"nome\":\"compras\"}," +
                "{\"id\":3,\"nome\":\"marketing\"}]";
    }

    public void createData() throws Exception {
        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getSetor("vendas"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getSetor("compras"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getSetor("marketing"))))
                .andExpect(status().isCreated());
    }

}

