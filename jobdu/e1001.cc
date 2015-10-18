#include <iostream>

int main(int argc, char** argv) {
  int M = 0, N = 0;
  for (;;) {
    std::cin >> M;
    if (M == 0) return 0;
    std::cin >> N;
    int** matrix = new int*[M];
    // initialize
    for (int i = 0; i < M; ++i) {
      matrix[i] = new int[N];
      for (int j = 0; j < N; ++j) {
        std::cin >> matrix[i][j];
      }
    }
    // add
    int val = 0;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        std::cin >> val;
        matrix[i][j] += val;
      }
    }
    // count
    int count = 0;
    // row count
    for (int i = 0; i < M; ++i) {
      bool flag = true;
      for (int j = 0; j < N; ++j) {
        if (matrix[i][j] != 0) flag = false;
      }
      if (flag) count++;
    }
    // column count
    for (int j = 0; j < N; ++j) {
      bool flag = true;
      for (int i = 0; i < M; ++i) {
        if (matrix[i][j] != 0) flag = false;
      }
      if (flag) count++; 
    }
    // out put 
    std::cout << count << std::endl;
  }
}
