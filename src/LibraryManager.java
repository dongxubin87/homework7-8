import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private final List<LibraryItem> libraryItems = new ArrayList<>();
    private final List<IMember> members = new ArrayList<>();

    public void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
    }

    public void addMember(IMember member) {
        members.add(member);
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public List<IMember> getMembers() {
        return members;
    }
}
