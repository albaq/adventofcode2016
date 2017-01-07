import java.util.Arrays;
import java.util.List;

public class SecondDay {
    private List<String> lines;

    public SecondDay() {
        String lineOne   = "DLDRDDDLULDRRLUDDLDUURDRDUULDRDDRRLDLLUUDDLLRLRDRUURLUDURDDRURLUDDUULUURLLRRRRUDULUDLULLUURRLLRRURRUDUUURRLUUUDURDLLLDULDRLRDDDUDDUURLRRRURULLUDDUULDRRRDDLRLUDDRRDLRDURLRURUDDUULDDUUDDURRLUURRULRRLDLULLRLRUULDUDDLLLRDDULRUDURRDUUDUUDDUULULURDLUDRURDLUUDRDUURDDDRDRLDLDRURRLLRURURLLULLRRUULRRRRDLDULDDLRRRULRURRDURUDUUULDUUDRLDDLDUDDRULLUDUULRRRDRRDRDULDLURDDURLRUDLURLUDDDRLLURUUUUUUURUULDUUDDRLULRUDURRDLDUULLRLULLURDDDDDLRRDLRLLDDUDRRRDDURDLRRUDDUDLRRRDDURULRURRRLDRDUDLD";
        String lineTwo   = "LRRDUDUUUDRRURRDUUULULUDDLLDRRRUDDUULRRDRUDRLLRLRULRRDUUDRLDURUDLLLDRRDLRLUUDRUDRRRUDRRRULDRRLLRDDDLLRDDRULRLLRUDRLLLULDLDDRDRUUUUUULURLLRUDRDRLLULLRUUURRDRULULUDLDURRUUDURLLUDRDLDDULUDLRDDRLRLURULDRURRRRURRDDUDRULUUUDDDRULRULDLLURUUULRDDLRUURLRLDLUULLURDRDDDUDDDRLDRDLLDRDDDDURLUUULDDRURULUDDURDRDRLULDULURDUURDRLLUUUULRULUUDRLLDDRRURUURLDLLRRRDLRURDDLDLDRLRRDLDURULDDLULRRRUUDLRDUURDURLURDDLDLRURLLLDRDULDDRUDDULDDRRLDLRDRDLDUUDLUULRLUDUUDUUUULDURULRRUDULURLRLDRLULLLDUDLLLRUDURDDDURLDDLRLRRDLUDLDDDDLULDRLDUUULDRRDDLRUULDLULUUURUDDRLDDDULRUDRURUURUUURRULRURDURLLRLLUULUULURDRLLUDDLU";
        String lineThree = "LLDURDUDRLURUDRLRLUDDRRURDULULDDUDUULRRLRLRRDRDRDURRLRLURRLRUDULLUULLURUDDRLDDDRURLUUDLDURRDURDDLUULRDURRUUURLRRURRDRDRDURRRLULLDRUDLRUDURDRDDLLULLULRRUDULDDRDRRDLLLDLURLRDRDLUDDRLDDLDRULDURLLRLDRDLUDDDDLDUUDRLLRRRRLDDRRLRLURLLRLLUULLDUUDLRDRRRDRDLLDULLDRLDDUDRDDRURRDDLRDLRRUUDRRRRDURUULDRDDURLURRRRURRDRRULULURULUUUDRRRLDLLLDDRULRUDDURDRLDDRDLULLLRURUDRLRDDLDLRRRUURDURLDURRUUDDLRDRUUUURDLRLULRUUDRLDLULLULUURURDULUDUDRRRLLRLURLLDLRRURURRUDLUDDDDRDUDUDUUUULLDRDLLLLUUUUDRLRLUDURLLUDRUUDLLURUULDDDDULUUURLLDL";
        String lineFour  = "DLULLRDLRRLLLDLRRURRDRURDRUUULDDRLURURRDLRRULUUDDRLRRLDULRRUUDUULDDDUDLLDLURDRLLULLUUULLDURDRRRDDLRDUDRRRLRLDRRLRLULDDUDURRRLDLRULDULDDUDDRULDLDRDRDDRUDRUDURRRRUUDUDRLDURLDLRRUURRDDUDLLDUDRRURRLRRRRRLDUDDRLLLURUDRRUDRLRDUDUUUUUDURULLDUUDLRUUULDUUURURLUUDULDURUDDDLRRRDDRRDLRULLLRDDRLRLUULDUUULLLLDLRURLRRDURRLDLLLDURDLLUDDDLLDDURDDULURDRRRDDDLDDURRULUUDDLULLURULUULDLDDLUDRURURULUDDULRDRLDRRRUUUURUULDRLRRURRLULULURLLDRLRLURULRDDDULRDDLUR";
        String lineFive  = "RURRULLRRDLDUDDRRULUDLURLRRDDRDULLLUUDDDRDDRRULLLDRLRUULRRUDLDLLLRLLULDRLDDDLLDDULLDRLULUUUURRRLLDRLDLDLDDLUDULRDDLLRLLLULLUDDRDDUUUUDLDLRRDDRDLUDURRUURUURDULLLLLULRRLDRLRDLUURDUUDLDRURURLLDRRRLLLLRDLDURRLRRLLRUUDDUULLRLUDLRRRRRURUDDURULURRUULRDDULUUDUUDDRDDDDDUUUDDDRRLDDRRDDUUULDURLDULURDRDLLURDULRUDRUULUULLRRRRLRUUDDUDLDURURLRRRULRDRRUDDRDDRLRRRLRURRRUULULLLUULLLULLUDLRDLDURRURDLDLRDUULDRLLRRLDUDDUULULR";

        lines = Arrays.asList(lineOne, lineTwo, lineThree, lineFour, lineFive);
    }

