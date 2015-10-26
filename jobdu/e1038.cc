#include <iostream>

int main(int argc, char** argv) {
  long int factorials[10] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};
  for (int i = 1; ; i++) {
    std::cout << fatoric << " ";
    fatoric *= i;
    if (fatoric > 1000000) {
      std::cout << fatoric << " ";
      break;
    }
  }
  return 0;
}
