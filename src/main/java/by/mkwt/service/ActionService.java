package by.mkwt.service;

import by.mkwt.entity.ActionData;
import by.mkwt.exception.IncorrectActionDataException;
import by.mkwt.exception.ResourceNotFoundException;
import by.mkwt.service.util.IdCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActionService {

    @Value("#{action['required_parameter.type']}")
    private String type;

    @Value("#{exception['action_not_found']}")
    private String actionNotFound;

    @Value("#{exception['action_data_should_have_type']}")
    private String actionDataShouldHaveType;

    private CommandProvider commandProvider;
    private IdCounter idCounter;
    private Map<Long, ActionData> executedActions;

    @Autowired
    public ActionService(CommandProvider commandProvider, IdCounter idCounter) {
        this.commandProvider = commandProvider;
        this.idCounter = idCounter;

        executedActions = new HashMap<>();
    }

    public ActionData executeImageAction(Map<String, String> imageAction) {
        if (!imageAction.containsKey(type)) {
            throw new IncorrectActionDataException(actionDataShouldHaveType);
        }

        ActionData actionData = new ActionData();
        actionData.setId(idCounter.createID());
        actionData.setType(ActionData.Type.valueOf(imageAction.get(type)));

        executedActions.put(actionData.getId(), actionData);

        actionData.setResult(commandProvider.getCommand(actionData.getType()).execute(imageAction));
        actionData.setStatus(ActionData.Status.completed);

        return actionData;
    }

    public ActionData getAction(Long id) {
        if (!executedActions.containsKey(id)) {
            throw new ResourceNotFoundException(actionNotFound);
        }

        return executedActions.get(id);
    }

    public Map<Long, ActionData> getExecutedActions() {
        return executedActions;
    }

}
