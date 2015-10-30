#include <iostream>
#include <assert.h>

int main(int argc, char** argv) {
  std::string line;
  while (std::cin >> line) {
    if (line == "E") {
      break;
    }
    // cope with statistic
    int statistic[3] = {0};
    for (int i = 0; i < line.size(); ++i) {
      if (line[i] == 'Z') statistic[0]++;
      else if (line[i] == 'O') statistic[1]++;
      else if (line[i] == 'J') statistic[2]++;
    }
    // output
    int i = 0;
    for (; i < line.size();) {
      if (statistic[0] != 0) {
        std::cout << 'Z';
	statistic[0]--;
	i++;
      }
      if (statistic[1] != 0) {
        std::cout << 'O';
	statistic[1]--;
	i++;
      }
      if (statistic[2] != 0) {
        std::cout << 'J';
	statistic[2]--;
	i++;
      }
    }
    std::cout << std::endl;
    assert(i == line.size());
  }
  return 0;
}
