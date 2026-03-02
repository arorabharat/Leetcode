package com.phonepe.CSSupport2;

import java.util.*;
import java.util.function.Predicate;

enum IssueType {
    MUTUAL_FUND,
    TRANSACTION,
    INSURANCE,
    GOLD;
}

enum AgentStatus {
    AVAILABLE,
    BUSY
}

class Agent {
    String Id;
    String name;
    String email;
    String assignedIssue;
    List<IssueType> issueTypeList;


    public Agent(String id, String name, String email, List<IssueType> issueTypeList) {
        Id = id;
        this.name = name;
        this.email = email;
        this.issueTypeList = issueTypeList;
    }
}

enum IssueStatus {
    ASSIGNED,
    UNASSIGNED,
    PENDING,
    RESOLVED
}

class Issue {

    String id;
    String trxId;
    IssueType type;
    String subject;
    String desc;
    String email;
    IssueStatus status;

    public Issue(String id, String trxId, IssueType type, String subject, String desc, String email, IssueStatus status) {
        this.id = id;
        this.trxId = trxId;
        this.type = type;
        this.subject = subject;
        this.desc = desc;
        this.email = email;
        this.status = status;
    }
}

class AgentWorkHistory {

}


interface CSSupportService {

    String createIssue(String transactionId, IssueType issueType, String subject, String description, String email);

    String addAgent(String agentEmail, String agentName, List<IssueType> issueTypeList);

    // -> Issue can be assigned to the agents based on different strategies. For now, assign to any one of the free agents.
    Agent assignIssue(String issueId);

    // -> issues against the provided filter
    List<Issue> getIssues(Predicate<Issue> issueFilter);

    boolean updateIssue(String issueId, IssueStatus status, String resolution);

    boolean resolveIssue(String issueId, String resolution);

    // -> a list of issue which agents worked on
    AgentWorkHistory viewAgentsWorkHistory();
}

class UniqueIDProvider {
    public String getId() {
        return UUID.randomUUID().toString();
    }
}

class IssueFilterBuilder {

}

interface Validator {
    boolean isValid();
}

class EmailValidator implements Validator {

    String email;

    public EmailValidator(String email) {
        this.email = email;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}


class CSServiceImpl implements CSSupportService {

    private final Map<String, Agent> agentById = new HashMap<>();
    private final Map<String, Issue> issueById = new HashMap<>();
    private final UniqueIDProvider uniqueIDProvider = new UniqueIDProvider();
    private final Map<IssueType, Queue<Issue>> pendingIssuesByType = new HashMap<>();
    private final Map<IssueType, Queue<Agent>> availableAgentsByIssuesType = new HashMap<>();


    @Override
    public String createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {

        String id = uniqueIDProvider.getId();

        Issue issue = new Issue(id, transactionId, issueType, subject, description, email, IssueStatus.UNASSIGNED);
        issueById.put(id, issue);
        addIssueToQueue(issueType, issue);
        return id;
    }

    private void addIssueToQueue(IssueType issueType, Issue issue) {
        pendingIssuesByType.computeIfAbsent(issueType, k -> new LinkedList<>());
        pendingIssuesByType.get(issueType).add(issue);
    }

    @Override
    public String addAgent(String agentEmail, String agentName, List<IssueType> issueTypeList) {

        String id = uniqueIDProvider.getId();

        Agent agent = new Agent(id, agentName, agentEmail, Collections.unmodifiableList(issueTypeList));
        agentById.put(id, agent);
        for(IssueType issueType : issueTypeList) {
            availableAgentsByIssuesType.computeIfAbsent(issueType, k -> new LinkedList<>());
            availableAgentsByIssuesType.get(issueType).add(agent);
        }
        return id;
    }

    @Override
    public Agent assignIssue(String issueId) {

        return null;
    }

    @Override
    public List<Issue> getIssues(Predicate<Issue> issueFilter) {
        return issueById.values().stream().filter(issueFilter).toList();
    }

    @Override
    public boolean updateIssue(String issueId, IssueStatus status, String resolution) {

        return false;
    }

    @Override
    public boolean resolveIssue(String issueId, String resolution) {
        return false;
    }

    @Override
    public AgentWorkHistory viewAgentsWorkHistory() {
        return null;
    }
}

public class CSResolution {

}
