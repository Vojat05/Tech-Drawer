#include <stdio.h>
#include <stdlib.h>

int main(void) {

    printf("Building Java ...\n");

    // Is windows
    #ifdef _WIN32 
        system("cd src & javac com/vojat/Main.java -d ./Build & cd ..");
        printf("Build complete !\n");
    
    #elif __linux__ 
        system("cd src ; javac com/vojat/Main.java -d ./Build ; cd ..");
        printf("Build complete !\n");
    
    #endif 
        return 0;
    return 0;
}
