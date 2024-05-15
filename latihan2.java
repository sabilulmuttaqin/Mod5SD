class Node2 {
    int data;
    Node2 left;
    Node2 right;

    public Node2(int data){
        this.data = data;
    }
}

class BinaryTree2 {
    public Node2 root;

    public void NewNode(int data){
        root = NewNode(root, new Node2(data));
    }

    private Node2 NewNode(Node2 root, Node2 newData){
        if (root == null){
            root = newData;
            return root;
        }

        if (newData.data < root.data){
            root.left = NewNode(root.left, newData);
        } else {
            root.right = NewNode(root.right, newData);
        }

        return root;
    }

    public void inOrder(Node2 node){
        if (node != null){
            inOrder(node.left);
            System.out.println(node.data + " ");
            inOrder(node.right);
        }
    }

    public void preOrder(Node2 node){
        if (node != null){
            System.out.println(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void postOrder(Node2 node){
        if (node != null){
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.data + " ");
        }
    }

    public static void main(String[] args) {
        BinaryTree2 tree = new BinaryTree2();

        tree.NewNode(20);
        tree.NewNode(2);
        tree.NewNode(25);
        tree.NewNode(37);
        tree.NewNode(16);

        System.out.println("\nPre Order: ");
        tree.preOrder(tree.root);
        System.out.println("\nIn Order: ");
        tree.inOrder(tree.root);
        System.out.println("\nPost Order: ");
        tree.postOrder(tree.root);

    }
}