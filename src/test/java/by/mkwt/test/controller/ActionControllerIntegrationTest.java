package by.mkwt.test.controller;

import by.mkwt.controller.ActionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionControllerIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    private ActionController actionController;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.actionController).build();
    }

    @Test
    public void testPostEmptyImageAction() throws Exception {
        mockMvc.perform(post("/actions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostImageAction() throws Exception {
        Map<String, String> action = new HashMap<>();
        action.put("type", "find_and_download");
        action.put("limit", "1");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(action);

        mockMvc.perform(post("/actions").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.length()").value(Integer.parseInt(action.get("limit"))));
    }
}
