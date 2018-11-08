import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Utility {
    public static void output(String name, String... lines) throws IOException {
        output(name, true, lines);
    }

    public static void output(String name, boolean writeConsole, String... lines) throws IOException {
        Path path = Paths.get("output", name);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        for (String line : lines) {
            line += "\n";
            if (writeConsole) {
                System.out.print(line);
            }
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
        }
    }

    //copied solution from https://stackoverflow.com/questions/19433135/notserializableexception-when-sorting-in-spark
    public interface SerializableComparator<T> extends Comparator<T>, Serializable {
        static <T> SerializableComparator<T> serialize(SerializableComparator<T> comparator) {
            return comparator;
        }
    }

    public static void roll(int rounds) throws IOException {
        TimeLogger tl = new TimeLogger("dice");
        tl.start();
        for (int i = 0; i < rounds; i++) {
            Random random = new Random(17);
            ConcurrentHashMap<Integer, Long> data = new ConcurrentHashMap<>(60);
            LongStream.range(0, 43545352)
                    .parallel()
                    .mapToInt(v -> random.nextInt(60))
                    .forEach(v -> data.put(v, data.getOrDefault(v, 0L) + 1));
            tl.log("loop " + i + " completed");
            Utility.output("result",
                    data.values().stream().map(Object::toString).collect(Collectors.joining("\n")));
        }

    }

    public static void main(String[] args) throws IOException {
        roll(1000);
    }
}
