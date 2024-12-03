public class Book extends LibraryItem {
    private final String title;

    public Book(String id, String title) {
        super(id);
        this.title = title;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String getInfo() {
        return title;
    }
}
