#include <iostream>

int main(int argc, char** argv) {
  int max = 0;
  while (std::cin >> max) {
    int x = 0;
    for (int i = 0; i < 9; i++) {
      std::cin >> x;
      if (x > max) {
        max = x;
      }
    }
    std::cout << "max=" << max << std::endl;
  } 
  return 0;
}
