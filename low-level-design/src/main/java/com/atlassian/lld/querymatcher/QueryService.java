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

record Criteria(String key, String value) {
}

record GenericQuery(List<Criteria> criteriaList) {
}

interface QueryMatcherService {
    List<WorkItem> filter(Query query, List<WorkItem> workItem);

    List<WorkItem> filter(GenericQuery query, List<WorkItem> workItem);
}

interface QueryValidator {
    void validate(Query query);
}


interface GenericQueryValidator {
    void validate(GenericQuery query);
}

class MinOneFieldQueryValidatorImpl implements QueryValidator {
    @Override
    public void validate(Query query) {
        if (query == null || (query.key().isEmpty() && query.project().isEmpty() && query.workItemType().isEmpty())) {
            throw new RuntimeException("Illegal Query type");
        }
    }
}

class MinOneFieldGenericQueryValidatorImpl implements GenericQueryValidator {
    @Override
    public void validate(GenericQuery query) {
        if (query == null || query.criteriaList().isEmpty()) {
            throw new RuntimeException("Illegal Query type");
        }
    }
}

interface QueryMatchingStrategy {
    boolean isMatch(Query query, WorkItem workItems);
}

interface GenericQueryMatchingStrategy {
    boolean isMatch(GenericQuery query, WorkItem workItems);
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

class WorkItemSchema {
    public static final String KEY = "key";
    public static final String PROJECT = "project";
    public static final String WORK_ITEM_TYPE = "workItemType";

    private WorkItemSchema() {
    }
}

class ExactFieldGenericQueryMatchStrategy implements GenericQueryMatchingStrategy {

    @Override
    public boolean isMatch(GenericQuery query, WorkItem workItem) {
        for (Criteria criteria : query.criteriaList()) {
            if (criteria.key().equals(WorkItemSchema.KEY)) {
                if (!criteria.value().equals(workItem.key())) {
                    return false;
                }
            }
            if (criteria.key().equals(WorkItemSchema.PROJECT)) {
                if (!criteria.value().equals(workItem.project())) {
                    return false;
                }
            }
            if (criteria.key().equals(WorkItemSchema.WORK_ITEM_TYPE)) {
                if (!criteria.value().equals(workItem.workItemType().toString())) {
                    return false;
                }
            }
        }
        return true;
    }
}


class QueryMatcherServiceImpl implements QueryMatcherService {

    private final QueryValidator queryValidator;
    private final GenericQueryValidator genericQueryValidator;
    private final QueryMatchingStrategy queryMatchingStrategy;
    private final GenericQueryMatchingStrategy genericQueryMatchingStrategy;

    public QueryMatcherServiceImpl(QueryValidator queryValidator,
                                   GenericQueryValidator genericQueryValidator,
                                   QueryMatchingStrategy queryMatchingStrategy,
                                   GenericQueryMatchingStrategy genericQueryMatchingStrategy) {
        this.queryValidator = queryValidator;
        this.genericQueryValidator = genericQueryValidator;
        this.queryMatchingStrategy = queryMatchingStrategy;
        this.genericQueryMatchingStrategy = genericQueryMatchingStrategy;
    }

    @Override
    public List<WorkItem> filter(Query query, List<WorkItem> workItems) {
        queryValidator.validate(query);
        return new ArrayList<>(workItems.stream().filter(workItem -> queryMatchingStrategy.isMatch(query, workItem)).toList());
    }

    @Override
    public List<WorkItem> filter(GenericQuery query, List<WorkItem> workItems) {
        genericQueryValidator.validate(query);
        return new ArrayList<>(workItems.stream().filter(workItem -> genericQueryMatchingStrategy.isMatch(query, workItem)).toList());
    }
}

public class QueryService {

    public static void main(String[] args) {
        QueryValidator queryValidator = new MinOneFieldQueryValidatorImpl();
        GenericQueryValidator genericQueryValidator = new MinOneFieldGenericQueryValidatorImpl();
        QueryMatchingStrategy queryMatchingStrategy = new ExactFieldQueryMatchStrategy();
        GenericQueryMatchingStrategy genericQueryMatchingStrategy = new ExactFieldGenericQueryMatchStrategy();
        QueryMatcherService queryMatcherService = new QueryMatcherServiceImpl(queryValidator, genericQueryValidator, queryMatchingStrategy, genericQueryMatchingStrategy);
        Query taskQuery = new Query(Optional.empty(), Optional.empty(), Optional.of(WorkItemType.TASK));
        GenericQuery taskGenericQuery = new GenericQuery(List.of( new Criteria(WorkItemSchema.WORK_ITEM_TYPE, WorkItemType.TASK.toString())));
        WorkItem w1 = new WorkItem("k1", "p1", WorkItemType.STORY);
        WorkItem w2 = new WorkItem("k2", "p1", WorkItemType.BUG);
        WorkItem w3 = new WorkItem("k3", "p1", WorkItemType.TASK);
        WorkItem w4 = new WorkItem("k4", "p2", WorkItemType.TASK);
        List<WorkItem> filteredResults = queryMatcherService.filter(taskQuery, List.of(w1, w2, w3, w4));
        List<WorkItem> filteredResultsGeneric = queryMatcherService.filter(taskGenericQuery, List.of(w1, w2, w3, w4));
        System.out.println(filteredResults);
        System.out.println(filteredResultsGeneric);
        Query taskQuery2 = new Query(Optional.empty(), Optional.of("p1"), Optional.of(WorkItemType.TASK));
        List<WorkItem> filteredResults2 = queryMatcherService.filter(taskQuery2, List.of(w1, w2, w3, w4));
        System.out.println(filteredResults2);

    }
}
