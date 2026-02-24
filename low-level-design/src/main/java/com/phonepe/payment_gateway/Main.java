package com.phonepe.payment_gateway;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// --- 1. Models and Enums ---
enum PaymentInstrumentType { CREDIT_CARD, UPI }
enum ChargeStatus { SUCCESS, FAILED, PENDING, INITIATED }
enum Client { FLIPKART, SWIGGY, AMAZON }

interface PaymentInstrument {
    String getId();
    PaymentInstrumentType getPaymentInstrumentType();
}

class UPI implements PaymentInstrument {
    private final String id;
    private final String upiHandle;

    public UPI(String upiHandle) {
        this.id = UUID.randomUUID().toString();
        this.upiHandle = upiHandle;
    }

    @Override
    public String getId() { return this.id; }

    @Override
    public PaymentInstrumentType getPaymentInstrumentType() {
        return PaymentInstrumentType.UPI; // FIX: Changed from CREDIT_CARD
    }
}

// Keeping Charge simple for brevity (BankCharge is identical but acts as the external DTO)
class Charge {
    private final Client client;
    private final String chargeId;
    private final BigDecimal amount;
    private final PaymentInstrument paymentInstrument;
    private ChargeStatus chargeStatus;

    public Charge(Client client, BigDecimal amount, PaymentInstrument paymentInstrument) {
        this.client = client;
        this.chargeId = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentInstrument = paymentInstrument;
        this.chargeStatus = ChargeStatus.INITIATED;
    }

    public Client getClient() { return client; }
    public String getChargeId() { return chargeId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentInstrument getPaymentInstrument() { return paymentInstrument; }
    public ChargeStatus getChargeStatus() { return chargeStatus; }
    public void setChargeStatus(ChargeStatus chargeStatus) { this.chargeStatus = chargeStatus; }
}

class BankCharge {
    private ChargeStatus chargeStatus;
    // ... same fields as Charge ...
    public BankCharge(Charge charge) {
        this.chargeStatus = ChargeStatus.INITIATED;
    }
    public ChargeStatus getChargeStatus() { return chargeStatus; }
    public void setChargeStatus(ChargeStatus chargeStatus) { this.chargeStatus = chargeStatus; }
}

// --- 2. Bank Adapters ---
interface BankChargeAdapter {
    void processCharge(BankCharge bankCharge);
}

class HDFCBankAdapter implements BankChargeAdapter {
    @Override
    public void processCharge(BankCharge bankCharge) {
        System.out.println("Routing via HDFC Bank...");
        bankCharge.setChargeStatus(ChargeStatus.SUCCESS);
    }
}

class ICICIBankAdapter implements BankChargeAdapter {
    @Override
    public void processCharge(BankCharge bankCharge) {
        System.out.println("Routing via ICICI Bank...");
        bankCharge.setChargeStatus(ChargeStatus.SUCCESS);
    }
}

// --- 3. The Strategies ---
interface ChargeRoutingStrategy {
    BankChargeAdapter getChargeAdapter(Charge charge);
}

// FIX: Actually implemented the routing logic
class FlipkartRoutingStrategy implements ChargeRoutingStrategy {
    private final BankChargeAdapter hdfcAdapter = new HDFCBankAdapter();
    private final BankChargeAdapter iciciAdapter = new ICICIBankAdapter();

    @Override
    public BankChargeAdapter getChargeAdapter(Charge charge) {
        // Example Rule: Flipkart routes all UPI to ICICI, and everything else to HDFC
        if (charge.getPaymentInstrument().getPaymentInstrumentType() == PaymentInstrumentType.UPI) {
            return iciciAdapter;
        }
        return hdfcAdapter;
    }
}

// --- 4. The Core Processor ---
class ChargeRoutingStrategyResolver {
    // FIX: Made thread-safe for the interview
    private final Map<Client, ChargeRoutingStrategy> strategyMap = new ConcurrentHashMap<>();

    public void setChargeRoutingStrategy(Client client, ChargeRoutingStrategy strategy) {
        strategyMap.put(client, strategy);
    }

    public ChargeRoutingStrategy getChargeRoutingStrategy(Charge charge) {
        return strategyMap.get(charge.getClient());
    }
}

class PaymentProcessor {
    private final ChargeRoutingStrategyResolver resolver = new ChargeRoutingStrategyResolver();

    public void setRoutingStrategyForClient(Client client, ChargeRoutingStrategy strategy) {
        resolver.setChargeRoutingStrategy(client, strategy); // FIX: Delegated to resolver
    }

    public void processCharge(Charge charge) {
        ChargeRoutingStrategy strategy = resolver.getChargeRoutingStrategy(charge);

        // FIX: Handled the Null scenario gracefully
        if (strategy == null) {
            System.out.println("Failed: No routing strategy configured for " + charge.getClient());
            charge.setChargeStatus(ChargeStatus.FAILED);
            return;
        }

        BankChargeAdapter adapter = strategy.getChargeAdapter(charge);
        BankCharge bankCharge = new BankCharge(charge);

        // Process the payment
        adapter.processCharge(bankCharge);

        // Sync status back to our internal system
        charge.setChargeStatus(bankCharge.getChargeStatus());
        System.out.println("Charge " + charge.getChargeId() + " final status: " + charge.getChargeStatus() + "\n");
    }
}

// --- 5. The Driver ---
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Payment Gateway...");

        PaymentProcessor processor = new PaymentProcessor();

        // 1. Onboard Flipkart with their specific routing rules
        processor.setRoutingStrategyForClient(Client.FLIPKART, new FlipkartRoutingStrategy());

        // 2. Create some dummy payment instruments
        PaymentInstrument upiApp = new UPI("john@ybl");

        // 3. Create a transaction for Flipkart using UPI
        Charge flipkartUpiCharge = new Charge(Client.FLIPKART, new BigDecimal("500.00"), upiApp);

        // 4. Process it
        System.out.println("Processing Flipkart UPI Charge...");
        processor.processCharge(flipkartUpiCharge);

        // 5. Try a Swiggy transaction (which we haven't onboarded yet to test failure)
        Charge swiggyCharge = new Charge(Client.SWIGGY, new BigDecimal("200.00"), upiApp);
        System.out.println("Processing Swiggy UPI Charge...");
        processor.processCharge(swiggyCharge);
    }
}