import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        int n = scanner.nextInt();
        if ((n > -15 & n <= 12) || (n > 14 & n < 17) || (n >= 19)) {
            System.out.println("True");    
        } else {
            System.out.println("False");
        }
        
    }
}
