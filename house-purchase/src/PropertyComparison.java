import java.util.Scanner;

public class PropertyComparison {

    static double apartmentPrice;
    static double loanRate;
    static int loanYears;
    static double initialRent;
    static double rentIncrease;
    static double monthlyIncome;
    static double apartmentAppreciation;
    static double investmentReturn;
    static double downPaymentPercentage;
    static double furnishingCost;
    
    // Global tracking variables
    static double totalRentPaid;
    static double totalInvestmentMade;
    static double inflationRate;
    static double buyerTaxBenefitCorpus;
    static double totalBrokeragePaid;
    static double renterBrokerageCostFV;
    static double finalSecurityDepositRefund;
    
    static double totalMovingAndPaintingPaid;
    static double renterMovingCostFV;
    
    static double repairCostPercent;
    static double totalRepairsInvested;
    static double buyerSellingBrokerage;

    public static void main(String[] args) {
        if (!readInputs()) {
            return;
        }

        double emi = calculateEMI();
        double apartmentNetWorth = calculateBuyApartmentNetWorth();
        double rentAndInvestNetWorth = calculateRentAndInvestNetWorth(emi);

        printRentSchedule(emi);
        printResults(apartmentNetWorth, rentAndInvestNetWorth, emi);
        printDisclaimer();
    }

    private static void printDisclaimer() {
        System.out.println("\n----- Note on Real-Life Risks -----");
        System.out.println("The following real-life risks exist but were excluded, assuming ideal behavior:");
        System.out.println("- Floating Interest Rates: Home loan rates can increase. (Mitigated if Buyer closes loan early)");
        System.out.println("- Rent Spikes: Landlords can demand massive rent hikes. (Mitigated if Renter is careful/negotiates well)");
        System.out.println("-----------------------------------");
        System.out.println("\n----- Excluded Minor Variables -----");
        System.out.println("- Maintenance Costs / HOA (Assumed similar out-of-pocket for both)");
        System.out.println("- Long Term Capital Gains (LTCG) Tax (Applies to both property sale and investment withdrawals)");
        System.out.println("- Property Tax (Considered negligible in this specific context)");
        System.out.println("- Minor Renter Costs: Society move-in fees, rent agreement registration, and furniture depreciation/breakage during moves. (These are absorbed by our aggressive assumption of moving every year).");
        System.out.println("- Minor Buyer Costs: Loan processing fees, home insurance, and one-time sinking fund/Khata transfer fees.");
        System.out.println("- Amortizing Tax Benefits (Home loan tax benefits decrease over time, but are assumed flat here)");
        System.out.println("------------------------------------");
        System.out.println("\n----- Real-Life Behavioral Factors (Unmodeled) -----");
        System.out.println("- The Discipline Factor: An EMI is forced savings. A SIP is voluntary. Very few renters have the robotic discipline to invest the exact difference for 20 years without spending it.");
        System.out.println("- Loan Prepayment: Most buyers use annual bonuses to prepay their loan, closing a 20-year loan in 7-10 years, saving Crores in interest.");
        System.out.println("- Salary Growth: Salaries grow over time. Since the EMI is fixed, buyers have massive extra cash flow later in life. This model assumes neither party invests their salary hikes.");
        System.out.println("------------------------------------------------------");
        System.out.println("\n----- Qualitative Pros & Cons (The Lifestyle Factor) -----");
        System.out.println("Guy 1 (Buyer):");
        System.out.println("  + Ultimate Peace of Mind: Absolute stability during adverse situations (job loss, pandemics). Nobody can evict you.");
        System.out.println("  + Zero Shifting Hassle: Never have to deal with brokers, packing, or hunting for a new house ever again.");
        System.out.println("  + Creative Freedom: Absolute freedom to modify, drill, paint, and design the house exactly to your taste.");
        System.out.println("  - Illiquid Asset: Huge portion of net worth is locked in brick and mortar, difficult to cash out quickly.");
        System.out.println("  - Maintenance Burden: Must personally manage and pay for all structural repairs and plumbing issues.");
        System.out.println("  - Aging Society Risk: Stuck in an aging building as newer, better societies are built nearby over 20 years.");
        System.out.println("Guy 2 (Renter):");
        System.out.println("  + Supreme Flexibility: Can easily shift closer to a new office, drastically saving daily commute time.");
        System.out.println("  + Zero Maintenance Headaches: A leaking roof or broken pipe is the landlord's problem, not yours.");
        System.out.println("  + Society Upgrades: Can always upgrade to live in brand-new, modern societies even in the later stages of life.");
        System.out.println("  + Zero Capital Risk: No risk of losing your entire corpus to a delayed builder project, legal dispute, or bad construction.");
        System.out.println("  - Instability & Hassle: Constantly dealing with arbitrary rent hikes, landlord restrictions, and the immense stress of moving.");
        System.out.println("----------------------------------------------------------\n");
    }

