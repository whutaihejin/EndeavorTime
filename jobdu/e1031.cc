#include <iostream>
#include <assert.h>

int main(int argc, char** argv) {
  int n = 0;
  while (std::cin >> n) {
    if (n == 0) {
      break;
    }
    assert(n >= 1);
    int count = 0;
    for (; n > 1;) {
      if ((n & 0x01) == 0) {
        n >>= 1;
      } else {
        n = 3 * n + 1;
	n >>= 1;
      }
      count++;
    }
    std::cout << count << std::endl; 
  }
  return 0;
}
