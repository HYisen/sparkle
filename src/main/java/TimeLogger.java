import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimeLogger {
    private Instant first;
    private Instant last;
    private String id;
    private Path path;

    public TimeLogger(String id) {
        this.id = id;
        this.path = Paths.get("output", id + "_log");
    }

    public void start() throws IOException {
        first = Instant.now();
        last = first;
        Files.createFile(path);
        log("start");
    }

    public void log(String msg) throws IOException {
        Instant now = Instant.now();
        String content = String.format("%s : +%10d,%10d ms %s\n", toString(),
                last.until(now, ChronoUnit.MILLIS),
                first.until(now, ChronoUnit.MILLIS),
                msg);
        last = now;
        System.out.print(content);
        Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public String toString() {
        return "Stopwatch " + id;
    }
}
