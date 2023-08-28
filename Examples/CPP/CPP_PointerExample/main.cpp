#include <iostream>

int main() {
    int* x = (int*) malloc(sizeof(int));
    //we don't use malloc for objects in C++

    *x = 1000;
    int* y = x;   // x and y are both pointing to the same block of memory

    (*y)++;
    printf("%d \n", *x);  //1001
    //free(x);
    //free(y); error? you want to free the same memory block (you already did that with the previous line)
}


