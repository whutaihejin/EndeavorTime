#include <iostream>

int main(int argc, char** argv) {
  int N = 0;
  while (std::cin >> N) {
    // quit 
    if (N == 0) {
      break;
    }
    // input score array
    int* score = new int[N];
    for (int i = 0; i < N; i++) {
      std::cin >> score[i];
    }
    // statistic
    int specify = 0, count = 0;
    std::cin >> specify;
    for (int i = 0; i < N; i++) {
      if (score[i] == specify) {
        count++;
      }
    }
    std::cout << count << std::endl;
    delete[] score;
  }
  return 0;
}
