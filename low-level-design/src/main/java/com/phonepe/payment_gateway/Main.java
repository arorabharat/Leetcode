package com.phonepe.payment_gateway;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// --- 1. DOMAIN MODELS & ENUMS ---
enum PaymentInstrumentType { CREDIT_CARD, UPI }
enum ChargeStatus { SUCCESS, FAILED, PENDING, INITIATED }
enum Client { FLIPKART, SWIGGY, AMAZON }

interface PaymentInstrument {
    String getId();
    PaymentInstrumentType getPaymentInstrumentType();
}

class UPI implements PaymentInstrument {
    private final String id = UUID.randomUUID().toString();
    private final String vpa;
    public UPI(String vpa) { this.vpa = vpa; }
    public String getVpa() { return vpa; }
    @Override public String getId() { return id; }
    @Override public PaymentInstrumentType getPaymentInstrumentType() { return PaymentInstrumentType.UPI; }
}

class Charge {
    private final Client client;
    private final String chargeId = UUID.randomUUID().toString();
    private final BigDecimal amount;
    private final PaymentInstrument instrument;
    private ChargeStatus status = ChargeStatus.INITIATED;

    public Charge(Client client, BigDecimal amount, PaymentInstrument instrument) {
        this.client = client;
        this.amount = amount;
        this.instrument = instrument;
    }

    public Client getClient() { return client; }
    public String getChargeId() { return chargeId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentInstrument getInstrument() { return instrument; }
    public ChargeStatus getStatus() { return status; }
    public void setStatus(ChargeStatus status) { this.status = status; }
}

// --- 2. BANK-SPECIFIC SCHEMAS (External Objects) ---
// These prove you can handle different object types per bank
class HDFCObject {
    public double val;
    public String txnRef;
    public HDFCObject(double v, String r) { this.val = v; this.txnRef = r; }
}

class ICICIObject {
    public String amountStr;
    public String vpaHandle;
    public ICICIObject(String a, String v) { this.amountStr = a; this.vpaHandle = v; }
}

// --- 3. ADAPTERS (Translation Layer) ---
interface BankAdapter {
    void process(Charge internalCharge);
}

class HDFCAdapter implements BankAdapter {
    @Override
    public void process(Charge charge) {
        // Translation logic: Internal Charge -> HDFCObject
        HDFCObject hdfcReq = new HDFCObject(charge.getAmount().doubleValue(), charge.getChargeId());
        System.out.println("[ADAPTER] Transformed to HDFCObject. Calling HDFC API...");
        charge.setStatus(ChargeStatus.SUCCESS);
    }
}

class ICICIAdapter implements BankAdapter {
    @Override
    public void process(Charge charge) {
        // Translation logic: Internal Charge -> ICICIObject
        String vpa = (charge.getInstrument() instanceof UPI) ? ((UPI) charge.getInstrument()).getVpa() : "NA";
        ICICIObject iciciReq = new ICICIObject(charge.getAmount().toPlainString(), vpa);
        System.out.println("[ADAPTER] Transformed to ICICIObject. Calling ICICI API...");
        charge.setStatus(ChargeStatus.SUCCESS);
    }
}

// --- 4. ROUTING STRATEGIES ---
interface RoutingStrategy {
    BankAdapter getAdapter(Charge charge);
}

// Percentage-based/Weighted Strategy
class WeightedStrategy implements RoutingStrategy {
    private final List<BankAdapter> pool = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public void addBank(BankAdapter adapter, int weight) {
        for (int i = 0; i < weight; i++) pool.add(adapter);
    }

    @Override
    public BankAdapter getAdapter(Charge charge) {
        if (pool.isEmpty()) return null;
        return pool.get(counter.getAndIncrement() % pool.size());
    }
}

// Rule-based Strategy
class FlipkartStrategy implements RoutingStrategy {
    private final BankAdapter hdfc = new HDFCAdapter();
    private final BankAdapter icici = new ICICIAdapter();

    @Override
    public BankAdapter getAdapter(Charge charge) {
        // Rule: All UPI to ICICI, everything else to HDFC
        return (charge.getInstrument().getPaymentInstrumentType() == PaymentInstrumentType.UPI) ? icici : hdfc;
    }
}

// --- 5. CORE SERVICE ---
class PaymentGateway {
    private final Map<Client, RoutingStrategy> clientRules = new ConcurrentHashMap<>();

    public void onboardClient(Client client, RoutingStrategy strategy) {
        clientRules.put(client, strategy);
    }

    public void execute(Charge charge) {
        RoutingStrategy strategy = clientRules.get(charge.getClient());
        if (strategy == null) {
            System.out.println("No strategy found for client: " + charge.getClient());
            return;
        }

        BankAdapter adapter = strategy.getAdapter(charge);
        adapter.process(charge);
        System.out.println("Final Transaction Status: " + charge.getStatus() + " for ID: " + charge.getChargeId() + "\n");
    }
}

// --- 6. DRIVER CLASS ---
public class Main {
    public static void main(String[] args) {
        PaymentGateway pg = new PaymentGateway();

        // 1. Setup Flipkart (Rule-based)
        pg.onboardClient(Client.FLIPKART, new FlipkartStrategy());

        // 2. Setup Swiggy (Weighted: 2 parts HDFC, 1 part ICICI)
        WeightedStrategy swiggyWeighted = new WeightedStrategy();
        swiggyWeighted.addBank(new HDFCAdapter(), 2);
        swiggyWeighted.addBank(new ICICIAdapter(), 1);
        pg.onboardClient(Client.SWIGGY, swiggyWeighted);

        // 3. Execute Flipkart UPI
        Charge c1 = new Charge(Client.FLIPKART, new BigDecimal("499.00"), new UPI("customer@ybl"));
        pg.execute(c1);

        // 4. Execute Swiggy Weighted Load
        System.out.println("--- Swiggy Load Balance Test ---");
        for (int i = 0; i < 3; i++) {
            Charge c = new Charge(Client.SWIGGY, new BigDecimal("150.00"), new UPI("swiggy@ybl"));
            pg.execute(c);
        }
    }
}