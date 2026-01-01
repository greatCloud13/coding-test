package com.seowon.coding.domain.model;


import com.seowon.coding.util.ListFun;
import lombok.Builder;

import java.util.HashMap;
import java.util.List;

class PermissionChecker {

    /**
     * TODO #7: 코드를 최적화하세요
     * 테스트 코드`PermissionCheckerTest`를 활용하시면 리펙토링에 도움이 됩니다.
     */
    public static boolean hasPermission(
            String userId,
            String targetResource,
            String targetAction,
            List<User> users,
            List<UserGroup> groups,
            List<Policy> policies
    ) {

        // 1. 탐색 전  HashMap으로 변환 O(1);
        HashMap<String, User> userHashMap = ListFun.toHashMap(users, (User user) -> {return user.id;});
        HashMap<String, UserGroup> userGroupHashMap = ListFun.toHashMap(groups, (UserGroup userGroup) -> {return userGroup.id;});
        HashMap<String, Policy> policyHashMap = ListFun.toHashMap(policies, (Policy policy) ->{return policy.id;});

        // O(g*p*s)
        User user = userHashMap.get(userId);
        if (user == null){
            return false;
        }
        for(String userGroupId : user.groupIds){                    //O(g)
            UserGroup userGroup = userGroupHashMap.get(userGroupId);
            if(userGroup == null) continue;
            for(String policyId : userGroup.policyIds){             //O(p)
                Policy policy = policyHashMap.get(policyId);
                if(policy == null) continue;
                for(Statement statement : policy.statements){       // O(s)
                    if (statement.actions.contains(targetAction) &&
                            statement.resources.contains(targetResource)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class User {
    String id;
    List<String> groupIds;

    public User(String id, List<String> groupIds) {
        this.id = id;
        this.groupIds = groupIds;
    }
}

class UserGroup {
    String id;
    List<String> policyIds;

    public UserGroup(String id, List<String> policyIds) {
        this.id = id;
        this.policyIds = policyIds;
    }
}

class Policy {
    String id;
    List<Statement> statements;

    public Policy(String id, List<Statement> statements) {
        this.id = id;
        this.statements = statements;
    }
}

class Statement {
    List<String> actions;
    List<String> resources;

    @Builder
    public Statement(List<String> actions, List<String> resources) {
        this.actions = actions;
        this.resources = resources;
    }
}