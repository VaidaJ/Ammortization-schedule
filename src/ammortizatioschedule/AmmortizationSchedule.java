package ammortizatioschedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author Vaidos
 */
public class AmmortizationSchedule {
    int payment;
    String paymentDate;
    double remainingAmount;
    double principalPayment;
    double interestPayment;
    double totalPayment;
    double interestRate;
    public static ArrayList<AmmortizationSchedule> ammortizationSchedule = new ArrayList<AmmortizationSchedule>();

    public AmmortizationSchedule(int payment,String paymentDate, double remainingAmount, double principalPayment, double interestPayment, double totalPayment, double interestRate) {
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.remainingAmount = remainingAmount;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = totalPayment;
        this.interestRate = interestRate;
    }
    public AmmortizationSchedule(){
    }

    public int getPayment() {
        return payment;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public double getPrincipalPayment() {
        return principalPayment;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }
    
    public ArrayList<AmmortizationSchedule> createAmmortizationSchedule(String startDate, int numberOfPeriods, double value, double rate){
        try{  
            int paymentNr;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(startDate);
            date.setMonth(date.getMonth() - 1);
            double remainingAmount = value;
            double principalPayment;
            double interestPayment;
            double rateOfPeriod = rate/12/100;
            double totalPayment = (rateOfPeriod*remainingAmount)/(1-Math.pow((1+rateOfPeriod), numberOfPeriods*(-1)));    
            double precisionTotalPayment = BigDecimal.valueOf(totalPayment).setScale(2, RoundingMode.HALF_UP).doubleValue();
            for(int i = 0; i < numberOfPeriods; i++){
                paymentNr = i+1;
                date.setMonth(date.getMonth() + 1);
                interestPayment = rateOfPeriod * remainingAmount;
                principalPayment = totalPayment - interestPayment;      
                AmmortizationSchedule schedule = new AmmortizationSchedule(paymentNr, formatter.format(date),
                        BigDecimal.valueOf(remainingAmount).setScale(2, RoundingMode.HALF_UP).doubleValue(), 
                        BigDecimal.valueOf(principalPayment).setScale(2, RoundingMode.HALF_UP).doubleValue(),
                        BigDecimal.valueOf(interestPayment).setScale(2, RoundingMode.HALF_UP).doubleValue(), 
                        precisionTotalPayment, rate);
                ammortizationSchedule.add(schedule);
                remainingAmount -= principalPayment;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return ammortizationSchedule;
    }  
    
    public ArrayList<AmmortizationSchedule> createScheduleWithInput(){
        AmmortizationSchedule schedule = new AmmortizationSchedule();
        Scanner sc = new Scanner(System.in);
        System.out.println("Iveskite pradzios data foramtu yyyy-mm-dd:");
        String date = sc.nextLine();
        System.out.println("Iveskite menesiniu imoku skaiciu:");
        int payments = sc.nextInt();         
        System.out.println("Iveskite paskolos suma:");
        double sum = sc.nextFloat();         
        sc.nextLine();
        System.out.println("Iveskite metiniu palukanu norma:");
        double interestRate = sc.nextFloat();         
        sc.nextLine();
        return schedule.createAmmortizationSchedule(date, payments, sum, interestRate);    
    }
}
