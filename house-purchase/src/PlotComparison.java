import java.util.Scanner;

public class PlotComparison {

    static double basePlotPrice;
    static double freeholdChargePercent;
    static double stampDutyPercent;
    static double downPaymentPercentage;
    static double loanRate;
    static int loanYears;
    static double plotAppreciation;
    static double investmentReturn;
    static double inflationRate;

    public static void main(String[] args) {
        if (!readInputs()) {
            return;
        }

        double freeholdCharge = basePlotPrice * freeholdChargePercent;
        double registryValue = basePlotPrice + freeholdCharge;
        double stampDuty = registryValue * stampDutyPercent;
        double downPayment = basePlotPrice * downPaymentPercentage;
        
        double loanAmount = basePlotPrice - downPayment;
        double totalUpfront = downPayment + freeholdCharge + stampDuty;
        
        double emi = calculateEMI(loanAmount, loanRate, loanYears);
        
        // Guy 1 (Plot Buyer)
        // Freehold conversion increases the plot's inherent value, so it appreciates from registryValue
        double futurePlotValue = registryValue * Math.pow(1 + plotAppreciation, loanYears);
        double sellingBrokerage = futurePlotValue * 0.01;
        double finalPlotNetWorth = futurePlotValue - sellingBrokerage;
        
        // Guy 2 (SIP Investor)
        double sipCorpus = totalUpfront;
        double monthlyReturn = investmentReturn / 12.0;
        int totalMonths = loanYears * 12;
        
        double totalInvestmentMade = totalUpfront;
        
        for (int month = 1; month <= totalMonths; month++) {
            sipCorpus *= (1 + monthlyReturn);
            sipCorpus += emi;
            totalInvestmentMade += emi;
        }
        
        double finalSipNetWorth = sipCorpus;
        
        printResults(registryValue, totalUpfront, loanAmount, emi, futurePlotValue, sellingBrokerage, finalPlotNetWorth, totalInvestmentMade, finalSipNetWorth);
    }
    
