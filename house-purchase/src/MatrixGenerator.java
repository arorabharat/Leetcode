import java.io.PrintWriter;
import java.io.IOException;

public class MatrixGenerator {

    static double apartmentPrice = 2.4e7;
    static double loanRate = 0.085;
    static int loanYears = 20;
    static double initialRent = 70000;
    static double rentIncrease = 0.05;
    static double downPaymentPercentage = 0.20;
    static double furnishingCost = 1500000.0;
    static double repairCostPercent = 0.005;

    // We will dynamically override these in the loop
    static double apartmentAppreciation;
    static double investmentReturn;
    
    // Tracking for internal methods (reset every loop)
    static double buyerTaxBenefitCorpus;
    static double renterBrokerageCostFV;
    static double renterMovingCostFV;
    static double finalSecurityDepositRefund;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter("matrix_output.txt")) {
            writer.println("Buy vs Rent Comparison Matrix (Difference in Final Net Worth)");
            writer.println("Format: Winner (Difference in Crores)");
            writer.println("Rows: Apartment Appreciation (1% to 12%)");
            writer.println("Columns: Investment Return (1% to 12%)\n");

            // Print header row
            writer.printf("%-10s", "Appr \\ Inv");
            for (int inv = 1; inv <= 12; inv++) {
                writer.printf("| %-12s", inv + "%");
            }
            writer.println("|");

            // Print separator
            writer.print("----------");
            for (int inv = 1; inv <= 12; inv++) {
                writer.print("+-------------");
            }
            writer.println("+");

            // Loop Appr (1 to 12)
            for (int appr = 1; appr <= 12; appr++) {
                writer.printf("%-10s", appr + "%");
                apartmentAppreciation = appr / 100.0;

                for (int inv = 1; inv <= 12; inv++) {
                    investmentReturn = inv / 100.0;
                    String result = calculateDifference();
                    writer.printf("| %-12s", result);
                }
                writer.println("|");
            }
            System.out.println("Matrix successfully generated to matrix_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String calculateDifference() {
        // Reset tracking vars
        buyerTaxBenefitCorpus = 0;
        renterBrokerageCostFV = 0;
        renterMovingCostFV = 0;
        finalSecurityDepositRefund = 0;

        double emi = calculateEMI();
        double apartmentNetWorth = calculateBuyApartmentNetWorth();
        double rentAndInvestNetWorth = calculateRentAndInvestNetWorth(emi);

        double buyerSellingBrokerage = apartmentNetWorth * 0.01;
        double finalBuyerNetWorth = apartmentNetWorth - buyerSellingBrokerage;
        double finalRenterNetWorth = rentAndInvestNetWorth - renterBrokerageCostFV - renterMovingCostFV + finalSecurityDepositRefund;

        double difference = finalBuyerNetWorth - finalRenterNetWorth;
        double diffInCrores = Math.abs(difference) / 1e7;

        if (difference > 0) {
            return String.format("B (%.2fCr)", diffInCrores);
        } else if (difference < 0) {
            return String.format("R (%.2fCr)", diffInCrores);
        } else {
            return "TIE";
        }
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
            buyerTaxBenefitCorpus += 120000;
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
        
        double monthlyRent = initialRent;
        double securityDeposit = getSecurityDepositAmount(monthlyRent);
        double corpus = initialCorpus - securityDeposit;
        
        for (int year = 1; year <= loanYears; year++) {
            double currentApartmentValue = apartmentPrice * Math.pow(1 + apartmentAppreciation, year - 1);
            double annualRepairCost = currentApartmentValue * repairCostPercent;
            
            double monthlySip = (emi - monthlyRent) + (annualRepairCost / 12.0);
            
            double annualBrokerage = monthlyRent;
            renterBrokerageCostFV *= (1 + investmentReturn);
            renterBrokerageCostFV += annualBrokerage;
            
            double annualMovingAndPainting = monthlyRent * 1.0; 
            renterMovingCostFV *= (1 + investmentReturn);
            renterMovingCostFV += annualMovingAndPainting;
            
            for (int month = 1; month <= 12; month++) {
                corpus *= (1 + monthlyInvestmentReturn);
                corpus += monthlySip;
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
}
