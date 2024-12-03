import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.UUID;

public class LibraryGUI {
    private final LibraryManager libraryManager = new LibraryManager();
    private final JFrame frame = new JFrame("Library Management System");
    private final JTable itemTable;
    private final JTable memberTable;

    public LibraryGUI() {
        libraryManager.addLibraryItem(new Book("B001", "The Great Gatsby"));
        libraryManager.addLibraryItem(new Magazine("M001", "Tech Monthly"));
        libraryManager.addMember(new FacultyMember("Alice"));
        libraryManager.addMember(new StudentMember("Bob"));
        libraryManager.addMember(new GuestMember("Charlie"));

        // Initialization code here
        String[] itemColumns = {"ID", "Type", "Info", "Available"};
        itemTable = new JTable(new DefaultTableModel(itemColumns, 0));
        updateItemTable();

        String[] memberColumns = {"ID", "Name", "Type"};
        memberTable = new JTable(new DefaultTableModel(memberColumns, 0));
        updateMemberTable();

        JPanel buttonPanel = new JPanel();
        JButton addItemButton = new JButton("Add Item");
        JButton borrowItemButton = new JButton("Borrow Item");
        JButton returnItemButton = new JButton("Return Item");

        addItemButton.addActionListener(e -> addItem());
        borrowItemButton.addActionListener(e -> borrowItem());
        returnItemButton.addActionListener(e -> returnItem());

        buttonPanel.add(addItemButton);
        buttonPanel.add(borrowItemButton);
        buttonPanel.add(returnItemButton);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JScrollPane(itemTable));
        frame.add(new JScrollPane(memberTable));
        frame.add(buttonPanel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void updateItemTable() {
        DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
        model.setRowCount(0);
        for (LibraryItem item : libraryManager.getLibraryItems()) {
            model.addRow(new Object[]{
                    item.getId(),
                    item.getType(),
                    item.getInfo(),
                    item.isAvailable() ? "Yes" : "No"
            });
        }
    }
    private void updateMemberTable() {
        DefaultTableModel model = (DefaultTableModel) memberTable.getModel();
        model.setRowCount(0);
        for (IMember member : libraryManager.getMembers()) {
            model.addRow(new Object[]{
                    member.getId(),
                    member.getInfo(),
                    member.getClass().getSimpleName()
            });
        }
    }
    private void addItem() {

        String[] options = {"Book", "Magazine"};
        String choice = (String) JOptionPane.showInputDialog(
                frame,
                "Select item type:",
                "Add Item",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null) return;

        String id = JOptionPane.showInputDialog(frame, "Enter Item ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Item ID cannot be empty!");
            return;
        }

        String info;
        if ("Book".equals(choice)) {
            info = JOptionPane.showInputDialog(frame, "Enter Book Title:");
            if (info != null && !info.trim().isEmpty()) {
                libraryManager.addLibraryItem(new Book(id, info));
                updateItemTable();
                JOptionPane.showMessageDialog(frame, "Book added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Book title cannot be empty!");
            }
        } else if ("Magazine".equals(choice)) {
            info = JOptionPane.showInputDialog(frame, "Enter Magazine Issue:");
            if (info != null && !info.trim().isEmpty()) {
                libraryManager.addLibraryItem(new Magazine(id, info));
                updateItemTable();
                JOptionPane.showMessageDialog(frame, "Magazine added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Magazine issue cannot be empty!");
            }
        }
    }
    private void borrowItem() {
        int selectedItemRow = itemTable.getSelectedRow();
        int selectedMemberRow = memberTable.getSelectedRow();

        if (selectedItemRow == -1 || selectedMemberRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item and a member.");
            return;
        }

        String itemId = (String) itemTable.getValueAt(selectedItemRow, 0);
        UUID memberId = (UUID) memberTable.getValueAt(selectedMemberRow, 0);

        LibraryItem item = libraryManager.getLibraryItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElse(null);

        IMember member = libraryManager.getMembers().stream()
                .filter(m -> m.getId().equals(memberId))
                .findFirst()
                .orElse(null);

        if (item == null || member == null) {
            JOptionPane.showMessageDialog(frame, "Invalid selection.");
            return;
        }

        if (!item.isAvailable()) {
            JOptionPane.showMessageDialog(frame, "This item is not available for borrowing.");
            return;
        }

        boolean success = member.borrowItem(item);
        if (success) {
            updateItemTable();
            JOptionPane.showMessageDialog(frame, "Item borrowed successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Member cannot borrow more items.");
        }
    }
    private void returnItem() {
        int selectedItemRow = itemTable.getSelectedRow();

        if (selectedItemRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to return.");
            return;
        }

        String itemId = (String) itemTable.getValueAt(selectedItemRow, 0);

        LibraryItem item = libraryManager.getLibraryItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            JOptionPane.showMessageDialog(frame, "Invalid item selection.");
            return;
        }

        if (item.isAvailable()) {
            JOptionPane.showMessageDialog(frame, "This item is already available in the library.");
            return;
        }

        item.returnItem();
        updateItemTable();
        JOptionPane.showMessageDialog(frame, "Item returned successfully!");
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
