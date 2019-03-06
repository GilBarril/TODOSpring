package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.service.GroupService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;



@RequestMapping("/groups")
@RestController
public class GroupController extends BaseController{

    @Autowired
    GroupService groupService;
    @PostMapping(consumes = "application/json")
    public String addGroup(@Valid @RequestBody GroupController.R_Group group, HttpSession session) {

        Long userId = getLoggedUser(session);


        if (group.description == null) {
            group.description = "";
        }

        groupService.addGroup(group.name, group.description, userId);

        return BaseController.OK_MESSAGE;
    }


    static class R_Group {
        @NotNull
        public String name;

        public String description;
    }


}
