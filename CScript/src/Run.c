#include <stdlib.h>

int main(void) {

    // If windows
    #ifdef _WIN32
        system("cd src/Build & java com/vojat/Main & cd ../../");

    #elif __linux__
        system("cd src/Build ; java com/vojat/Main ; cd ../../");

    #endif
        return 0;
}