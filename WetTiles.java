import java.util.Scanner;
import java.util.ArrayList;

public class WetTiles {
    public int width;
    public int height;
    public int[][] tiles;
    public int wet_count = 0;
    public ArrayList<Coordinate> latest_wet_tiles =
        new ArrayList<Coordinate>();

    public WetTiles(int width, int height, ArrayList<Coordinate> leaks,
                    ArrayList<Line> walls) {
        this.tiles = new int[width][height];
        this.width = width;
        this.height = height;

        for (Coordinate leak: leaks) {
            this.tiles[leak.x][leak.y] = 1;
            wet_count++;
            latest_wet_tiles.add(new Coordinate(leak.x, leak.y));
        }

        for (Line line: walls) {
            ArrayList<Coordinate> coords = line.flatten();
            for (Coordinate coord: coords) {
                this.tiles[coord.x][coord.y] = -1;
            }
        }
    }

    public static class Coordinate {
        public int x;
        public int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Line {
        public Coordinate start;
        public Coordinate end;
        public Line(Coordinate start, Coordinate end) {
            if (start.x > end.x && start.y > end.y) {
                this.start = end;
                this.end = start;
            } else {
                this.start = start;
                this.end = end;
            }
        }

        public ArrayList<Coordinate> flatten() {
            ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
            int x_gradient = start.x <= end.x ? 1 : -1;
            int y_gradient = start.y <= end.y ? 1 : -1;

            if (start.x == end.x || start.y == end.y) {
                for (int x = start.x; x <= end.x; x += x_gradient) {
                    for (int y = start.y; y <= end.y; y += y_gradient) {
                        coords.add(new Coordinate(x, y));
                    }
                }
            } else if (start.x < end.x || start.y > end.y) {
                int y = start.y;
                for (int x = start.x; x <= end.x; x++) {
                    coords.add(new Coordinate(x, y));
                    y += y_gradient;
                }
            } else {
                int x = start.x;
                for (int y = start.y; y <= end.y; y += y_gradient) {
                    coords.add(new Coordinate(x, y));
                    x += x_gradient;
                }
            }

            return coords;
        }
    }

    public boolean within_bounds(Coordinate pos) {
        return (pos.x >= 0
                && pos.x < width
                && pos.y >= 0
                && pos.y < height);
    }

    public void spread(Coordinate pos) {
        Coordinate[] surrounding = {
            new Coordinate(pos.x, pos.y - 1),
            new Coordinate(pos.x, pos.y + 1),
            new Coordinate(pos.x - 1, pos.y),
            new Coordinate(pos.x + 1, pos.y)
        };

        int minute = tiles[pos.x][pos.y] + 1;

        for (Coordinate surrounding_tile: surrounding) {
            if (within_bounds(surrounding_tile)) {
                if (tiles[surrounding_tile.x][surrounding_tile.y] == 0) {
                    tiles[surrounding_tile.x][surrounding_tile.y] = minute;
                    wet_count++;
                    latest_wet_tiles.add(new Coordinate(
                        surrounding_tile.x, surrounding_tile.y));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void next() {
        ArrayList<Coordinate> current_wet_tiles =
            (ArrayList<Coordinate>)latest_wet_tiles.clone();
        latest_wet_tiles = new ArrayList<Coordinate>();
        for (Coordinate tile: current_wet_tiles) {
            spread(new Coordinate(tile.x, tile.y));
            latest_wet_tiles.remove(tile);
        }
    }

    public int get_wet_tile_count() {
        return wet_count;
    }

    public void simulate(int minutes) {
        for (int min = 1; min < minutes; min++) {
            if (latest_wet_tiles.isEmpty()) {
                return;
            }
            next();
        }
    }

    public void draw() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int time = tiles[x][y];
                if (time != -1){
                    System.out.print(time);
                } else {
                    System.out.print('.');
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public static void main( String[] args ) {
        Scanner stdin = new Scanner(System.in);

        while (true) {
            int x = stdin.nextInt();
            if (x == -1) {
                break;
            }
            int y = stdin.nextInt();
            int minutes = stdin.nextInt();
            int leak_count = stdin.nextInt();
            int wall_count = stdin.nextInt();

            ArrayList<Coordinate> leaks = new ArrayList<Coordinate>();
            for (int i = 0; i < leak_count; i++) {
                leaks.add(new Coordinate(stdin.nextInt() - 1,
                                         stdin.nextInt() - 1));
            }

            ArrayList<Line> walls = new ArrayList<Line>();
            for (int i = 0; i < wall_count; i++) {
                walls.add(new Line(
                    new Coordinate(stdin.nextInt() - 1, stdin.nextInt() - 1),
                    new Coordinate(stdin.nextInt() - 1, stdin.nextInt() - 1)
                ));
            }

            WetTiles house = new WetTiles(x, y, leaks, walls);
            house.simulate(minutes);
            house.draw();
            System.out.println(house.get_wet_tile_count());
        }
    }
}
