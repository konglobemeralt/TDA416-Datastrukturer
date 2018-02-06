#include <iostream>

struct node{
    int data;
    node *left;
    node *right;
};


/*
 Given a binary tree, return true if a node
 with the target data is found in the tree.
*/
static int lookup(struct node* node, int target) {
    // 1. Base case == empty tree
    // in that case, the target is not found so return false
    if (node == NULL) {
        return(false);
    }
    else {
        // 2. see if found here
        if (target == node->data) return(true);
        else {
            // 3. otherwise recur down the correct subtree
            if (target < node->data) return(lookup(node->left, target));
            else return(lookup(node->right, target));
        } }
}

/*
 Helper function that allocates a new node
 */
struct node* NewNode(int data) {
    struct node* node = new(struct node);
    node->data = data;
    node->left = NULL;
    node->right = NULL;
    return(node);
}

/*
 inserts a new node with the given number in the correct place in the tree.
*/
struct node* insert(struct node* node, int data) {
    // 1. If the tree is empty, return a new, single node
    if (node == NULL) {
        return(NewNode(data));
    }
    else {
        // 2. Otherwise, recur down the tree
        if (data <= node->data) node->left = insert(node->left, data);
        else node->right = insert(node->right, data);
        return(node); // return the node pointer
    }
}

/*
 * Build a basic tree
 */
node* build123(){
    node* rootNode = NewNode(2);
    node* leftNode = NewNode(1);
    node* rightNode = NewNode(3);

    rootNode->left = leftNode;
    rootNode->right = rightNode;

    return rootNode;
}


int maxDepth(struct node* node){
    if(node == NULL){
        return 0;
    }
    else {
        int leftDepth = maxDepth(node->left);
        int rightDepth = maxDepth(node->right);

        if (leftDepth > rightDepth) return(leftDepth+1);
        else return(rightDepth+1);
    }

}

int main() {
    std::cout<< maxDepth(build123());

}
