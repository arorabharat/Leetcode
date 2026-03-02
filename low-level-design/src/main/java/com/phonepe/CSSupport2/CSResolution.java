package com.phonepe.CSSupport2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

 // ======================== Data model =============================
enum IssueType {
    MUTUAL_FUND,
    TRANSACTION,
    INSURANCE,
    GOLD;
}

enum IssueStatus {
    OPEN,
    ASSIGNED,
    IN_PROGRESS,
    RESOLVED
}

class Issue {

    private final String id;
    private final String trxId;
    private final IssueType type;
    private final String subject;
    private final String desc;
    private final String customerEmail;
    private final IssueStatus status;
    private String resolution;
    private Optional<String> assignedAgentId;

    public Issue(String id, String trxId, IssueType type, String subject, String desc, String customerEmail) {
        this.id = id;
        this.trxId = trxId;
        this.type = type;
        this.subject = subject;
        this.desc = desc;
        this.customerEmail = customerEmail;
        this.status = IssueStatus.OPEN;
    }

    public Issue(String id, String trxId, IssueType type, String subject, String desc, String customerEmail, IssueStatus status, String resolution, Optional<String> assignedAgentId) {
        this.id = id;
        this.trxId = trxId;
        this.type = type;
        this.subject = subject;
        this.desc = desc;
        this.customerEmail = customerEmail;
        this.status = status;
        this.resolution = resolution;
        this.assignedAgentId = assignedAgentId;
    }

    public String getId() {
        return id;
    }

    public String getTrxId() {
        return trxId;
    }

    public IssueType getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public String getResolution() {
        return resolution;
    }

    public Optional<String> getAssignedAgentId() {
        return assignedAgentId;
    }
}

class Agent {

    private final String Id;
    private final String name;
    private final String email;
    private final Set<IssueType> expertiseList;


    public Agent(String id, String name, String email, Set<IssueType> expertiseList) {
        Id = id;
        this.name = name;
        this.email = email;
        this.expertiseList = expertiseList;
    }
}


enum AgentStatus {
    AVAILABLE,
    BUSY
}


class AgentWorkHistory {

}

interface CustomerSupportService {

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


class CustomerServiceImpl implements CustomerSupportService {

    private final Map<String, Agent> agentById = new HashMap<>();
    private final Map<String, Issue> issueById = new ConcurrentHashMap<>();
    private final UniqueIDProvider uniqueIDProvider = new UniqueIDProvider();
    private final Map<IssueType, Queue<Issue>> pendingIssuesByType = new HashMap<>();
    private final Map<IssueType, Queue<Agent>> availableAgentsByIssuesType = new HashMap<>();


    @Override
    public String createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        String id = "Issue="+uniqueIDProvider.getId();
        Issue issue = new Issue(id, transactionId, issueType, subject, description, email);
        issueById.put(id, issue);
        System.out.println("Logged a new issue : " + issue.getId());
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

//        Agent agent = new Agent(id, agentName, agentEmail, Collections.unmodifiableList(issueTypeList));
//        agentById.put(id, agent);
//        for(IssueType issueType : issueTypeList) {
//            availableAgentsByIssuesType.computeIfAbsent(issueType, k -> new LinkedList<>());
//            availableAgentsByIssuesType.get(issueType).add(agent);
//        }
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

    public static void main(String[] args) {

    }

}
