import java.util.Scanner;

public class InputReader {
    private static boolean exists;
    private Scanner scanner;

    public InputReader(){
        this(new Scanner(System.in)); 

    }

    public InputReader(Scanner scanner){
        if (!exists){
            this.scanner = scanner;
            exists = true;
        }
        else {
            throw new IllegalStateException("you cannot put more than one scanner");
        }
    }

    public int readInt(String prompt){
        int input = -1;

         while (input < 0){
            System.out.print(prompt + " ?>");
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input < 0) {
                    System.out.print("ERROR: input cannot be empty, try again.");
                }
            } else {
                System.out.print("ERROR: input cannot be negative, try again.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return input;
        
    }

    public double readDouble(String prompt){
        double input = -1;

        while (input < 0){
            System.out.print(prompt + " ?>");
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                if (input < 0) {
                    System.out.print("ERROR: input cannot be empty, try again.");
                }
            } else {
                System.out.print("ERROR: input cannot be negative, try again.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return input;
    }

    public String readString(String prompt){
        String input = "";
        while(input == null || input.isBlank()){
            System.out.print(prompt + " ?>");
            input = scanner.nextLine().trim();

            if(input == null || input.isBlank()){
                System.out.print("invalid input, try again.");
            }
        }
        return input;
    }


}
