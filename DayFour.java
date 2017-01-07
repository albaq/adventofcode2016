import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alvaro on 1/7/17.
 */
public class DayFour {
    private String line;
    private Pattern pattern;

    private Pattern codeExtracterPattern;


    public static void main(String[] args) {
        List<String> lines = DayFour.readLinesFromFile("DayFourInput.txt");
        int count = lines
                .stream()
                .map(line-> new DayFour(line))
                .filter(elem->elem.getGivenChecksum().get().equals(elem.computeChecksum()))
                .mapToInt(elem->elem.extractCode().get())
                .sum();
        System.out.println(count);

        lines
                .stream()
                .map(line-> new DayFour(line))
                .filter(elem->elem.getGivenChecksum().get().equals(elem.computeChecksum()))
                .map(elem->  {
                    String lineWithoutChecksum = elem.removeGivenChecksum();
                    Integer code = elem.extractCode().get();
                    int indexLastDash = lineWithoutChecksum.lastIndexOf("-");
                    String lineWithoutCode = lineWithoutChecksum.substring(0, indexLastDash);
                    return new LineCode(lineWithoutCode, code);
                })
                .filter(elem->elem.decrypt().equals("northpole object storage"))
                .map(elem->String.format("%s -> %d", elem.decrypt(), elem.code))
                .forEach(System.out::println);

    }

    /*
    @Override
    public String toString() {
        return String.format("%s -> %s -> %s -> %d", this.line, this.getGivenChecksum().get().equals(this.computeChecksum()), this.computeChecksum(), this.extractCode().get());
    }
    */

    private static List<String> readLinesFromFile(String name) {
        Path path = Paths.get(name);
        Stream<String> linesFromFile = Stream.empty();
        try {
            linesFromFile = Files.lines(path);
        } catch (IOException e) {}
        return linesFromFile.collect(Collectors.toList());
    }

    public Optional<Integer> extractCode() {
        Matcher m = codeExtracterPattern.matcher(line);
        if(m.find()) {
            return Optional.of(Integer.valueOf(m.group()));
        }
        return Optional.empty();

    }


    public DayFour (String line) {
        this.line = line;
        pattern = Pattern.compile("\\[(.*?)\\]");
        codeExtracterPattern = Pattern.compile("\\d+");
    }

    public String removeDashes(String data) {
        return data.replaceAll("\\-", "");
    }

    public Optional<String> getGivenChecksum() {

        Matcher m = pattern.matcher(this.line);

        if(m.find()) {
            return Optional.of(m.group(1));
        }
        return Optional.empty();
    }

    public String computeChecksum() {
        return String.join("", removeDashes(this.removeGivenChecksum())
                .chars()
                .mapToObj(elem->(char)elem)
                .filter(elem->!Character.isDigit(elem))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed().thenComparing(other->other.getKey()))//Comparator.comparing(Map.Entry::getValue).reversed())//.thenComparing(entry->entry.getKey())).reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .map(elem->""+elem)
                .collect(Collectors.toList()));
    }

    public String removeGivenChecksum() {
        String response = line;
        Optional<String> checksum = getGivenChecksum();

        if(checksum.isPresent()) {
            response = response.replaceFirst(checksum.get(), "");
            response = response.replaceFirst("\\[", "");
            response = response.replaceFirst("\\]", "");
        }
        return response;
    }
}

class LineCode {
    String line;
    int code ;

    public LineCode(String line, int code) {
        this.line = line;
        this.code = code;
    }

    public String decrypt() {
        int offset = code % 26;
        return String.join("", line
                .chars()
                .mapToObj(elem->(char)elem)
                .map(elem->(elem.equals('-'))?' ':(((elem -'a'+offset) % 26)+'a'))
                .map(elem->(char)elem.intValue())
                .map(elem-> ""+elem)
                .collect(Collectors.toList()));
    }
}
