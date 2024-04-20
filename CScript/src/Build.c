#include <stdio.h>
#include <stdlib.h>

int main(void) {
    printf("Building Java ...\n");
    system("cd src ; javac com/vojat/Main.java -d ./Build ; cd ..");
    printf("Build complete !\n");
    return 0;
}