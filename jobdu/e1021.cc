#include <iostream>
#include <string.h>

int main(int argc, char** argv) {
  char hash[256];
  std::string key;
  while (getline(std::cin, key)) {
    if (key == "#") {
      return 0;
    }
    // make hash table for search
    memset(hash, 0, 256);
    for (int i = 0; i < key.size(); i++) {
      hash[key[i]] = i + 1;
    }
    // value input
    std::string value;
    getline(std::cin, value);

    // statistic
    int count[5] = {0};
    char *p = &value[0], *limit = p + value.size();
    for (; p < limit; ++p) {
      if (hash[*p] != 0) {
        count[hash[*p] - 1]++;
      } 
    }
    // output
    for (int i = 0; i < key.size(); i++) {
      std::cout << key[i] << " " << count[i] << std::endl;
    }
  }
  return 0;
}
