package by.mkwt.test.service;

import by.mkwt.entity.ActionData;
import by.mkwt.service.ActionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionServiceIntegrationTest {

    @Autowired
    private ActionService actionService;

    @Test
    public void testExecuteImageAction() {
        Map<String, String> action = new HashMap<>();
        action.put("type", "find_and_download");
        action.put("limit", "1");
        ActionData actionData = actionService.executeImageAction(action);
        assertThat(actionData).isNotNull().hasFieldOrProperty("result").hasFieldOrPropertyWithValue("status", ActionData.Status.completed);
    }

}
