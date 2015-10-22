#include <iostream>
#include <math.h>

bool prime(int x) {
  // simple situation
  if (x < 2) {
    return false;
  }
  // judge
  for (int i = 2; i <= sqrt(x); i++) {
    if (x % i == 0) {
      return false;
    }
  }
  return true;
}

int main(int argc, char** argv) {
  int x = 0;
  while (std::cin >> x) {
    if (prime(x)) {
      std::cout << "yes" << std::endl;
    } else {
      std::cout << "no" << std::endl;
    }
  } 
  return 0;
}
