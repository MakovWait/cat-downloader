package by.mkwt.service.util;

import by.mkwt.controller.ActionController;
import by.mkwt.entity.ActionData;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ActionDataResourceAssembler implements ResourceAssembler<ActionData, Resource<ActionData>> {

    @Override
    public Resource<ActionData> toResource(ActionData actionData) {

        return new Resource<>(actionData,
                linkTo(methodOn(ActionController.class).getAction(actionData.getId())).withSelfRel(),
                linkTo(methodOn(ActionController.class).getActions()).withRel("actions"));
    }

}
