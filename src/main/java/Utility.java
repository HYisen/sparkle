import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
}
