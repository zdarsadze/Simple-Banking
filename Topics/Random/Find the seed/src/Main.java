import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        
        int[] max = new int[b-a+1];
        int temp;
        
        for (int i = a; i <= b; i++) {
            Random random = new Random(i);
            int localMax = random.nextInt(k);
            //System.out.println(localMax);
            for (int j = 2; j <= n; j++) {
                temp = random.nextInt(k);
                //System.out.println(temp);
                if (temp > localMax) {
                    localMax = temp;
                }
            }
            max[i - a] = localMax;
        }
        
        int minIndex = 0;
        int min = max[0];
        
        for (int i = 1; i <= b - a; i++) {
            if (max[i] < min) {
                minIndex = i;
                min = max[i];
                //break;
            }
        }
        
        System.out.println(minIndex + a);
        System.out.println(min);
        //System.out.println(Arrays.toString(max));
    }
}
