import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Record implements Serializable, Comparable<Record> {
    private LocalDateTime time;
    private String uid;
    private String key;
    private int position;
    private int frequent;
    private String url;

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Record o) {
        return time.compareTo(o.time);
    }

    public Record() {
    }

    public static Optional<Record> genItemOptional(String line) {
        String[] words = line.split("\t");

        Record rtn = new Record();

        try {
            rtn.time = LocalDateTime.parse(words[0], DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            rtn.uid = words[1];
            rtn.key = words[2];
            rtn.position = Integer.valueOf(words[3]);
            rtn.frequent = Integer.valueOf(words[4]);
            rtn.url = words[5];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("fail to parse line : " + line);
            rtn = null;
        }

        return Optional.ofNullable(rtn);
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
