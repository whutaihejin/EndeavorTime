#include <iostream>
#include <vector>
#include <algorithm>

bool compare(long x, long y) {
  return (x < y);
}

void parse(std::vector<long>& vec, std::string& line) {
  char* p = &line[0], *limit = p + line.size();
  long val = 0, base = 10;
  for (; p < limit; ++p) {
    if (*p == ' ') {
      vec.push_back(val);
      val = 0;
    } else {
      val = val * base + *p - '0';
    }
  }
  vec.push_back(val);
}

int main(int argc, char** argv) {
  std::vector<long> vec;
  std::string line;
  while (getline(std::cin, line)) {
    parse(vec, line);
    getline(std::cin, line);
    parse(vec, line);
    std::sort(vec.begin(), vec.end(), compare);
    std::cout << vec[(vec.size() + 1) / 2] << std::endl;
    vec.clear();
  }
  return 0;
}
