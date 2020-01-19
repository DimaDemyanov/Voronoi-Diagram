package diagram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PointReader {

    private Scanner sc;

    public PointReader(File file) throws FileNotFoundException {
        sc = new Scanner(file);
    }

    public Point[] readAllPoints() {
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextFloat(), sc.nextFloat(), i);
        }
        return points;
    }

}
