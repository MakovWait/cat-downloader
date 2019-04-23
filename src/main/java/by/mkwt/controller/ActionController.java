package by.mkwt.controller;

import by.mkwt.entity.ActionData;
import by.mkwt.service.ActionService;
import by.mkwt.service.util.ActionDataResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private ActionService actionService;
    private ActionDataResourceAssembler assembler;

    @Autowired
    public ActionController(ActionService actionService, ActionDataResourceAssembler assembler) {
        this.actionService = actionService;
        this.assembler = assembler;
    }

    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    public Resources<Resource<ActionData>> getActions() {
        List<Resource<ActionData>> actions = new ArrayList<>();
        for (ActionData actionData : actionService.getExecutedActions().values()) {
            actions.add(assembler.toResource(actionData));
        }

        return new Resources<>(actions,
                linkTo(methodOn(ActionController.class).getActions()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public Resource<ActionData> getAction(@PathVariable Long id) {

        return assembler.toResource(actionService.getAction(id));
    }

    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> executeAction(@RequestBody Map<String, String> imageAction) throws URISyntaxException {
        Resource<ActionData> resource = assembler.toResource(actionService.executeImageAction(imageAction));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

}
