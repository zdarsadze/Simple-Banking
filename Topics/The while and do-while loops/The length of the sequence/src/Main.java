import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int s = 0;
        while (scanner.nextInt() != 0) {
            s++;
        }
        System.out.println(s);
    }
}