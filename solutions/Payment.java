/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
// package keLima;

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
    final static String BRONZE = "BRONZE";
    
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
        else if (tier.equalsIgnoreCase(BRONZE)) {
            epoch -= 0;
        } 
        else {
            throw new InputMismatchException();
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
        return result.trim();
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
        
        PriorityQueue<Payment> meowsQ = new PriorityQueue<>();
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextLine()) {
            
            try {
                String in = sc.nextLine();

                if (in.equalsIgnoreCase(exit)) {
                    break;
                } else if (in.equalsIgnoreCase(reboot)) {
                    meowsQ.clear(); 
                } else {
                    String [] inArr = in.split(" ", 3); // deleted 'String[]' just now
                    int lengthEpoch = inArr[0].length(); // added this
                    int lengthId = inArr[1].length(); // added this
                    
                    if (lengthEpoch != 13 || lengthId != 32) { // added this if block
                        throw new InputMismatchException();
                    }
                    
                    epoch = Long.valueOf(inArr[0]);
                    txnId = inArr[1];
                    tier = inArr[2];

                    // decide if a second has elapsed
                    if (!meowsQ.isEmpty()) {
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
                    Payment data = new Payment(epoch, txnId, tier); 
                    meowsQ.add(data);
                    if (diff == 1) { 
                        String ans = toStr(meowssQ);
                        System.out.println(ans);
                        diff = 0;
                    }
                }
            } catch (NumberFormatException e) {
                return;
            } catch (InputMismatchException e) {
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
        }          
  
    }

    
    
}
