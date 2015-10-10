import java.util.Scanner;

public class ReverseRot {
    public static char get_next(char c, int n) {
        while (n-- > 0) {
            switch (c) {
                case 'Z':
                    c = '_';
                    break;
                case '_':
                    c = '.';
                    break;
                case '.':
                    c = 'A';
                    break;
                default:
                    c = (char)(c + 1);
                    break;
            }
        }
        return c;
    }

    public static String reverse_rot(String input, int n) {
        int length = input.length();
        char[] char_array = input.toCharArray();
        for (int i = length - 1; i >= 0; i--) {
            char c = input.charAt(i);
            char_array[length - i - 1] = get_next(c, n);
        }
        return new String(char_array);
    }

    public static void main( String[] args ) {
        Scanner stdin = new Scanner(System.in);
        while (true) {
            int n = stdin.nextInt();
            if (n == 0) {
                break;
            }
            String input = stdin.next();
            System.out.println(reverse_rot(input, n));
        }
    }
}
