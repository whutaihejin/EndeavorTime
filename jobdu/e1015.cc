#include <iostream>

bool LastK(int x, int y, int k) {
  for (int i = 0; i < k; i++) {
    if (x % 10 != y % 10){
      return false;
    }
    x /= 10;
    y /= 10;
  }
  return true;
}

int main(int argc, char** argv) {
  int x = 0, y = 0, k = 0;
  while (std::cin >> x >> y >> k) {
    if (x == 0 && y == 0){
      break;
    }
    if (LastK(x, y, k)) {
      std::cout << -1 << std::endl;
    } else {
      std::cout << x + y << std::endl;
    }
    // std::cout << x << " => " << y << " => " << k << std::endl;
  }
  return 0;
}
