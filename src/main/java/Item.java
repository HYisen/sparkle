import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item implements Serializable {
    private LocalDateTime time;
    private String uid;
    private String key;
    private int position;
    private int frequent;
    private String url;


    public Item(String line) {
        String[] words = line.split("\t");
        this.time = LocalDateTime.parse(words[0], DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.uid = words[1];
        this.key = words[2];
        this.position = Integer.valueOf(words[3]);
        this.frequent = Integer.valueOf(words[4]);
        this.url = words[5];
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s", time.format(DateTimeFormatter.ofPattern("ddHHmmss")),
                uid, key, position, frequent, url);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
