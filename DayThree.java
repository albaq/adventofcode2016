import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alvaro on 1/7/17.
 */
public class DayThree {
    public static final int NUMBER_OF_SIDES_IN_TRIANGLE = 3;

    public static void main(String[] args) {
        DayThree dayThree = new DayThree();
        List<String> lines = dayThree.readLinesFromFile("DayThreeInput.txt");
        long count= dayThree.quantityOfHorizontalValidTriangles(lines);

        System.out.println(count);

        count= dayThree.quantityOfVerticalValidTriangles(lines);
        System.out.println(count);
    }

    public long quantityOfVerticalValidTriangles(List<String> lines) {
        long count=0;
        int index = 0;
        List<String> accumulator = new ArrayList<>();
        for(String line : lines){
            index++;
            if(index % NUMBER_OF_SIDES_IN_TRIANGLE == 0) {
                accumulator.add(line);
                Triplet left=null, right=null, middle=null;
                for(String subline : accumulator) {
                    String [] parts = subline.trim().split("\\s+");
                    left = addToTriplet(left, parts[0]);
                    middle = addToTriplet(middle, parts[1]);
                    right = addToTriplet(right, parts[2]);
                }

                count += Stream.of(left, middle, right).filter(Objects::nonNull).filter(Triplet::isValid).count();

                accumulator.clear();
            } else {
                accumulator.add(line);
            }
        }

        return count;
    }

    private List<String> readLinesFromFile(String name) {
        Path path = Paths.get(name);
        Stream<String> linesFromFile = Stream.empty();
        try {
            linesFromFile = Files.lines(path);
        } catch (IOException e) {}
        return linesFromFile.collect(Collectors.toList());
    }

    private Triplet addToTriplet(Triplet t, String parts) {
        if(t==null){
            t = new Triplet(Integer.valueOf(parts));
        } else if(t.middle == 0){
            t.middle = Integer.valueOf(parts);
        } else {
            t.right = Integer.valueOf(parts);
        }
        return t;
    }

    public long quantityOfHorizontalValidTriangles(List<String> lines) {
        return lines
                    .stream()
                    .map(line-> {
                        String [] parts = line.trim().split("\\s+");
                        if(parts.length == NUMBER_OF_SIDES_IN_TRIANGLE) return new Triplet(Integer.valueOf(parts[0]),
                                Integer.valueOf(parts[1]),
                                Integer.valueOf(parts[2]));
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .filter(Triplet::isValid)
                    .count();
    }
}

class Triplet {
    int left, middle, right;

    public Triplet(int left, int middle, int right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public Triplet(int left) {
        this(left, 0, 0);
    }

    public boolean isValid() {
        return (((left + middle) > right) && ((left+right)>middle) && ((middle+right)>left));
    }
}
