package com.expenses.expense_sharing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.expense_sharing.domain.Group;
import com.expenses.expense_sharing.service.GroupService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public Group create(@RequestBody CreateGroupRequest request) {

        if (request.getCreatedByUserId() == null) {
            throw new RuntimeException("createdByUserId is required");
        }

        return groupService.createGroup(
                request.getName(),
                request.getCreatedByUserId(),
                request.getMemberIds()
        );
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping("/{groupId}/members/{userId}")
    public void addMember(
            @PathVariable Long groupId,
            @PathVariable Long userId
    ) {
        groupService.addMember(groupId, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Group> getGroupsForUser(@PathVariable Long userId) {
        return groupService.getGroupsForUser(userId);
    }

    

}
@Data
class CreateGroupRequest {
    private String name;
    private Long createdByUserId;
    private List<Long> memberIds;
}
