#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>

char prefix[] = "(CRunner)";
char errCheck = 0;

int main(int argc, char* argv[]) {

    // If windows
    #ifdef _WIN32
        if (argc > 1 && strcmp(argv[1], "-B") == 0) {

            printf("%s Building Java ...\n", prefix);
            errCheck = system("cd src & javac com/vojat/Main.java -d ./Build & cd ..");

            // Check if build was successful
            if (errCheck == 0) printf("%s Building java complete !\n", prefix);
            else{
                printf("%s Java build failed!\n Running last working build...\n", prefix);
                sleep(500);
            }
        } else if (strcmp(argv[1], "?") == 0) {
            printf("Arguments:\n\t-B      ---  Build the source code and then run it.\n\t?       ---  Show this screen.\n\t<None>  ---  Run the program using the last build, works just if there's some previous build.\n");
            return 0;
        }

        printf("%s Running Java ...\n\tJAVA Console:\n", prefix);
        errCheck = system("cd src/Build & java com/vojat/Main & cd ../../");
        if (errCheck == 0) printf("%s Program stopped successfully!\n", prefix);
        else printf("%s Running program failed!\n", prefix);

    #elif __linux__
        if (argc == 1);
        else if (strcmp(argv[1], "-B") == 0) {

            printf("%s Building Java ...\n", prefix);
            errCheck = system("cd src ; javac com/vojat/Main.java -d ./Build ; cd ..");
            
            // Check if build was successful
            if (errCheck == 0) printf("%s Building java complete !\n", prefix);
            else{
                printf("%s Java build failed!\n Running last working build...\n", prefix);
                sleep(500);
            }
        } else if (strcmp(argv[1], "?") == 0) {
            printf("Arguments:\n\t-B      ---  Build the source code and then run it.\n\t?       ---  Show this screen.\n\t<None>  ---  Run the program using the last build, works just if there's some previous build.\n");
            return 0;
        }
        printf("%s Running Java ...\n", prefix);
        errCheck = system("cd src/Build ; java com/vojat/Main ; cd ../../");
        if (errCheck == 0) printf("%s\nProgram stopped successfully!\n", prefix);
        else printf("%s Running program failed!\n", prefix);

    #endif
        return 0;
}