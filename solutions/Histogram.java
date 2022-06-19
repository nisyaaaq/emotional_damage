import java.util.Scanner;
class Histogram{

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int NumOfCases = in.nextInt();
//        ArrayList<Integer> ans = new ArrayList<>();

        for(int c=0; c<NumOfCases; c++) {
            int NumOfData = in.nextInt();
            int NumOfBins = in.nextInt();

            int[] DataPoints = new int[NumOfData];
            for (int b = 0; b < NumOfData; b++) {
                DataPoints[b] = in.nextInt();
            }

            int max = DataPoints[0];
            int min = DataPoints[0];

            for (int i = 0; i < DataPoints.length; i++) {
                if (DataPoints[i] > max) {
                    max = DataPoints[i];
                }
                if (DataPoints[i] < min) {
                    min = DataPoints[i];
                }
            }

            int CutoffsValue = (max - min) / NumOfBins;
            int sum = 0;
            int[] interval = new int[NumOfBins + 1];
            int[] count = new int[NumOfBins];

            for (int i = 0; i < interval.length; i++) {
                if (i == 0) {
                    System.out.print(min + " ");
                    sum += min;
                    interval[i] = min;
//                    ans.add(min);
                } else {
                    System.out.print(sum + " ");
                    interval[i] = sum;
//                    ans.add(sum);
                }
                sum += CutoffsValue;
            }
//            ans.add(00);
            System.out.println();


            for (int i = 0; i < DataPoints.length; i++) {
                for (int j = 0; j < interval.length - 1; j++) {
                    if (DataPoints[i] >= interval[j] && DataPoints[i] < interval[j + 1]) {
                        count[j]++;
                    }
                }
                if (DataPoints[i] == interval[interval.length - 1]) {
                    count[interval.length - 2]++;
                }
            }
//            for (int i = 0; i < count.length; i++){
//                ans.add(count[i]);
//            }
//            ans.add(00);

            for (int i = 0; i < count.length; i++) {
                System.out.print(count[i] + " ");
            }
            System.out.println();
        }

//        while(!ans.isEmpty()){
//            int num = ans.remove(0);
//            if(num != 0){
//                System.out.print(num + " ");
//            }
//            if(num == 0){
//                System.out.println();
//            }
//        }
    }
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
            for (int inter: newArray) {
                System.out.print(inter + " ");
            }
            System.out.println();
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
            for (int freq: frequency) {
                System.out.print(freq + " ");
            }
        }
    }
