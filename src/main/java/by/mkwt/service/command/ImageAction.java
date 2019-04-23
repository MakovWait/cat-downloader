package by.mkwt.service.command;

import by.mkwt.exception.IncorrectActionDataException;

import java.util.List;
import java.util.Map;

public interface ImageAction {

    List<String> execute(Map<String, String> actionBody);

}
