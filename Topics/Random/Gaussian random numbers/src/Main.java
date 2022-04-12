import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        int k = scanner.nextInt();
        int n = scanner.nextInt();
        double m = scanner.nextDouble();
        boolean b;
        do {
            Random random = new Random(k);
            b = true;
            for (int i = 0; i < n; i++) {
                if (random.nextGaussian() > m) {
                    b = false;
                    break;
                }
            }
            k++;
        } while (!b);
        
        System.out.println(k - 1);
    }
}