    private static void printResults(double registryValue, double totalUpfront, double loanAmount, double emi, double futurePlotValue, double sellingBrokerage, double finalPlotNetWorth, double totalInvestmentMade, double finalSipNetWorth) {
        String format = "| %-35s | %-25s | %-32s |%n";
        String separator = "----------------------------------------------------------------------------------------------------";

        System.out.println("\n" + separator);
        System.out.printf(format, "Metric", "Guy 1 (Plot Buyer)", "Guy 2 (SIP Investor)");
        System.out.println(separator);
        
        System.out.printf(format, "Base Plot Price", String.format("₹%.2f Lakhs", basePlotPrice / 1e5), "-");
        System.out.printf(format, "Freehold Charge", String.format("₹%.2f Lakhs", (basePlotPrice * freeholdChargePercent) / 1e5), "-");
        System.out.printf(format, "Registry Value (Base + Freehold)", String.format("₹%.2f Lakhs", registryValue / 1e5), "-");
        System.out.printf(format, "Stamp Duty & Reg", String.format("₹%.2f Lakhs", (registryValue * stampDutyPercent) / 1e5), "-");
        System.out.printf(format, "Down Payment", String.format("₹%.2f Lakhs", (basePlotPrice * downPaymentPercentage) / 1e5), "-");
        
        System.out.println(separator);
        System.out.printf(format, "Total Upfront Cash (Sunk)", String.format("₹%.2f Lakhs", totalUpfront / 1e5), String.format("₹%.2f Lakhs (Invested)", totalUpfront / 1e5));
        System.out.printf(format, "Loan Amount", String.format("₹%.2f Lakhs", loanAmount / 1e5), "-");
        System.out.printf(format, "Monthly Outflow", String.format("₹%,.0f (EMI)", emi), String.format("₹%,.0f (SIP)", emi));
        
        System.out.println(separator);
        System.out.printf(format, "Total Capital Invested", String.format("₹%,.0f (%.2f Cr)", totalInvestmentMade, totalInvestmentMade / 1e7), String.format("₹%,.0f (%.2f Cr)", totalInvestmentMade, totalInvestmentMade / 1e7));
        
        double totalEmiPaid = emi * loanYears * 12;
        double totalInterestPaid = totalEmiPaid - loanAmount;
        System.out.printf(format, "Total EMI Paid (15 Yrs)", String.format("₹%,.0f (%.2f Lakhs)", totalEmiPaid, totalEmiPaid / 1e5), "-");
        System.out.printf(format, "  ├─ Principal Repaid", String.format("₹%,.0f (%.2f Lakhs)", loanAmount, loanAmount / 1e5), "-");
        System.out.printf(format, "  └─ Interest Paid", String.format("₹%,.0f (%.2f Lakhs)", totalInterestPaid, totalInterestPaid / 1e5), "-");
        
        double totalSipPaid = emi * loanYears * 12;
        System.out.printf(format, "Total SIP Paid (15 Yrs)", "-", String.format("₹%,.0f (%.2f Lakhs)", totalSipPaid, totalSipPaid / 1e5));
        System.out.printf(format, "  ├─ Upfront Lumpsum", "-", String.format("₹%,.0f (%.2f Lakhs)", totalUpfront, totalUpfront / 1e5));
        System.out.printf(format, "  └─ Monthly SIPs", "-", String.format("₹%,.0f (%.2f Lakhs)", totalSipPaid, totalSipPaid / 1e5));
        
        double sipProfit = finalSipNetWorth - totalInvestmentMade;
        System.out.printf(format, "Total Investment Profit", "-", String.format("₹%,.0f (%.2f Cr)", sipProfit, sipProfit / 1e7));
        
        System.out.println(separator);
        System.out.printf(format, "Gross Asset Value", String.format("₹%,.0f (%.2f Cr)", futurePlotValue, futurePlotValue / 1e7), String.format("₹%,.0f (%.2f Cr)", finalSipNetWorth, finalSipNetWorth / 1e7));
        System.out.printf(format, "Less: Selling Brokerage (1%)", String.format("-₹%,.0f (%.2f Lakhs)", sellingBrokerage, sellingBrokerage / 1e5), "-");
        
        System.out.println(separator);
        System.out.printf(format, "FINAL NET WORTH", String.format("₹%,.0f (%.2f Cr)", finalPlotNetWorth, finalPlotNetWorth / 1e7), String.format("₹%,.0f (%.2f Cr)", finalSipNetWorth, finalSipNetWorth / 1e7));
        System.out.println(separator);

        double difference = finalPlotNetWorth - finalSipNetWorth;
        double presentValueDifference = Math.abs(difference) / Math.pow(1 + inflationRate, loanYears);
        System.out.println();
        if (difference > 0) {
            System.out.printf("PLOT wins by: ₹%.2f Cr%n", difference / 1e7);
            System.out.printf("(Inflation adjusted present value: ₹%.2f Lakhs)%n", presentValueDifference / 1e5);
        } else if (difference < 0) {
            System.out.printf("SIP wins by: ₹%.2f Cr%n", -difference / 1e7);
            System.out.printf("(Inflation adjusted present value: ₹%.2f Lakhs)%n", presentValueDifference / 1e5);
        } else {
            System.out.println("It's a perfect TIE!");
        }
        
        System.out.println("\n----- Important Note -----");
        System.out.println("Unlike an apartment, rent is EXCLUDED from this model. Because you cannot live on a vacant plot,");
        System.out.println("both Guy 1 (Plot Buyer) and Guy 2 (SIP Investor) must pay rent to live elsewhere.");
        System.out.println("Since their rent outflow is exactly the same, it cancels out of the financial equation completely.");
        System.out.println("This is a pure comparison of Leveraged Land vs Unleveraged Equity.");
        
        System.out.println("\n----- Excluded Real-Life Variables -----");
        System.out.println("The following minor realities are intentionally excluded to keep the math balanced:");
        System.out.println("- LTCG Tax (12.5%): Excluded because both Real Estate and Equities are now taxed at exactly 12.5% in India, so it cancels out.");
        System.out.println("- Holding Costs: Minor expenses like building a boundary wall (~₹2L), hiring a caretaker, and paying municipal vacant land tax.");
        System.out.println("- Liquidity: Equities can be liquidated in 48 hours. A plot can take 6-12 months to sell, locking your capital.");
        System.out.println("- Encroachment Risk: The unquantifiable but massive risk of local land grabbing or legal disputes on vacant land.");
        System.out.println("- Fractional Liquidity: You cannot sell 'a portion' of a plot in an emergency; it's all or nothing.");
        System.out.println("- Zero Yield: A plot yields no rent or dividends; you rely 100% on a future buyer paying a higher price.");
        System.out.println("- Regulatory Risk: Zoning laws, FAR changes, or government acquisition can arbitrarily alter land value.");
        System.out.println("- Minor Sunk Costs: Loan processing fees, forced bank insurance, mutation (Dakhil Kharij) bribes, and legal/notary fees (~₹1-1.5L) are intentionally ignored.");
    }
    
    private static double calculateEMI(double principal, double rate, int years) {
        double monthlyRate = rate / 12.0;
        int totalMonths = years * 12;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) / (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }

    private static boolean readInputs() {
        Scanner scanner = new Scanner(System.in);

        basePlotPrice = readDoubleOrDefault(scanner, "Base Plot price", 5000000.0);
        freeholdChargePercent = readDoubleOrDefault(scanner, "Freehold charge (%)", 12.0) / 100.0;
        stampDutyPercent = readDoubleOrDefault(scanner, "Stamp Duty & Registration (%)", 8.0) / 100.0;
        downPaymentPercentage = readDoubleOrDefault(scanner, "Down payment percentage (%)", 20.0) / 100.0;
        
        loanRate = readDoubleOrDefault(scanner, "Loan rate (as decimal, e.g. 0.09)", 0.09);
        loanYears = readIntOrDefault(scanner, "Loan years", 15);
        
        plotAppreciation = readDoubleOrDefault(scanner, "Plot appreciation (%)", 11.5) / 100.0;
        investmentReturn = readDoubleOrDefault(scanner, "Investment return (%)", 10.0) / 100.0;
        inflationRate = readDoubleOrDefault(scanner, "Inflation rate (%)", 5.0) / 100.0;

        return true;
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
