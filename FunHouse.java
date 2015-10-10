import java.util.Scanner;
import java.util.ArrayList;

public class FunHouse {
    public int width;
    public int height;
    public char[][] tiles;
    public Coordinate entry;
    public boolean solved = false;
    public Coordinate current_pos;
    public Direction current_direction;
    public ArrayList<Coordinate> mirrors = new ArrayList<Coordinate>();

    public final char WALL = 'x';
    public final char EXIT = '&';
    public final char ENTRY = '*';
    public final char EMPTY_TILE = '.';
    public final char FORWARD_MIRROR = '/';
    public final char BACKWARD_MIRROR = '\\';

    public class Coordinate {
      public int x;
      public int y;
      public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }

    public enum Direction {
        UP,
        RIGHT,
        LEFT,
        DOWN
    }

    public FunHouse(int width, int height, char[][] tiles) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                switch (tiles[y][x]) {
                    case ENTRY:
                        this.entry = new Coordinate(x, y);
                        break;
                    case FORWARD_MIRROR:
                    case BACKWARD_MIRROR:
                        this.mirrors.add(new Coordinate(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        this.current_pos = this.entry;
        if (this.entry.x == 0) {
            this.current_direction = Direction.RIGHT;
        } else if (this.entry.x == this.width - 1) {
            this.current_direction = Direction.LEFT;
        } else if (this.entry.y == 0) {
            this.current_direction = Direction.DOWN;
        } else if (this.entry.y == this.height - 1) {
            this.current_direction = Direction.UP;
        }
    }

    public boolean within_bounds() {
        return (current_pos.x >= 0
                && current_pos.x < width
                && current_pos.y >= 0
                && current_pos.y < height);
    }

    public void bounce() {
        char mirror = tiles[current_pos.y][current_pos.x];
        if (mirror == BACKWARD_MIRROR) {
            switch (current_direction) {
                case UP:
                    current_direction = Direction.LEFT;
                    break;
                case DOWN:
                    current_direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    current_direction = Direction.DOWN;
                    break;
                case LEFT:
                    current_direction = Direction.UP;
                    break;
            }
        } else if (mirror == FORWARD_MIRROR) {
            switch (current_direction) {
                case UP:
                    current_direction = Direction.RIGHT;
                    break;
                case DOWN:
                    current_direction = Direction.LEFT;
                    break;
                case RIGHT:
                    current_direction = Direction.UP;
                    break;
                case LEFT:
                    current_direction = Direction.DOWN;
                    break;
            }
        }
    }

    public void next() {
        switch (current_direction) {
            case UP:
                current_pos.y -= 1;
                break;
            case DOWN:
                current_pos.y += 1;
                break;
            case RIGHT:
                current_pos.x += 1;
                break;
            case LEFT:
                current_pos.x -= 1;
                break;
        }

        if (within_bounds()) {
            switch (tiles[current_pos.y][current_pos.x]) {
                case BACKWARD_MIRROR:
                case FORWARD_MIRROR:
                    bounce();
                    break;
                case WALL:
                    tiles[current_pos.y][current_pos.x] = EXIT;
                    solved = true;
                    break;
            }
        }
    }

    public void find_exit() {
        while (!solved) {
            next();
        }
    }

    public void draw() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                System.out.print(tiles[y][x]);
            }
            System.out.println("");
        }
    }

    public static void main( String[] args ) {
        Scanner stdin = new Scanner(System.in);

        int house_number = 0;
        while (++house_number > 0) {
            int x = stdin.nextInt();
            int y = stdin.nextInt();
            if (x == 0 && y == 0) {
                break;
            }

            char[][] tiles = new char[y][x];
            for (int j = 0; j < y; j++) {
                String row = stdin.next();
                for (int i = 0; i < x; i++) {
                    tiles[j][i] = row.charAt(i);
                }
            }
            FunHouse house = new FunHouse(x, y, tiles);

            System.out.println("HOUSE " + String.valueOf(house_number));
            house.find_exit();
            house.draw();
        }
    }
}
