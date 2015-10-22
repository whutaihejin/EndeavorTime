#include <iostream>
#include <stdint.h>

int64_t ToInt64(const std::string& x) {
  int flag = 1, index = 0;
  // handle the flag
  if (x[index] == '-') {
    flag = -1;
    index++;
  } else if (x[index] == '+') {
    index++;
  }
  // convert
  int64_t val = 0, base = 10;
  for (; index < x.size(); ++index) {
    if (x[index] == ',') continue;
    val = val * base + x[index] - '0';
  }
  // result should with flag
  val *= flag;
  return val;
}

int main(int argc, char** argv) {
  std::string x, y;
  while(std::cin >> x >> y) {
    int64_t xval = ToInt64(x);
    int64_t yval = ToInt64(y);
    std::cout << xval + yval << std::endl;
  }
  return 0;
}
