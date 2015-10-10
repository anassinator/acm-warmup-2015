import java.util.Scanner;
import java.util.ArrayList;

public class Multiplication {
    public int a;
    public int b;
    public ArrayList<Integer> a_digits = new ArrayList<Integer>();
    public ArrayList<Integer> b_digits = new ArrayList<Integer>();
    public ArrayList<Box> boxes = new ArrayList<Box>();

    public class Box {
        public int top;
        public int bottom;
        public Box(int a, int b) {
            int mult = a * b;
            this.top = (mult / 10) % 10;
            this.bottom = mult % 10;
        }
    }

    public Multiplication(int a, int b) {
        this.a = a;
        this.b = b;

        ArrayList<Integer> a_digits = new ArrayList<Integer>();
        ArrayList<Integer> b_digits = new ArrayList<Integer>();

        while (a > 0) {
            a_digits.add(a % 10);
            a /= 10;
        }

        while (b > 0) {
            b_digits.add(b % 10);
            b /= 10;
        }
    }

    public void draw() {

    }

    public static void main( String[] args ) {
        Scanner stdin = new Scanner(System.in);

        while (true) {
            int a = stdin.nextInt();
            int b = stdin.nextInt();
            if (a == 0 && b == 0) {
                break;
            }

            Multiplication mult = new Multiplication(a, b);
            mult.draw();
        }
    }
}
