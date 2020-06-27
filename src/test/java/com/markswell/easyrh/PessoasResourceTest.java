package com.markswell.easyrh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markswell.easyrh.model.Pessoas;
import com.markswell.easyrh.model.Setor;
import com.markswell.easyrh.service.SetorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
public class PessoasResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SetorService setorService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URI = "/pessoa";

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void postSetorTest() throws Exception {
        createSetor();

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPessoa("marcos", "silva", "programador", new Setor(1, "desenvolvimento")))))
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
        assertEquals(getFiltered(), response.getContentAsString());
    }

    private String getFiltered() {
        return "{\"id\":2,\"nome\":\"marcos\",\"sobrenome\":\"silva\",\"profissao\":\"programador\",\"setor\":{\"id\":1,\"nome\":\"desenvolvimento\"}}";
    }

    @Test
    @WithMockUser(username = "admin", password = "Qwerty", roles = "USER")
    void updateSetorTest() throws Exception {
        mockMvc.perform(put(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPessoa(2))))
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

    private Pessoas getPessoa(String nome, String sobrenome, String profissao, Setor setor) {
        return new Pessoas(nome, sobrenome, profissao, setor);
    }
    private Pessoas getPessoa(Integer id) {
        return new Pessoas(id);
    }

    private String getResponseContentEqual() {
        return "[{\"id\":2,\"nome\":\"marcos\",\"sobrenome\":\"silva\",\"profissao\":\"programador\",\"setor\":{\"id\":1,\"nome\":\"desenvolvimento\"}}," +
                "{\"id\":3,\"nome\":\"lucas\",\"sobrenome\":\"menezes\",\"profissao\":\"programador\",\"setor\":{\"id\":1,\"nome\":\"desenvolvimento\"}}," +
                "{\"id\":4,\"nome\":\"mateus\",\"sobrenome\":\"ferreira\",\"profissao\":\"programador\",\"setor\":{\"id\":1,\"nome\":\"desenvolvimento\"}}]";
    }

    @BeforeEach
    public void createData() throws Exception {
        createSetor();

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPessoa("marcos", "silva", "programador", new Setor(1, "desenvolvimento")))))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPessoa("lucas", "menezes", "programador", new Setor(1, "desenvolvimento")))))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPessoa("mateus", "ferreira", "programador", new Setor(1, "desenvolvimento")))))
                .andExpect(status().isCreated());
    }

    private void createSetor() {
        setorService.save(new Setor("desenvolvimento"));
    }

}
