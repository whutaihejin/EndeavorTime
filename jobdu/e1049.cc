#include <iostream>

int main(int argc, char** argv) {
  std::string line;
  std::string vx;
  char x;
  while (getline(std::cin, line)) {
    getline(std::cin, vx);
    x = vx[0];
    char *p = &line[0], *limit = p + line.size();
    char* arr = new char[line.size()];
    int k = 0;
    for (; p < limit; ++p) {
      if (*p != x) {
        arr[k++] = *p;
      }
    }
    std::string r(arr, k);
    std::cout << r << std::endl;
    delete[] arr;
  }
  return 0;
}
