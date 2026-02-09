package com.atlassian.lld.querymatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

enum WorkItemType {
    BUG,
    TASK,
    STORY
}

record WorkItem(String key, String project, WorkItemType workItemType) {
}

record Query(Optional<String> key, Optional<String> project, Optional<WorkItemType> workItemType) {
}

interface QueryMatcherService {
    List<WorkItem> filter(Query query, List<WorkItem> workItem);
}

interface QueryValidator {
    void validate(Query query);
}

class MinOneFieldQueryValidatorImpl implements QueryValidator {
    @Override
    public void validate(Query query) {
        if (query == null || (query.key().isEmpty() && query.project().isEmpty() && query.workItemType().isEmpty())) {
            throw new RuntimeException("Illegal Query type");
        }
    }
}

interface QueryMatchingStrategy {
    boolean isMatch(Query query, WorkItem workItem);
}

class ExactFieldQueryMatchStrategy implements QueryMatchingStrategy {

    @Override
    public boolean isMatch(Query query, WorkItem workItem) {
        if (query.key().isPresent() && !query.key().get().equals(workItem.key())) {
            return false;
        }
        if (query.project().isPresent() && !query.project().get().equals(workItem.project())) {
            return false;
        }
        return query.workItemType().isEmpty() || query.workItemType().get().equals(workItem.workItemType());
    }
}


class QueryMatcherServiceImpl implements QueryMatcherService {

    private final QueryValidator queryValidator;
    private final QueryMatchingStrategy queryMatchingStrategy;

    public QueryMatcherServiceImpl(QueryValidator queryValidator, QueryMatchingStrategy queryMatchingStrategy) {
        this.queryValidator = queryValidator;
        this.queryMatchingStrategy = queryMatchingStrategy;
    }

    @Override
    public List<WorkItem> filter(Query query, List<WorkItem> workItems) {
        queryValidator.validate(query);
        return new ArrayList<>(workItems.stream().filter(workItem -> queryMatchingStrategy.isMatch(query, workItem)).toList());
    }
}

public class QueryService {

    public static void main(String[] args) {
        QueryValidator queryValidator = new MinOneFieldQueryValidatorImpl();
        QueryMatchingStrategy queryMatchingStrategy = new ExactFieldQueryMatchStrategy();
        QueryMatcherService queryMatcherService = new QueryMatcherServiceImpl(queryValidator, queryMatchingStrategy);
        Query taskQuery = new Query(Optional.empty(), Optional.empty(), Optional.of(WorkItemType.TASK));
        WorkItem w1 = new WorkItem("k1", "p1", WorkItemType.STORY);
        WorkItem w2 = new WorkItem("k2", "p1", WorkItemType.BUG);
        WorkItem w3 = new WorkItem("k3", "p1", WorkItemType.TASK);
        WorkItem w4 = new WorkItem("k4", "p2", WorkItemType.TASK);
        List<WorkItem> filteredResults = queryMatcherService.filter(taskQuery, List.of(w1, w2, w3, w4));
        System.out.println(filteredResults);
        Query taskQuery2 = new Query(Optional.empty(), Optional.of("p1"), Optional.of(WorkItemType.TASK));
        List<WorkItem> filteredResults2 = queryMatcherService.filter(taskQuery2, List.of(w1, w2, w3, w4));
        System.out.println(filteredResults2);

    }
}
