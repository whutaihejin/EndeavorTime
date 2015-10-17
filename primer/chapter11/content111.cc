#include <iostream>
#include <vector>
#include <algorithm>

int main(int argc, char** argv) {
  const int value = 3;
  std::vector<int> vec;
  for (int i = 0; i < 5; ++i) {
    vec.push_back(i + 1);
  }
  std::vector<int>::const_iterator iter =
      find(vec.begin(), vec.end(), value);
  std::cout << "The value " << value 
            << (iter == vec.end() ? " is not present" : " is present")
            << std::endl;
  return 0;
}
