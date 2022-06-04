/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.util.*;

/**
 *
 * @author nisyaqanita
 */
public class Payment implements Comparable<Payment> {

    /**
     * @param args the command line arguments
     */
    
    private Long epoch;
    private String txnId;
    private String tier;
    
    final static String PLATINUM = "PLATINUM";
    final static String GOLD = "GOLD";
    final static String SILVER = "SILVER";
    
    public Payment(Long epoch, String txnId, String tier) {
        
//        decrease epoch time regarding the tier
        if (tier.equalsIgnoreCase(PLATINUM)) {
            epoch -= 3000;
                
        }
        else if (tier.equalsIgnoreCase(GOLD)) {
             epoch -= 2000;
                
        }
        else if (tier.equalsIgnoreCase(SILVER)) {
            epoch -= 1000;
        }
        
        this.epoch = epoch;
        this.txnId = txnId;
        this.tier = tier;
    }

    public Long getEpoch() {
        return epoch;
    }

    public void setEpoch(Long epoch) {
        this.epoch = epoch;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
    
    @Override
    public int compareTo(Payment o) {
        return this.getEpoch().compareTo(o.getEpoch());
    }
    
    public static String toStr(PriorityQueue<Payment> q) {
        String result = "";
        int i = 0; // **
        while(!q.isEmpty() && i < 100){ // **
            result += q.poll() + " ";
            i++; //**
        }
//        System.out.println("queue's current length: " + q.size());
        return result;
    }
    
    @Override
    public String toString() {
        return this.getTxnId();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        final String exit = "EXIT";
        final String reboot = "REBOOT";
        
        Long epoch;
        Long lastAddedEpoch = 0L;
        String txnId;
        String tier;
        int diff = 0;
        
        PriorityQueue<Payment> meowsQ = new PriorityQueue<>(/*Collections.reverseOrder()*/);
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextLine()) {
            
            try {
                String in = sc.nextLine();
                in.trim();

                if (in.equalsIgnoreCase(exit)) {
                    //print txnId
//                    System.out.println(toStr(meowsQ)); // **
                    break;
                } else if (in.equalsIgnoreCase(reboot)) {
                    meowsQ.clear(); 
//                    break; // ** it got commented
                } else {
                    String[] inArr = in.split(" ");

                    if (inArr.length != 3) {
                        throw new InputMismatchException();
                    }

                    epoch = Long.valueOf(inArr[0]);
                    txnId = inArr[1];
                    tier = inArr[2];

                    // decide if a second has elapsed
                    if (!meowsQ.isEmpty()) {
                        // peek to last epoch added to the queue
//                        Long lastTransaction = meowsQ.peek().getEpoch();
//                        String tierChecker = meowsQ.peek().getTier();

//                        if (tierChecker.equalsIgnoreCase(PLATINUM)) {
//                            lastAddedEpoch += 3000;
//                        } else if (tierChecker.equalsIgnoreCase(GOLD)) {
//                            lastAddedEpoch += 2000;
//                        } else if (tierChecker.equalsIgnoreCase(SILVER)) {
//                            lastAddedEpoch += 1000;
//                        }

                        lastAddedEpoch /= 1000;
                        int secondDigitLast = (lastAddedEpoch.intValue()) % 10;

                        // take the digit seconds from just added transaction
                        Long epochTemp = epoch;
                        epochTemp /= 1000;
                        int secondDigitJust = (epochTemp.intValue()) % 10;
                        
                        diff = secondDigitJust - secondDigitLast;
                        lastAddedEpoch = epoch;
                    } else {
                        lastAddedEpoch = epoch;
                    }
                    meowsQ.add(new Payment(epoch, txnId, tier));
                    if (diff == 1) { 
                        //print txnId
                        System.out.println(toStr(meowsQ));
//                        System.out.println();
                        diff = 0; // just added this line
//                        break; // got commented
                    }
                }
            } catch (NumberFormatException e) {
                return;
            } catch (InputMismatchException e) {
                return;
            }
        }          
  
    }

    
    
}
