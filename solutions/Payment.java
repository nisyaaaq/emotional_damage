/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Fixed;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author nisyaqanita
 */
public class payment {
    Queue<String> input = new PriorityQueue<>(Collections.reverseOrder());
    static String reboot = "REBOOT";
    static String exit = "EXIT";
    
    public payment(Queue q) {
        for (Object item : q) {
            String str = (String) item;
            String oldEpoch = str.substring(0, str.indexOf(" "));
            long epoch = Long.parseLong(str.substring(0, str.indexOf(" ")));
            if (str.substring(str.lastIndexOf(" ") + 1).equalsIgnoreCase("PLATINUM")) {
                epoch -= 3000;
                
            }
            else if (str.substring(str.lastIndexOf(" ") + 1).equalsIgnoreCase("GOLD")) {
                epoch -= 2000;
                
            }
            else if (str.substring(str.lastIndexOf(" ") + 1).equalsIgnoreCase("SILVER")) {
                epoch -= 1000;
            }
            
            Long time = System.currentTimeMillis();
            epoch = time - epoch;
            String epochStr = String.valueOf(epoch);
            str = str.replace(oldEpoch, epochStr);
            input.add(str);
        }
    }
    
    public String txnSorted() {
        String result = "";
        int i = 0;
        while (i < 100) {
            String str = input.poll();
            if (str == null) {
                break;
            }
            result += (str.substring((str.indexOf(" ") + 1), str.lastIndexOf(" ")) + " ");
            i++;
        }
        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        Queue<String> data = new LinkedList<>();
        String lastItemAdded;
        int addedDigit, stackDigit, diff = 0;
        
        do {
            String in = sc.nextLine();
            if (in.equalsIgnoreCase(exit)) {
                break;
            } 
            else if (in.equalsIgnoreCase(reboot)) {
                data.clear();
            }
            else {
                addedDigit = Integer.parseInt(in.substring(9, 10));
                data.add(in);
                lastItemAdded = data.peek();
                stackDigit = Integer.parseInt(lastItemAdded.substring(9, 10));
                diff = addedDigit - stackDigit;
            }

        } while (diff == 0);
        
        payment info = new payment(data);
        System.out.println(info.txnSorted());
    }
    
}
