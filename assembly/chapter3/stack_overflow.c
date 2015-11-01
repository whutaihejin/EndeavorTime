#include <stdio.h>

long int count = 0;

void fun() {
 printf("%ld times\n", ++count);
 fun();
}

int main(int argc, char** argv) {
	fun();
	return 0;
}
