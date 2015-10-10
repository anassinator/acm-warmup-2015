import java.util.Scanner;

public class WordCloud {
    public static final int SPACING_BETWEEN_WORDS = 10;

    public static int get_width(String input, int size) {
        return (int)Math.ceil(9.0/16.0 * input.length() * size);
    }

    public static int get_size(int count, int max_count) {
        return 8 + (int)Math.ceil(40.0 * (count - 4) / (max_count - 4));
    }

    public static int get_height(String[] words, int[] counts, int max_width) {
        int max_count = 0;
        for (int i = 0; i < counts.length; i++) {
            max_count = Math.max(max_count, counts[i]);
        }

        int current_width = 0;
        int current_height = 0;
        int current_max_size = 0;

        for (int i = 0; i < words.length; i++) {
            int current_size = get_size(counts[i], max_count);
            int width = get_width(words[i], current_size);
            current_width += width + SPACING_BETWEEN_WORDS;

            if (current_width > (max_width + SPACING_BETWEEN_WORDS)) {
                current_width = width + SPACING_BETWEEN_WORDS;
                current_height += current_max_size;
                current_max_size = current_size;
            } else {
                current_max_size = Math.max(current_size, current_max_size);
            }
        }
        return current_height + current_max_size;
    }

    public static void main( String[] args ) {
        Scanner stdin = new Scanner(System.in);
        int cloud_number = 0;
        while (++cloud_number > 0) {
            int max_width = stdin.nextInt();
            int word_count = stdin.nextInt();
            if (max_width == 0 && word_count == 0) {
                break;
            }

            String[] words = new String[word_count];
            int[] counts = new int[word_count];
            for (int i = 0; i < word_count; i++) {
                words[i] = stdin.next();
                counts[i] = stdin.nextInt();
            }

            System.out.print("CLOUD " + String.valueOf(cloud_number) + ": ");
            System.out.println(get_height(words, counts, max_width));
        }
    }
}
