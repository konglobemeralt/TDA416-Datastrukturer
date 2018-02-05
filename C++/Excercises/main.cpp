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