#include <iostream>

struct node{
    int data;
    struct node* next;
};

int main() {
    struct node* myList = buildOneTwoThree();

    std::cout << getListLength(myList);

    push(&myList, 23);
    push(&myList, 34);

    std::cout << getListLength(myList);
}

int getListLength(struct node* current){
    int count = 0;

    while(current != NULL){
        count++;
        current = current->next;
    }
    return count;
}

void push(struct node** headReference, int data){

    struct node* newNode = malloc(sizeof(node));

    newNode->data = data;
    newNode->next = *headReference;
    *headReference = newNode;
}


//Just a test list "1" -> "2" -> "3"
struct node* buildOneTwoThree() {
    struct node* head = NULL;
    struct node* second = NULL;
    struct node* third = NULL;

    //Allocate three nodes
    head = malloc(sizeof(struct node));
    second = malloc(sizeof(struct node));
    third = malloc(sizeof(struct node));

    //Set up the nodes
    head->data = 1;
    head->next = second;

    second->data = 2;
    second->next = second;

    third->data = 3;
    third->next = NULL;

    //return start of nodes
    return head;
}