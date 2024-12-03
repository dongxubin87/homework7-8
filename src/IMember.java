import java.util.UUID;

public interface IMember {
    UUID getId();

    String getInfo();

    boolean borrowItem(LibraryItem item);

    void returnItem(LibraryItem item);
}
