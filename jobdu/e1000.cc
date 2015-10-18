#include <iostream>

int main(int argc, char** argv) {
  int x = 0, y = 0;
  while (std::cin >> x >> y) {
    std::cout << (x + y) << std::endl;
  }
}
