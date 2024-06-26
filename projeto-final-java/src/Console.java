import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {

    private static Scanner leitor = new Scanner(System.in);

    public static String lerString(String msg) {
        System.out.print(msg + " ");
        return leitor.nextLine();
    }

    public static int lerInt(String msg) {
        int valor = 0;
        while (true) {
            try {
                System.out.print(msg + " ");
                valor = leitor.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("O valor digitado não é do tipo 'int'");
            } finally {
                leitor.nextLine(); // limpeza de buffer
            }
        }
        return valor;
    }
}