    private static void printRentSchedule(double emi) {
        int currentYear = java.time.Year.now().getValue();
        System.out.println("\n----- Yearly Rent Schedule -----");
        String format = "| %-6s | %-16s | %-18s | %-20s | %-24s | %-16s |%n";
        String separator = "-----------------------------------------------------------------------------------------------------------------------";
        System.out.println(separator);
        System.out.printf(format, "Year", "Monthly Rent", "Annual Rent Paid", "Brokerage (1 Month)", "Moving & Painting (1 Mo)", "Monthly SIP");
        System.out.println(separator);
        
        double currentRent = initialRent;
        double cumulativeRent = 0;
        double cumulativeBrokerage = 0;
        double cumulativeMoving = 0;
        
        for (int i = 0; i < loanYears; i++) {
            double annualRent = currentRent * 12;
            double brokerage = currentRent;
            double movingCost = currentRent * 1.0; // 50% movers + 50% painting
            double monthlySip = emi - currentRent;
            
            cumulativeRent += annualRent;
            cumulativeBrokerage += brokerage;
            cumulativeMoving += movingCost;
            
            System.out.printf(format, String.valueOf(currentYear + i), String.format("₹%,.0f", currentRent), String.format("₹%,.0f", annualRent), String.format("₹%,.0f", brokerage), String.format("₹%,.0f", movingCost), String.format("₹%,.0f", monthlySip));
            currentRent *= (1 + rentIncrease);
        }
        System.out.println(separator);
        System.out.printf(format, "TOTAL", "-", String.format("₹%,.0f", cumulativeRent), String.format("₹%,.0f", cumulativeBrokerage), String.format("₹%,.0f", cumulativeMoving), "-");
        System.out.println(separator);
    }

    private static boolean readInputs() {
        Scanner scanner = new Scanner(System.in);

        apartmentPrice = readDoubleOrDefault(scanner, "Apartment price", 2.4e7);
        downPaymentPercentage = readDoubleOrDefault(scanner, "Down payment percentage (%)", 20.0) / 100.0;
        furnishingCost = readDoubleOrDefault(scanner, "Furnishing cost", 1500000.0);
        
        loanRate = readDoubleOrDefault(scanner, "Loan rate (as decimal, e.g. 0.085)", 0.085);
        loanYears = readIntOrDefault(scanner, "Loan years", 20);
        initialRent = readDoubleOrDefault(scanner, "Initial rent", 70000);
        rentIncrease = readDoubleOrDefault(scanner, "Rent increase (as decimal, e.g. 0.05)", 0.05);
        monthlyIncome = readDoubleOrDefault(scanner, "Monthly income", 250000);

        if (calculateEMI() > monthlyIncome) {
            System.out.println("impossible purchase");
            return false;
        }

        apartmentAppreciation = readDoubleOrDefault(scanner, "Apartment appreciation (%)", 6.0) / 100.0;
        investmentReturn = readDoubleOrDefault(scanner, "Investment return (%)", 10.0) / 100.0;
        inflationRate = readDoubleOrDefault(scanner, "Inflation rate (%)", 5.0) / 100.0;
        repairCostPercent = readDoubleOrDefault(scanner, "Annual repair cost (%)", 0.5) / 100.0;

        return true;
    }

