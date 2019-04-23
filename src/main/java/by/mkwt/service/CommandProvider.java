package by.mkwt.service;

import by.mkwt.entity.ActionData;
import by.mkwt.exception.IncorrectActionDataException;
import by.mkwt.service.command.FindAndDownloadImagesAction;
import by.mkwt.service.command.ImageAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandProvider {

    private Map<ActionData.Type, ImageAction> commands;

    @Autowired
    public CommandProvider(FindAndDownloadImagesAction findAndDownloadImagesAction) {
        commands = new HashMap<>();
        commands.put(ActionData.Type.find_and_download, findAndDownloadImagesAction);
    }

    public ImageAction getCommand(ActionData.Type commandType) {
        if (!commands.containsKey(commandType)) {
            throw new IncorrectActionDataException("Incorrect action type");
        }

        return commands.get(commandType);
    }

}
