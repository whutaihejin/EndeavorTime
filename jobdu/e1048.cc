#include <iostream>

inline void exchange(int& a, int& b) {
  int temp = a;
  a = b;
  b = temp;
}

int main(int argc, char** argv) {
  int a, b, c;
  while (std::cin >> a >> b >> c) {
    // sort a <= b <= c
    if (a > b) {
      exchange(a, b);
    }
    if (b > c ) {
      exchange(b, c);
    }
    if (a > b) {
      exchange(a, b);
    }
    // calculation
    long x = a * a + b * b;
    if (x > c * c) {
      std::cout << "锐角三角形" << std::endl;
    } else if (x == c *c) {
      std::cout << "直角三角形" << std::endl;
    } else {
      std::cout << "钝角三角形" << std::endl;
    }
  }
  return 0;
}
