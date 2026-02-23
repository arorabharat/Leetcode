package com.phonepe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

enum TransactionState {
    FAILED,
    PENDING,
    SUCCESS
}

enum IssueType {
    MUTUAL_FUND,
    GOLD,
    PAYMENT,
    INSURANCE
}

enum TicketStatus {
    ASSIGNED,
    UNASSIGNED,
    RESOLVED
}

class Ticket {
    private final String id;
    private final TicketStatus ticketStatus;
    private final String transactionId;
    private final String title;
    private final String desc;
    private final String email;
    private final IssueType issueType;

    public Ticket(String transactionId, String title, String desc, String email, IssueType issueType) {
        this.id = UUID.randomUUID().toString();
        this.ticketStatus = TicketStatus.UNASSIGNED;
        this.transactionId = transactionId;
        this.title = title;
        this.desc = desc;
        this.email = email;
        this.issueType = issueType;
    }

    public String getId() {
        return id;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getEmail() {
        return email;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}

record Transaction(String id, String date, String amount) {
}


class Agent {

    private final String id;
    private final String name;
    private final Set<IssueType> expertiseList;

    public Agent(String name, Set<IssueType> expertiseList) {
        this.id = UUID.randomUUID().toString();
        ;
        this.name = name;
        this.expertiseList = expertiseList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<IssueType> getExpertiseList() {
        return Set.copyOf(expertiseList);
    }
}

class NoSuchTransactionException extends RuntimeException {

    public NoSuchTransactionException(String message) {
        super(message);
    }
}

class DuplicateTransactionIdException extends RuntimeException {

    public DuplicateTransactionIdException(String message) {
        super(message);
    }
}


class DuplicateAgentIdException extends RuntimeException {

    public DuplicateAgentIdException(String message) {
        super(message);
    }
}

class TransactionRepository {

    private final Map<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    public Transaction findTransactionOrThrow(String transactionId) {
        if (!transactionMap.containsKey(transactionId)) {
            throw new NoSuchTransactionException("Invalid transactionId " + transactionId);
        }
        return transactionMap.get(transactionId);
    }
}

class TicketRepository {

    private final Map<String, Ticket> id2Ticket = new ConcurrentHashMap<>();
    private final Map<String, List<Ticket>> email2Tickets = new ConcurrentHashMap<>();

    public Ticket findTransactionOrThrow(String ticketId) {
        if (!id2Ticket.containsKey(ticketId)) {
            throw new NoSuchTransactionException("Invalid ticketId " + ticketId);
        }
        return id2Ticket.get(ticketId);
    }

    public boolean containsTicket(String ticketId) {
        return id2Ticket.containsKey(ticketId);
    }

    public List<Ticket> listTicketsForEmail(String email) {
        return new ArrayList<>(email2Tickets.getOrDefault(email, new ArrayList<>()));
    }

    public Ticket save(Ticket ticket) {
        String ticketId = ticket.getId();
        if (!id2Ticket.containsKey(ticketId)) {
            throw new DuplicateTransactionIdException("Invalid ticketId " + ticketId);
        }
        return id2Ticket.put(ticketId, ticket);
    }
}

class AgentRepository {

//    private final Map<String, com.phonepe.CSSupport2.Agent> id2Agent = new ConcurrentHashMap<>();
//
//    public com.phonepe.CSSupport2.Agent save(com.phonepe.CSSupport2.Agent agent) {
//        String agentId = agent.getId();
//        if (!id2Agent.containsKey(agentId)) {
//            throw new DuplicateAgentIdException("Invalid agentId " + agentId);
//        }
//        return id2Agent.put(agentId, agent);
//    }
}


class CustomerSupportService {

    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;
    private final AgentRepository agentRepository;

    public CustomerSupportService(TransactionRepository transactionRepository, TicketRepository ticketRepository, AgentRepository agentRepository) {
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
        this.agentRepository = agentRepository;
    }

    public void createIssue(String transactionId,
                            String title,
                            String desc,
                            String email,
                            IssueType issueType) {
        Objects.requireNonNull(transactionId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(desc);
        Objects.requireNonNull(email);
        Objects.requireNonNull(issueType);
        transactionRepository.findTransactionOrThrow(transactionId);
        Ticket ticket = new Ticket(transactionId, title, desc, email, issueType);
        ticketRepository.save(ticket);
    }

    public Optional<Ticket> findByTicketId(String ticketId) {
        Objects.requireNonNull(ticketId);
        if (ticketRepository.containsTicket(ticketId)) {
            return Optional.of(ticketRepository.findTransactionOrThrow(ticketId));
        }
        return Optional.empty();
    }

    public List<Ticket> findAllByEmail(String emailId) {
        Objects.requireNonNull(emailId);
        return ticketRepository.listTicketsForEmail(emailId);
    }

    public void addAgent(String name, List<IssueType> issueTypeList, String accessToken) {
        if (!"ADMIN".equals(accessToken)) {
            throw new RuntimeException("Don't have rights to add agent");
        }
//        agentRepository.save(new com.phonepe.CSSupport2.Agent(name, new HashSet<>(issueTypeList)));
    }
}


public class CustomerSupportResolution {


    public static void main(String[] args) {

    }
}
