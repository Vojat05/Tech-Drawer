#include <stdlib.h>
int os = 0;
int main(void) {

    // If windows
    #ifdef _WIN32
        os = 1;

    #elif __linux__
        os = 2;

    #endif
        os = 0;

    // Download from github
    if (os == 1) {
        system("git pull origin main");
    } else if (os == 2) {
        system("git pull origin main");
    }

    // Build java
    if (os == 1) {
        system("./Build.exe");
    } else if (os == 2) {
        system("wine ./Build.exe");
    }

    return 0;
}