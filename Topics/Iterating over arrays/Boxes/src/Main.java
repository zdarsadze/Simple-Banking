import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        ArrayList<Integer> arr1 = new ArrayList<>();
        ArrayList<Integer> arr2 = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        final int n = 3;
        for (int i = 0; i < n; i++) {
            arr1.add(scanner.nextInt());
        }
        
        for (int i = 0; i < n; i++) {
            arr2.add(scanner.nextInt());
        }
        
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr1.get(j) > arr1.get(j + 1)) {
                    int temp = arr1.get(j);
                    arr1.set(j, arr1.get(j + 1));
                    arr1.set(j + 1, temp);
                }
            }
        }
        
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr2.get(j) > arr2.get(j + 1)) {
                    int temp = arr2.get(j);
                    arr2.set(j, arr2.get(j + 1));
                    arr2.set(j + 1, temp);
                }
            }
        }
        
        if (arr1.get(0) > arr2.get(0) & arr1.get(1) > arr2.get(1) & arr1.get(2) > arr2.get(2)) {
            System.out.println("Box 1 > Box 2");
        } else if (arr1.get(0) < arr2.get(0) & arr1.get(1) < arr2.get(1) & arr1.get(2) < arr2.get(2)) {
            System.out.println("Box 1 < Box 2");
        } else {
            System.out.println("Incompatible");
        }
        //System.out.println(arr1);
        //System.out.println(arr2);
        
    }
}
