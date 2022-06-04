package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupMemberPageResponse;
import com.databasir.core.domain.group.data.GroupPageResponse;
import com.databasir.core.domain.group.data.GroupResponse;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.value.GroupMemberDetailPojo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupResponseConverter {

    default List<GroupPageResponse> toResponse(List<Group> pojos,
                                               Map<Integer, List<String>> groupOwnerGroupByGroupId,
                                               Map<Integer, Integer> projectCountMapByGroupId) {
        return pojos.stream()
                .map(group -> {
                    Integer groupId = group.getId();
                    List<String> owners = groupOwnerGroupByGroupId.getOrDefault(groupId, new ArrayList<>());
                    Integer projectCount = projectCountMapByGroupId.getOrDefault(groupId, 0);
                    return toResponse(group, owners, projectCount);
                })
                .collect(Collectors.toList());
    }

    GroupPageResponse toResponse(Group group,
                                 List<String> groupOwnerNames,
                                 Integer projectCount);

    GroupResponse toResponse(Group group, List<User> groupOwners);

    GroupMemberPageResponse toResponse(GroupMemberDetailPojo data);
}
