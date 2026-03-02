package com.phonepe.CSSupport2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private IssueStatus status;
    private String resolution;
    private String assignedAgentId;

    public Issue(String id, String trxId, IssueType type, String subject, String desc, String customerEmail) {
        this.id = id;
        this.trxId = trxId;
        this.type = type;
        this.subject = subject;
        this.desc = desc;
        this.customerEmail = customerEmail;
        this.status = IssueStatus.OPEN;
    }

    public Issue(String id, String trxId, IssueType type, String subject, String desc, String customerEmail, IssueStatus status, String resolution, String assignedAgentId) {
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

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setAssignedAgentId(String assignedAgentId) {
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
        return assignedAgentId == null ? Optional.empty() : Optional.of(assignedAgentId);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "resolution='" + resolution + '\'' +
                ", desc='" + desc + '\'' +
                ", subject='" + subject + '\'' +
                ", type=" + type +
                ", assignedAgentId='" + assignedAgentId + '\'' +
                '}';
    }
}

class Agent {

    private final String Id;
    private final String name;
    private final String email;
    private final Set<IssueType> expertiseList;
    private String assignedIssue;
    private List<String> agentWorkHistory;


    public Agent(String id, String name, String email, List<IssueType> expertiseList) {
        Id = id;
        this.name = name;
        this.email = email;
        this.expertiseList = Set.copyOf(expertiseList);
        this.agentWorkHistory = new ArrayList<>();
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAssignedIssue() {
        return assignedIssue;
    }

    public void setAssignedIssue(String assignedIssue) {
        this.assignedIssue = assignedIssue;
        agentWorkHistory.add(assignedIssue);
    }

    public boolean canHandle(IssueType issueType) {
        return this.expertiseList.contains(issueType);
    }

    public boolean isAvailable() {
        return this.assignedIssue == null;
    }

    public void freeAgent() {
        this.assignedIssue = null;
    }

    public List<String> getAgentWorkHistory() {
        return Collections.unmodifiableList(agentWorkHistory);
    }
}

interface AssignmentStrategy {
    Optional<Agent> findAgent(IssueType issueType, List<Agent> agentList);
}

class FreeAgentAssignmentStrategy implements AssignmentStrategy {
    @Override
    public Optional<Agent> findAgent(IssueType issueType, List<Agent> agentList) {
        return agentList.stream().filter(agent -> agent.canHandle(issueType) && agent.isAvailable()).findFirst();
    }
}

interface CustomerSupportService {

    String createIssue(String transactionId, IssueType issueType, String subject, String description, String email);

    String addAgent(String agentEmail, String agentName, List<IssueType> issueTypeList);

    Agent assignIssue(String issueId);

    List<Issue> getIssues(IssueFilter issueFilter);

    boolean updateIssue(String issueId, IssueStatus status, String resolution);

    boolean resolveIssue(String issueId, String resolution);

    void viewAgentsWorkHistory();
}

class UniqueIDProvider {
    public String getId() {
        return UUID.randomUUID().toString();
    }
}

class IssueFilter {

    private String email;
    private String issueId;
    private IssueType issueType;

    public static IssueFilter byEmail(String email) {
        IssueFilter issueFilter = new IssueFilter();
        issueFilter.email = email;
        return issueFilter;
    }

    public static IssueFilter byIssueId(String issueId) {
        IssueFilter issueFilter = new IssueFilter();
        issueFilter.issueId = issueId;
        return issueFilter;
    }

    public static IssueFilter byIssueType(IssueType issueType) {
        IssueFilter issueFilter = new IssueFilter();
        issueFilter.issueType = issueType;
        return issueFilter;
    }

    public String getEmail() {
        return this.email;
    }

    public String getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}

class CustomerSupportServiceImpl implements CustomerSupportService {

    private final Map<String, Agent> agentById = new HashMap<>();
    private final Map<String, Issue> issueById = new ConcurrentHashMap<>();
    private final AssignmentStrategy assignmentStrategy;
    private final UniqueIDProvider uniqueIDProvider = new UniqueIDProvider();

    public CustomerSupportServiceImpl(AssignmentStrategy assignmentStrategy) {
        this.assignmentStrategy = assignmentStrategy;
    }

    @Override
    public String createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        String id = "Issue=" + uniqueIDProvider.getId();
        Issue issue = new Issue(id, transactionId, issueType, subject, description, email);
        issueById.put(id, issue);
        System.out.println("Logged a new issue : " + issue.getDesc());
        return id;
    }

