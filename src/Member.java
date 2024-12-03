import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Member implements IMember {
    private final UUID id = UUID.randomUUID();
    private final String name;
    private final List<LibraryItem> borrowedItems = new ArrayList<>();
    private final int borrowingLimit;

    public Member(String name, int borrowingLimit) {
        this.name = name;
        this.borrowingLimit = borrowingLimit;
    }

    public UUID getId() {
        return id;
    }

    public String getInfo() {
        return name + " (" + getClass().getSimpleName() + ")";
    }

    @Override
    public boolean borrowItem(LibraryItem item) {
        if (borrowedItems.size() < borrowingLimit && item.isAvailable()) {
            item.borrowItem(id, null);
            borrowedItems.add(item);
            return true;
        }
        return false;
    }

    @Override
    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}