    public static void main(String[] args) {
        SecondDay secondDay = new SecondDay();

        secondDay.printPasswordNumericPad();

        System.out.println();

        secondDay. printPasswordDiamondPad();
    }

    public void printPasswordDiamondPad() {
        DiamondPad diamondPad = new DiamondPad();

        printPassword(diamondPad);
    }

    public void printPasswordNumericPad() {
        NumericPad numericPad = new NumericPad();

        printPassword(numericPad);
    }

    private void printPassword(Pad pad) {
        State current = pad.getCurrentState();

        for(String line : this.lines) {
            pad.setCurrentState(current);
            current = pad.executeCommands(line);
            System.out.print(current);
        }
    }
}

class Pad {
    protected State one, two, three, four, five, six, seven, eight, nine;
    protected State currentState;

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public State executeCommands(String commands) {
        for(Character c : commands.toCharArray()) {
            this.currentState = this.currentState.executeCommand(c);
        }
        return currentState;
    }

    public State getCurrentState() {
        return currentState;
    }
}

class DiamondPad extends Pad{
    private State a, b, c, d;

    public DiamondPad() {
        this.one = new State.StateBuilder("1").createState();
        this.two = new State.StateBuilder("2").createState();
        this.three = new State.StateBuilder("3").up(one).left(two).createState();
        one.setDown(three);
        two.setRight(three);
        this.four = new State.StateBuilder("4").left(three).createState();
        three.setRight(four);

        this.five = new State.StateBuilder("5").createState();
        this.six = new State.StateBuilder("6").left(five).up(two).createState();
        this.seven = new State.StateBuilder("7").left(six).up(three).createState();
        this.eight = new State.StateBuilder("8").left(seven).up(four).createState();
        this.nine = new State.StateBuilder("9").left(eight).createState();
        two.setDown(six);
        three.setDown(seven);
        four.setDown(eight);
        five.setRight(six);
        six.setRight(seven);
        seven.setRight(eight);
        eight.setRight(nine);

        this.a = new State.StateBuilder("A").up(six).createState();
        this.b = new State.StateBuilder("B").up(seven).left(a).createState();
        this.c = new State.StateBuilder("C").up(eight).left(b).createState();
        a.setRight(b);
        six.setDown(a);
        seven.setDown(b);
        eight.setDown(c);
        b.setRight(c);


        this.d = new State.StateBuilder("D").up(b).createState();
        b.setDown(d);

        this.currentState = five;
    }

}

class NumericPad extends Pad{

    public NumericPad() {
        one = new State.StateBuilder("1").createState();
        three = new State.StateBuilder("3").createState();
        two = new State.StateBuilder("2").left(one).right(three).createState();
        one.setRight(two);
        three.setLeft(two);

        this.seven = new State.StateBuilder("7").createState();
        this.nine = new State.StateBuilder("9").createState();
        this.eight = new State.StateBuilder("8").left(seven).right(nine).createState();
        this.seven.setRight(eight);
        this.nine.setLeft(eight);

        this.four = new State.StateBuilder("4").up(one).down(seven).createState();
        this.five = new State.StateBuilder("5").up(two).down(eight).left(four).createState();
        this.six = new State.StateBuilder("6").up(three).down(nine).left(five).createState();
        this.four.setRight(five);
        five.setRight(six);
        two.setDown(five);
        one.setDown(four);
        three.setDown(six);
        nine.setUp(six);
        eight.setUp(five);

        seven.setUp(four);

        currentState = five;
    }
}

class State {
    private String name;
    private State left, right, up, down;

    private State(String name) {
        this.name = name;
    }

    public void setLeft(State left) {
        this.left = left;
    }

    public void setRight(State right) {
        this.right = right;
    }

    public void setUp(State up) {
        this.up = up;
    }

    public void setDown(State down) {
        this.down = down;
    }

    private State moveLeft() {
        return left;
    }

    private State moveRight() {
        return right;
    }

    private State moveUp() {
        return up;
    }

    private State moveDown() {
        return down;
    }

    @Override
    public String toString() {
        return name;
    }

    public  State executeCommand(Character command) {
        switch(command) {
            case 'L': return this.moveLeft();
            case 'R': return this.moveRight();
            case 'D': return this.moveDown();
            case 'U': return this.moveUp();
        }
        return null;
    }

    public static class StateBuilder {
        private String name;
        private State left, right, up, down;

        public StateBuilder(String name) {
            this.name = name;
        }

        public StateBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StateBuilder left(State left) {
            this.left = left;
            return this;
        }

        public StateBuilder right(State right) {
            this.right = right;
            return this;
        }

        public StateBuilder up(State up) {
            this.up = up;
            return this;
        }
        public StateBuilder down(State down) {
            this.down = down;
            return this;
        }

        public State createState() {
            State answer = new State(name);
            answer.setLeft(this.left != null ?this.left:answer);
            answer.setUp(this.up != null?this.up:answer);
            answer.setRight(this.right != null?this.right:answer);
            answer.setDown(this.down != null?this.down:answer);
            return answer;
        }
    }
}
