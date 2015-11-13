#include <stdio.h>
int main(int argc, char** argv) {
	printf("%x\n", 0x503c + 0x8);
	printf("%x\n", 0x503c - 0x40);
	printf("%x\n", 0x503c + 64);
	printf("%x\n", 0x50ea - 0x503c);
	return 0;
}