    @Override
    public String addAgent(String agentEmail, String agentName, List<IssueType> issueTypeList) {
        String id = "Agent-" + uniqueIDProvider.getId();
        Agent agent = new Agent(id, agentName, agentEmail, issueTypeList);
        agentById.put(id, agent);
        System.out.println("Added a new agent : " + agent.getName());
        return id;
    }

    @Override
    public Agent assignIssue(String issueId) {
        Issue issue = issueById.get(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue with Id : " + issueId + " not  found");
        }
        Optional<Agent> agent = assignmentStrategy.findAgent(issue.getType(), new ArrayList<>(agentById.values()));
        if(agent.isPresent()) {
            System.out.println("Issue : " + issue.getDesc() + " is assigned to agent :" + agent.get().getName());
            agent.get().setAssignedIssue(issue.getId());
            issue.setAssignedAgentId(agent.get().getId());
        } else {
            // review later
            System.out.println("Unable to assign : " + issue.getDesc() + " to any agent");
        }
        return null;
    }

    @Override
    public List<Issue> getIssues(IssueFilter issueFilter) {
        List<Issue> filterResults = new ArrayList<>();
        if(issueFilter.getIssueId() != null) {
            Issue issue = issueById.get(issueFilter.getIssueId());
            if(issue != null) {
                filterResults.add(issue);
            }
            return filterResults;
        } else if(issueFilter.getIssueType() != null) {
            return issueById.values().stream().filter(issue -> issue.getType().equals(issueFilter.getIssueType())).toList();
        } else {
            // other criteria
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateIssue(String issueId, IssueStatus status, String resolution) {
        Issue issue = issueById.get(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue with Id : " + issueId + " not  found");
        }
        issue.setStatus(status);
        if(resolution != null) {
            issue.setResolution(resolution);
            System.out.println("Resolved issue : "+ issue.getId());
        }
        return false;
    }

    @Override
    public boolean resolveIssue(String issueId, String resolution) {
        Issue issue = issueById.get(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue with Id : " + issueId + " not  found");
        }
        issue.setStatus(IssueStatus.RESOLVED);
        issue.setResolution(resolution);
        Optional<String> assignedAgentId = issue.getAssignedAgentId();
        if(assignedAgentId.isEmpty()) {
            throw new RuntimeException("Invalid Issue state with Id : " + issueId);
        }
        Agent agent = agentById.get(assignedAgentId.get());
        agent.freeAgent();
        return false;
    }

    @Override
    public void viewAgentsWorkHistory() {
        for (Agent agent : agentById.values()) {
            System.out.println( agent.getName() + "  -> " + agent.getAgentWorkHistory());
        }
    }
}

public class CSResolution {

    public static void main(String[] args) {
        AssignmentStrategy assignmentStrategy = new FreeAgentAssignmentStrategy();
        CustomerSupportService customerSupportService = new CustomerSupportServiceImpl(assignmentStrategy);
        String issue1 = customerSupportService.createIssue("T1", IssueType.GOLD, "payment failed issue", "gold issue", "zyx@gmail.com");
        String issue2 = customerSupportService.createIssue("T2", IssueType.MUTUAL_FUND, "payment failed issue", "mutual fund issue", "zyx@gmail.com");
        customerSupportService.addAgent("a1@gmail.com", "a1",List.of(IssueType.GOLD, IssueType.TRANSACTION));
        customerSupportService.addAgent("a1@gmail.com", "a2",List.of(IssueType.MUTUAL_FUND));
        customerSupportService.assignIssue(issue1);
        customerSupportService.assignIssue(issue2);
        IssueFilter issueFilter = IssueFilter.byIssueId(issue1);
        List<Issue> issueList = customerSupportService.getIssues(issueFilter);
        System.out.println(issueList);
        customerSupportService.updateIssue(issue1, IssueStatus.IN_PROGRESS, "work in progress");
        issueList = customerSupportService.getIssues(issueFilter);
        for (Issue issue : issueList) {
            System.out.println(issue);
        }
        customerSupportService.resolveIssue(issue1,"Resolved");
        issueList = customerSupportService.getIssues(issueFilter);
        for (Issue issue : issueList) {
            System.out.println(issue);
        }
        customerSupportService.viewAgentsWorkHistory();
    }

}
