package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.GroupRepository;
import org.udg.pds.springtodo.repository.TagRepository;

import java.rmi.server.ServerCloneException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserService userService;

    public GroupRepository crud() {
        return groupRepository;
    }

    public Group getGroup(Long id) {
        Optional<Group> ot = groupRepository.findById(id);
        if (!ot.isPresent())
            throw new ServiceException("Tag does not exists");
        else
            return ot.get();
    }

    public Group addGroup(String name, String description,  Long userId) {
        try {
            User user = userService.getUser(userId);
            Group group = new Group(name, description);
            group.setUser(user);

            user.addGroup(group);
            groupRepository.save(group);
            return group;
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
            // We catch the normal exception and then transform it in a EJBException
            throw new ServiceException(ex.getMessage());
        }
    }


    public Collection<Group> getGroups(Long id) {
        Optional<User> u = userService.crud().findById(id);
        if (!u.isPresent()) throw new ServiceException("User does not exists");
        return u.get().getGroups();
    }
}
