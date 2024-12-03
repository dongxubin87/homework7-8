public class Magazine extends LibraryItem {
    private final String issue;

    public Magazine(String id, String issue) {
        super(id);
        this.issue = issue;
    }

    @Override
    public String getType() {
        return "Magazine";
    }

    @Override
    public String getInfo() {
        return issue;
    }
}
