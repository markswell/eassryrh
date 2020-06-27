package com.markswell.easyrh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markswell.easyrh.model.Setor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
public class SetorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URI = "/setor";

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
        MockHttpServletResponse response = createGet(URI);
        assertEquals(getResponseContentEqual(), response.getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void getByIdSetorTest() throws Exception {
        MockHttpServletResponse response = createGet(URI.concat("/2"));
        assertEquals("{\"id\":2,\"nome\":\"compras\"}", response.getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void updateSetorTest() throws Exception {
        mockMvc.perform(put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getSetor(2, "diretoria"))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void deleteSetorTest() throws Exception {
        mockMvc.perform(delete(URI.concat("/2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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
    private Setor getSetor(Integer id, String setor) {
        return new Setor(id, setor);
    }

    private String getResponseContentEqual() {
        return "[{\"id\":1,\"nome\":\"vendas\"}," +
                "{\"id\":2,\"nome\":\"compras\"}," +
                "{\"id\":3,\"nome\":\"marketing\"}]";
    }

    @BeforeEach
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

