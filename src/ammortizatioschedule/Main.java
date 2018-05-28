package ammortizatioschedule;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Vaidos
 */
public class Main {

    public static void main(String[] args) {
        AmmortizationSchedule schedule = new AmmortizationSchedule();
        while(true){
        Scanner sc = new Scanner(System.in);
        System.out.println("Pasirinkite veiksma");
        System.out.println("1. Sugeneruoti grafika su duotais duomenimis");
        System.out.println("2. Sugeneruoti grafika ivedant savo duomenis");
        System.out.println("3. Baigti darba");
        int nr = sc.nextInt();
            switch(nr){
                case 1:
                    createFile(schedule.createAmmortizationSchedule("2017-04-15", 24, 5000, 12), "scheduleExample.csv"); 
                    break;
                case 2:
                    createFile(schedule.createScheduleWithInput(), "scheduleWithInput.csv"); 
                    break;
                case 3:
                    System.exit(0);
                    break; 
                default: 
                    System.out.println("Toks pasirinkimas negalimas, iveskyte pasirinkima is naujo:");
                    break;
            }
        }
    }  
    
    public static void createFile(ArrayList<AmmortizationSchedule> list, String name){
        String newLine = "\n";
        String fileHeader = StringUtils.leftPad("Payment #", 10, ' ') +
                            StringUtils.leftPad("Payment date", 20, ' ') +
                            StringUtils.leftPad("Remaining amount", 20, ' ') +
                            StringUtils.leftPad("Principal payment", 20, ' ') +
                            StringUtils.leftPad("Interset payment", 20, ' ') +
                            StringUtils.leftPad("Total payment", 20, ' ') +
                            StringUtils.leftPad("Interest rate", 20, ' '); 
        try{
            FileWriter fw = new FileWriter(name);
            fw.append(fileHeader);
            for(AmmortizationSchedule schedule: list){
                fw.append(newLine);
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getPayment()), 10, ' '));
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getPaymentDate()), 20, ' '));             
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getRemainingAmount()), 20, ' '));
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getPrincipalPayment()), 20, ' '));
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getInterestPayment()), 20, ' '));
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getTotalPayment()), 20, ' '));
                fw.append(StringUtils.leftPad(String.valueOf(schedule.getInterestRate()), 20, ' '));
            }
            fw.flush();
            fw.close();
            System.out.println("Failas sukurtas");
        }
        catch(Exception e){
            System.out.println("Klaida");
        }     
    }
}
