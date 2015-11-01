#include <stdio.h>
#include <assert.h>
#include <string.h>

int atoi(const char* sp) {
	assert(sp);
	int len = strlen(sp);
	int val = 0, base = 10;
	for (int i = 0; i < len; i++) {
		val = val * base + sp[i] - '0';
	}
	return val;
}

int main(int argc, char** argv) {
	if (argc < 2) {
		printf("Usage: ./exerciese23 number\n");
		return 0;
	}
	int val = atoi(argv[1]);
	printf("%d = 0x%x\n", val, val);
	return 0;
}
