#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char prefix[] = "(CRunner)";

int main(int argc, char* argv[]) {

    // If windows
    #ifdef _WIN32
        if (argc > 1 && strcmp(argv[1], "-B") == 0) {

            printf("%s Building Java ...\n", prefix);
            system("cd src ; javac com/vojat/Main.java -d ./Build");
            printf("%s Building java complete !\n", prefix);
        
        }
        printf("%s Running Java ...\n", prefix);
        system("cd src/Build & java com/vojat/Main & cd ../../");
        printf("%s Program stopped !\n", prefix);

    #elif __linux__
        if (argc == 1);
        else if (strcmp(argv[1], "-B") == 0) {

            printf("%s Building Java ...\n", prefix);
            system("cd src ; javac com/vojat/Main.java -d ./Build");
            printf("%s Building java complete !\n", prefix);
        
        } else if (strcmp(argv[1], "?") == 0) {
            printf("Arguments:\n\t-B      ---  Build the source code and then run it.\n\t?       ---  Show this screen.\n\t<None>  ---  Run the program using the last build, works just if there's some previous build.\n");
            return 0;
        }
        printf("%s Running Java ...\n", prefix);
        system("cd src/Build ; java com/vojat/Main ; cd ../../");
        printf("%s Program stopped !\n", prefix);

    #endif
        return 0;
}