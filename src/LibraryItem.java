import java.util.Date;
import java.util.UUID;

public abstract class LibraryItem {
    private final String id;
    private boolean availability = true;
    private Date dueToDate;
    private UUID borrowerMemberId;

    public LibraryItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return availability;
    }

    public UUID getBorrowerMemberId() {
        return borrowerMemberId;
    }

    public abstract String getType();

    public abstract String getInfo();

    public void borrowItem(UUID memberId, Date dueToDate) {
        this.availability = false;
        this.dueToDate = dueToDate;
        this.borrowerMemberId = memberId;
    }

    public void returnItem() {
        this.availability = true;
        this.dueToDate = null;
        this.borrowerMemberId = null;
    }
}
