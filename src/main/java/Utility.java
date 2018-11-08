import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

public class Utility {
    public static void output(String name, String... lines) throws IOException {
        Path path = Paths.get("output", name);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        for (String line : lines) {
            line += "\n";
            System.out.print(line);
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
        }
    }

    //copied solution from https://stackoverflow.com/questions/19433135/notserializableexception-when-sorting-in-spark
    public interface SerializableComparator<T> extends Comparator<T>, Serializable {
        static <T> SerializableComparator<T> serialize(SerializableComparator<T> comparator) {
            return comparator;
        }
    }
}
