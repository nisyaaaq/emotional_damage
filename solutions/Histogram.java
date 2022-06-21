import java.util.*;
import java.lang.*;
/**
 *
 */
class Histogram {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int NumCases = input.nextInt();
        input.nextLine();
        for(int i=0;i<NumCases;i++){
            int numOfData = input.nextInt(); 
            int numOfBin = input.nextInt();
            input.nextLine();
            int[] strArr2 = new int[numOfData];
            for(int k=0;k<numOfData;k++){
                strArr2[k] = input.nextInt();
            }
            input.nextLine();
            int[] newArray = new int[numOfBin + 1];;
            int cutoff = intervals(strArr2, numOfBin, newArray);
            Frequency(strArr2,numOfBin,newArray);
        }
            
    }
    public static int max(int[] array){
            int max = 0;
            for(int i=0;i< array.length;i++){
                if(array[i]>max){
                    max = array[i];
                }
            }
            return max;
        }
        public static int min(int[] array){
            int min = array[0];
            for(int i=0;i< array.length; i++){
                if(array[i]<min){
                    min = array[i];
                }
            }
            return min;
        }

        public static int intervals(int[] N2, int numOfBin, int[] newArray){

            int min = min(N2);
            int max = max(N2);
            int cutoff = (max - min) / numOfBin;
            int interval = min;
            for (int i = 0; i <= numOfBin; i++) {
                newArray[i] = interval;
                interval += cutoff;

            }
            String line = "";
            for (int inter: newArray) {
                line += inter + " ";
            }
            System.out.println(line);
            return cutoff;
        }

        public static void Frequency(int[] N2, int numOfBin, int[] newArray){
            int[] frequency = new int[numOfBin];
            frequency[frequency.length-1] = 1;
            for (int number: N2) {
                for (int i = 0; i < newArray.length-1; i++) {
                    if (number >= newArray[i] && number<newArray[i+1]){
                        frequency[i]++;
                    }
                }
            }
            String line1="";
            for (int freq: frequency) {
       
                line1 += freq + " ";
            }
            System.out.println(line1);
        }
}
