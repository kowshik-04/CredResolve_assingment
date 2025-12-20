package com.expenses.expense_sharing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.expenses.expense_sharing.domain.Group;
import com.expenses.expense_sharing.domain.GroupMember;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.GroupMemberRepository;
import com.expenses.expense_sharing.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserService userService;

    public Group createGroup(String name, Long createdById, List<Long> memberIds) {
        User createdBy = userService.getById(createdById);

        if (groupRepository.existsByNameAndCreatedById(name, createdById)) {
        throw new RuntimeException(
                "Group with name '" + name + "' already exists for this user"
            );
        }
       

        // Validate all members BEFORE creating group
        List<User> members = memberIds.stream()
                .map(userService::getById)
                .toList();

        // Create group
        Group group = new Group();
        group.setName(name);
        group.setCreatedBy(createdBy);

        group = groupRepository.save(group);

        // Add members
        for (User user : members) {
            groupMemberRepository.save(
                    new GroupMember(null, group, user)
            );
        }

        return group;
    }
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void addMember(Long groupId, Long userId) {

    Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));

    User user = userService.getById(userId);

    // Prevent duplicates
    if (groupMemberRepository.existsByGroupIdAndUserId(groupId, userId)) {
        throw new RuntimeException("User already in group");
    }

    groupMemberRepository.save(
            new GroupMember(null, group, user)
    );
}

    public List<Group> getGroupsForUser(Long userId) {
        return groupMemberRepository.findByUserId(userId)
                .stream()
                .map(GroupMember::getGroup)
                .toList();
    }


}
