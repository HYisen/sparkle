import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item implements Serializable, Comparable<Item> {
    private LocalDateTime time;
    private String uid;
    private String key;
    private int position;
    private int frequent;
    private String url;

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Item o) {
        return time.compareTo(o.time);
    }

    public Item() {
    }

    public Item(String line) {
        load(line);
    }

    public void load(String line) {
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

    public String getUid() {
        return uid;
    }

    public String getKey() {
        return key;
    }

    public int getPosition() {
        return position;
    }

    public int getFrequent() {
        return frequent;
    }

    public String getUrl() {
        return url;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setFrequent(int frequent) {
        this.frequent = frequent;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
