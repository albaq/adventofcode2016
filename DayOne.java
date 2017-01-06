import java.awt.Point;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.Arrays;

enum Direction {
    North, South, East, West
}

public class DayOne {
    private Map<Direction, Integer> steps;
    private Set<Point> map;
    private Point stand;

    private boolean intersectionFound;
    private int distanceToIntersection;

    private Direction actualDirection;

    private String commandLine;

    public DayOne(String commandLine) {
        this.steps = new HashMap<>();
        this.map = new HashSet<>();
        this.actualDirection = Direction.North;
        this.commandLine = commandLine;
        this.stand = new Point(0, 0);
        this.intersectionFound = false;
        this.distanceToIntersection = -1;
    }

    public boolean isIntersectionFound() {
        return this.intersectionFound;
    }

    public int getDistanceToIntersection() {
        return this.distanceToIntersection;
    }

    public static void main(String[] args) {
        DayOne resp = new DayOne("L4, R2, R4, L5, L3, L1, R4, R5, R1, R3, L3, L2, L2, R5, R1, L1, L2, R2, R2, L5, R5, R5, L2, R1, R2, L2, L4, L1, R5, R2, R1, R1, L2, L3, R2, L5, L186, L5, L3, R3, L5, R4, R2, L5, R1, R4, L1, L3, R3, R1, L1, R4, R2, L1, L4, R5, L1, R50, L4, R3, R78, R4, R2, L4, R3, L4, R4, L1, R5, L4, R1, L2, R3, L2, R5, R5, L4, L1, L2, R185, L5, R2, R1, L3, R4, L5, R2, R4, L3, R4, L2, L5, R1, R2, L2, L1, L2, R2, L2, R1, L5, L3, L4, L3, L4, L2, L5, L5, R2, L3, L4, R4, R4, R5, L4, L2, R4, L5, R3, R1, L1, R3, L2, R2, R1, R5, L4, R5, L3, R2, R3, R1, R4, L4, R1, R3, L5, L1, L3, R2, R1, R4, L4, R3, L3, R3, R2, L3, L3, R4, L2, R4, L3, L4, R5, R1, L1, R5, R3, R1, R3, R4, L1, R4, R3, R1, L5, L5, L4, R4, R3, L2, R1, R5, L3, R4, R5, L4, L5, R2");

        int answer = resp.execute();

        System.out.println(String.format("distance to Easter Bunny HQ: %d", answer));
        if(resp.intersectionFound) {
            System.out.println(String.format("distance to intersection: %d",resp.distanceToIntersection));
        }
    }

    public int execute(){
        parse().forEach(command -> {
            this.actualDirection = veer(command.direction);
            forward(command.numSteps);
            if(!intersectionFound) {
                findIntersection(command.numSteps);
            }
        });

        return distance();

    }

    private void findIntersection(int numSteps) {
        int x = (int)this.stand.getX();
        int y = (int)this.stand.getY();

        for(int i=0; i<numSteps; i++) {
            Point path = advanceOneStep(x, y);
            if(this.map.contains(path)) {
                this.intersectionFound = true;
                this.distanceToIntersection = path.x + path.y;
            } else {
                this.map.add(path);
            }
            this.stand.move(path.x, path.y);

            x = path.x;
            y = path.y;
        }
    }

    private Point advanceOneStep(int x, int y) {
        switch(this.actualDirection) {
            case North: y++; break;
            case South: y--; break;
            case East: x++; break;
            case West: x--; break;
        }
        return new Point(x, y);
    }

    private int distance(){
        return Math.abs(this.steps.getOrDefault(Direction.East, 0)-this.steps.getOrDefault(Direction.West, 0))
                + Math.abs(this.steps.getOrDefault(Direction.South, 0)-this.steps.getOrDefault(Direction.North, 0));
    }

    private Stream<Command> parse() {
        return Arrays.stream(this.commandLine.split(","))
                .map(String::trim)
                .map(elem->new Command(elem.charAt(0), Integer.valueOf(elem.substring(1))));
    }

    private Direction veer(char command) {
        Direction answer = Direction.North;
        switch(command) {
            case 'R':
                answer = moveRight(this.actualDirection);
                break;
            case 'L':
                answer = moveLeft (this.actualDirection);
                break;
        }
        return answer;
    }

    private void forward(int numSteps) {
        int i = this.steps.getOrDefault(this.actualDirection, 0);
        i += numSteps;
        this.steps.put(this.actualDirection, i);
    }

    private Direction moveRight(Direction actual){
        return rotate(actual, true);
    }

    private Direction moveLeft(Direction actual){
        return rotate(actual, false);
    }

    private Direction rotate(Direction actual, boolean veerRight) {
        Direction answer = Direction.North;
        switch (actual) {
            case North:
                answer = veerRight?Direction.East: Direction.West;
                break;
            case East:
                answer = veerRight?Direction.South: Direction.North;
                break;
            case South:
                answer = veerRight?Direction.West: Direction.East;
                break;
            case West:
                answer = veerRight?Direction.North: Direction.South;
                break;
        }
        return answer;
    }
}

class Command {
    char direction;
    int numSteps;

    Command(char direction, int numSteps) {
        this.direction = direction;
        this.numSteps = numSteps;

    }
}
