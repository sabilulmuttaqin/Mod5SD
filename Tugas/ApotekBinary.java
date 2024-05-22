package Tugas;

import java.util.InputMismatchException;
import java.util.Scanner;

class Node {
    int kodeObat;
    String namaObat;
    Node left, right;

    public Node(int kodeObat, String namaObat) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.left = null;
        this.right = null;
    }
}

public class ApotekBinary {
    public Node root;

    public ApotekBinary() {
        root = null;
    }

    // Method khusus untuk menambahkan root
    public void addRoot(int kodeObat, String namaObat) {
        if (root != null) {
            System.out.println("Root sudah ada.");
            return;
        }
        root = new Node(kodeObat, namaObat);
    }

    // Method untuk menambahkan node baru dengan parent
    public void add(int parentKodeObat, int kodeObat, String namaObat, boolean isLeft) {
        if (findDuplicateKode(root, kodeObat)) {
            System.out.println("Kode obat " + kodeObat + " sudah ada.");
            return;
        }

        Node parent = findNode(root, parentKodeObat);
        if (parent == null) {
            System.out.println("Parent dengan kode obat " + parentKodeObat + " tidak ditemukan.");
            return;
        }

        if (isLeft) {
            if (parent.left != null) {
                System.out.println("Node kiri dari parent sudah terisi.");
                return;
            }
            parent.left = new Node(kodeObat, namaObat);
        } else {
            if (parent.right != null) {
                System.out.println("Node kanan dari parent sudah terisi.");
                return;
            }
            parent.right = new Node(kodeObat, namaObat);
        }
    }

    private Node findNode(Node node, int kodeObat) {
        if (node == null || node.kodeObat == kodeObat) {
            return node;
        }

        Node left = findNode(node.left, kodeObat);
        if (left != null) {
            return left;
        }

        return findNode(node.right, kodeObat);
    }

    // Method untuk mencari duplikasi kode obat
    private boolean findDuplicateKode(Node node, int kodeObat) {
        if (node == null) {
            return false;
        }
        if (node.kodeObat == kodeObat) {
            return true;
        }
        return findDuplicateKode(node.left, kodeObat) || findDuplicateKode(node.right, kodeObat);
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.kodeObat + " - " + node.namaObat);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.kodeObat + " - " + node.namaObat);
            inOrder(node.right);
        }
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.kodeObat + " - " + node.namaObat);
        }
    }

    public void printTreeStructure(Node node, int level) {
        if (node == null) {
            return;
        }
        printTreeStructure(node.right, level + 1);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t");
            }
            System.out.println("|---" + node.kodeObat);
        } else {
            System.out.println(node.kodeObat);
        }
        printTreeStructure(node.left, level + 1);

    }

    private void addNodesFromUserInput(Scanner scanner) {
        System.out.print("Masukkan jumlah obat yang ingin ditambahkan: ");
        int jumlahObat = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < jumlahObat; i++) {
            int kodeObat;
            String namaObat;
            int parentKodeObat;
            boolean isLeft;

            while (true) {
                try {
                    System.out.print("Masukkan kode obat ke " + (i + 1) + ": ");
                    kodeObat = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (!findDuplicateKode(root, kodeObat)) {
                        break;
                    }
                    System.out.println("Kode obat sudah ada. Silakan masukkan kode obat yang berbeda.");
                } catch (InputMismatchException e) {
                    System.out.println("Kode hanya bisa berupa angka. Silakan masukkan kembali.");
                    scanner.next(); // Clear the invalid input
                }
            }

            System.out.print("Masukkan nama obat ke " + (i + 1) + ": ");
            namaObat = scanner.nextLine();

            while (true) {
                try {
                    System.out.print("Masukkan kode parent obat untuk obat ke " + (i + 1) + ": ");
                    parentKodeObat = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (findNode(root, parentKodeObat) != null) {
                        break;
                    }
                    System.out.println("Parent dengan kode obat " + parentKodeObat + " tidak ditemukan.");
                } catch (InputMismatchException e) {
                    System.out.println("Kode hanya bisa berupa angka. Silakan masukkan kembali.");
                    scanner.next(); // Clear the invalid input
                }
            }

            while (true) {
                System.out.print("Node ini akan berada di sebelah kiri dari parent? (y/n): ");
                String leftInput = scanner.nextLine();
                isLeft = leftInput.equalsIgnoreCase("y");

                Node parentNode = findNode(root, parentKodeObat);
                if ((isLeft && parentNode.left == null)
                        || (!isLeft && parentNode.right == null)) {
                    break;
                } else {
                    System.out.println("Posisi ini sudah terisi. Silakan pilih posisi lain.");
                }
            }

            add(parentKodeObat, kodeObat, namaObat, isLeft);
        }
    }

    public static void main(String[] args) {
        ApotekBinary tree = new ApotekBinary();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Masukkan kode obat untuk root: ");
                int kodeObatRoot = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Masukkan nama obat untuk root: ");
                String namaObatRoot = scanner.nextLine();
                tree.addRoot(kodeObatRoot, namaObatRoot);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Kode hanya bisa berupa angka. Silakan masukkan kembali.");
                scanner.next(); // Clear the invalid input
            }
        }

        tree.addNodesFromUserInput(scanner);

        System.out.println("PreOrder traversal: ");
        tree.preOrder(tree.root);
        System.out.println("InOrder traversal: ");
        tree.inOrder(tree.root);
        System.out.println("PostOrder traversal: ");
        tree.postOrder(tree.root);

        // Print the structure of the binary tree
        System.out.println("\n\nStruktur Binary Tree:");
        tree.printTreeStructure(tree.root, 0);

        scanner.close();
    }
}