    private static double calculateEMI() {
        double loanPrincipal = apartmentPrice * (1.0 - downPaymentPercentage);
        double monthlyRate = loanRate / 12.0;
        int totalMonths = loanYears * 12;
        return (loanPrincipal * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) / (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }

    private static double calculateBuyApartmentNetWorth() {
        double propertyValue = apartmentPrice * Math.pow(1 + apartmentAppreciation, loanYears);
        
        buyerTaxBenefitCorpus = 0;
        for (int year = 1; year <= loanYears; year++) {
            buyerTaxBenefitCorpus *= (1 + investmentReturn);
            buyerTaxBenefitCorpus += 120000; // Annual tax benefit
        }
        
        return propertyValue + buyerTaxBenefitCorpus;
    }

    private static double getDownPaymentAmount() {
        return apartmentPrice * downPaymentPercentage;
    }

    private static double getStampDutyAmount() {
        return apartmentPrice * 0.10;
    }

    private static double getSecurityDepositAmount(double monthlyRentAmount) {
        return 4 * monthlyRentAmount;
    }

    private static double calculateRentAndInvestNetWorth(double emi) {
        double monthlyInvestmentReturn = investmentReturn / 12.0;
        
        double downPaymentAmount = getDownPaymentAmount();
        double stampDutyAmount = getStampDutyAmount();
        double initialCorpus = downPaymentAmount + stampDutyAmount + furnishingCost;
        
        totalInvestmentMade = initialCorpus;
        totalRentPaid = 0.0;
        totalBrokeragePaid = 0.0;
        renterBrokerageCostFV = 0.0;
        totalMovingAndPaintingPaid = 0.0;
        renterMovingCostFV = 0.0;

        double monthlyRent = initialRent;
        
        double securityDeposit = getSecurityDepositAmount(monthlyRent);
        double corpus = initialCorpus - securityDeposit;
        
        totalRepairsInvested = 0.0;

        for (int year = 1; year <= loanYears; year++) {
            // Guy 1 pays repairs, Guy 2 invests it instead.
            double currentApartmentValue = apartmentPrice * Math.pow(1 + apartmentAppreciation, year - 1);
            double annualRepairCost = currentApartmentValue * repairCostPercent;
            totalRepairsInvested += annualRepairCost;
            
            double monthlySip = (emi - monthlyRent) + (annualRepairCost / 12.0);
            
            // Calculate Brokerage FV (Paid at start of year / same time we assess year)
            double annualBrokerage = monthlyRent;
            totalBrokeragePaid += annualBrokerage;
            renterBrokerageCostFV *= (1 + investmentReturn);
            renterBrokerageCostFV += annualBrokerage;
            
            double annualMovingAndPainting = monthlyRent * 1.0; // 50% movers + 50% painting
            totalMovingAndPaintingPaid += annualMovingAndPainting;
            renterMovingCostFV *= (1 + investmentReturn);
            renterMovingCostFV += annualMovingAndPainting;
            
            for (int month = 1; month <= 12; month++) {
                corpus *= (1 + monthlyInvestmentReturn);
                corpus += monthlySip;
                
                totalRentPaid += monthlyRent;
                totalInvestmentMade += monthlySip;
            }
            monthlyRent *= (1 + rentIncrease);
            
            if (year < loanYears) {
                double newDeposit = getSecurityDepositAmount(monthlyRent);
                double depositTopUp = newDeposit - securityDeposit;
                corpus -= depositTopUp;
                securityDeposit = newDeposit;
            }
        }
        
        finalSecurityDepositRefund = securityDeposit;

        return corpus;
    }

    private static void printResults(double apartmentNetWorth, double rentAndInvestNetWorth, double emi) {
        double downPaymentAmount = getDownPaymentAmount();
        double stampDutyAmount = getStampDutyAmount();
        double totalUpfront = downPaymentAmount + stampDutyAmount + furnishingCost;

        String format = "| %-35s | %-25s | %-32s |%n";
        String separator = "----------------------------------------------------------------------------------------------------";

        System.out.println("\n" + separator);
        System.out.printf(format, "Metric", "Guy 1 (Buyer)", "Guy 2 (Renter)");
        System.out.println(separator);
        
        System.out.printf(format, "Down Payment", String.format("₹%.2f Lakhs", downPaymentAmount / 1e5), "-");
        System.out.printf(format, "Stamp Duty (10%)", String.format("₹%.2f Lakhs", stampDutyAmount / 1e5), "-");
        System.out.printf(format, "Furnishing", String.format("₹%.2f Lakhs", furnishingCost / 1e5), "-");
        System.out.printf(format, "Total Upfront Cash / Corpus", String.format("₹%.2f Lakhs", totalUpfront / 1e5), String.format("₹%.2f Lakhs", totalUpfront / 1e5));
        System.out.printf(format, "Monthly Outflow (Year 1)", String.format("₹%,.0f (EMI)", emi), String.format("₹%,.0f (Rent)", initialRent));
        double firstYearRepairMonthly = (apartmentPrice * repairCostPercent) / 12.0;
        System.out.printf(format, "Monthly Investment (Year 1)", "-", String.format("₹%,.0f (SIP + Repairs)", (emi - initialRent) + firstYearRepairMonthly));
        System.out.printf(format, "Total Rent Paid", "-", String.format("₹%,.0f (%.2f Cr)", totalRentPaid, totalRentPaid / 1e7));
        System.out.printf(format, "Total Brokerage Paid", "-", String.format("₹%,.0f (%.2f Lakhs)", totalBrokeragePaid, totalBrokeragePaid / 1e5));
        System.out.printf(format, "Total Moving & Painting Paid", "-", String.format("₹%,.0f (%.2f Lakhs)", totalMovingAndPaintingPaid, totalMovingAndPaintingPaid / 1e5));
        System.out.printf(format, "Total Avoided Repairs Invested", "-", String.format("₹%,.0f (%.2f Lakhs)", totalRepairsInvested, totalRepairsInvested / 1e5));
        
        double investedPrincipal = totalInvestmentMade - finalSecurityDepositRefund;
        System.out.printf(format, "Total Investment Profit", "-", String.format("₹%,.0f (%.2f Cr)", (rentAndInvestNetWorth - investedPrincipal), (rentAndInvestNetWorth - investedPrincipal) / 1e7));
        System.out.printf(format, "Add: Tax Benefit Corpus", String.format("₹%,.0f (%.2f Lakhs)", buyerTaxBenefitCorpus, buyerTaxBenefitCorpus / 1e5), "-");
        System.out.printf(format, "Less: Brokerage Opportunity Cost", "-", String.format("-₹%,.0f (%.2f Lakhs)", renterBrokerageCostFV, renterBrokerageCostFV / 1e5));
        System.out.printf(format, "Less: Moving & Painting Opp. Cost", "-", String.format("-₹%,.0f (%.2f Lakhs)", renterMovingCostFV, renterMovingCostFV / 1e5));
        System.out.printf(format, "Add: Refunded Security Deposit", "-", String.format("₹%,.0f (%.2f Lakhs)", finalSecurityDepositRefund, finalSecurityDepositRefund / 1e5));
        
        buyerSellingBrokerage = apartmentNetWorth * 0.01;
        
        System.out.println(separator);
        double finalBuyerNetWorth = apartmentNetWorth - buyerSellingBrokerage;
        double finalRenterNetWorth = rentAndInvestNetWorth - renterBrokerageCostFV - renterMovingCostFV + finalSecurityDepositRefund;
        
        System.out.printf(format, "Less: Selling Brokerage (1%)", String.format("-₹%,.0f (%.2f Lakhs)", buyerSellingBrokerage, buyerSellingBrokerage / 1e5), "-");
        System.out.printf(format, "FINAL NET WORTH", String.format("₹%,.0f (%.2f Cr)", finalBuyerNetWorth, finalBuyerNetWorth / 1e7), String.format("₹%,.0f (%.2f Cr)", finalRenterNetWorth, finalRenterNetWorth / 1e7));
        System.out.println(separator);

        double difference = finalBuyerNetWorth - finalRenterNetWorth;
        double presentValueDifference = Math.abs(difference) / Math.pow(1 + inflationRate, loanYears);
        System.out.println();
        if (difference > 0) {
            System.out.printf("BUY apartment wins by: ₹%.2f Cr%n", difference / 1e7);
            System.out.printf("(Inflation adjusted present value: ₹%.2f Cr)%n", presentValueDifference / 1e7);
        } else if (difference < 0) {
            System.out.printf("RENT + INVEST wins by: ₹%.2f Cr%n", -difference / 1e7);
            System.out.printf("(Inflation adjusted present value: ₹%.2f Cr)%n", presentValueDifference / 1e7);
        } else {
            System.out.println("It's a perfect TIE!");
        }
    }

    private static double readDoubleOrDefault(Scanner scanner, String prompt, double defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static int readIntOrDefault(Scanner scanner, String prompt, int defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
