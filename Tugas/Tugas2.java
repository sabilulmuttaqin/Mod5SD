package Tugas;
import java.util.InputMismatchException;
import java.util.Scanner;

class Book {
    int isbn;
    String title;

    public Book(int isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String toString() {
        return "ISBN: " + isbn + " - " + title;
    }
}

class NodeBook {
    Book book;
    NodeBook left, right;

    public NodeBook(Book book) {
        this.book = book;
        left = null;
        right = null;
    }
}

class BookInventory {
    NodeBook root;

    public BookInventory() {
        root = null;
    }

    public void addBook(Book book) {
        if (isDuplicateISBN(root, book.isbn)) {
            System.out.println("ISBN " + book.isbn + " sudah ada. Buku tidak bisa ditambahkan");
            return;
        }
        root = insertBook(root, book);
    }

    private NodeBook insertBook(NodeBook root, Book book) {
        if (root == null) {
            root = new NodeBook(book);
            return root;
        }

        if (book.isbn < root.book.isbn)
            root.left = insertBook(root.left, book);
        else if (book.isbn > root.book.isbn)
            root.right = insertBook(root.right, book);

        return root;
    }

    private boolean isDuplicateISBN(NodeBook root, int isbn) {
        if (root == null) {
            return false;
        }

        if (root.book.isbn == isbn) {
            return true;
        }

        if (isbn < root.book.isbn) {
            return isDuplicateISBN(root.left, isbn);
        } else {
            return isDuplicateISBN(root.right, isbn);
        }
    }

    public Book findBook(int isbn) {
        NodeBook node = findBook(root, isbn);
        
        if (node != null) {
            return node.book;
        } 
        else {
            return null;
        }
    }
    

    private NodeBook findBook(NodeBook root, int isbn) {
        if (root == null || root.book.isbn == isbn)
            return root;

        if (root.book.isbn < isbn)
            return findBook(root.right, isbn);

        return findBook(root.left, isbn);
    }

    public void printInventoryPreOrder() {
        printPreOrder(root);
    }

    private void printPreOrder(NodeBook root) {
        if (root != null) {
            System.out.println(root.book);
            printPreOrder(root.left);
            printPreOrder(root.right);
        }
    }

    public void printInventoryInOrder() {
        printInOrder(root);
    }

    private void printInOrder(NodeBook root) {
        if (root != null) {
            printInOrder(root.left);
            System.out.println(root.book);
            printInOrder(root.right);
        }
    }

    public void printInventoryPostOrder() {
        printPostOrder(root);
    }

    private void printPostOrder(NodeBook root) {
        if (root != null) {
            printPostOrder(root.left);
            printPostOrder(root.right);
            System.out.println(root.book);
        }
    }
}

public class Tugas2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookInventory inventory = new BookInventory();

        int choice = 0;
        while (choice != 6) { 
            System.out.println("\nMenu:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Temukan Buku berdaasarkan ISBN");
            System.out.println("3. Tampilkan inventori PreOrder");
            System.out.println("4. Tampilkan inventori InOrder");
            System.out.println("5. Tampilkan inventori PostOrder");
            System.out.println("6. Exit");
            System.out.print("Masukkan pilihan: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input tidak tepat. Isikan angka 1-6.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Tambah buku:");
                    int isbn = 0;
                    while (true) {
                        System.out.print("Masukkan ISBN (numerik): ");
                        try {
                            isbn = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Input tidak tepat. Masukkan angka numerik.");
                            scanner.next(); // Clear the invalid input
                        }
                    }
                    System.out.print("Masukkan judul: ");
                    String title = scanner.nextLine();
                    inventory.addBook(new Book(isbn, title));
                    break;
                case 2:
                    System.out.println("\n Temukan buku:");
                    System.out.print("Masukkan ISBN: ");
                    int searchIsbn = scanner.nextInt();
                    Book foundBook = inventory.findBook(searchIsbn);
                    if (foundBook != null) {
                        System.out.println("Buku ditemukan: " + foundBook);
                    } else {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.println("\nInventori PreOrder:");
                    inventory.printInventoryPreOrder();
                    break;
                case 4:
                    System.out.println("\nInventori InOrder:");
                    inventory.printInventoryInOrder();
                    break;
                case 5:
                    System.out.println("\nInventori PostOrder:");
                    inventory.printInventoryPostOrder();
                    break;
                case 6:
                    System.out.println("\nKeluar...");
                    break;
                default:
                    System.out.println("Input tidak benar. Masukkan dengan pilihan dengan benar");
            }
        }
        scanner.close();
    }
}
