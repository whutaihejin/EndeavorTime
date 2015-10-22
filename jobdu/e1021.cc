#include <iostream>

int index(std::string& key, char c) {
  for (int i = 0; i < key.size(); i++) {
    if (key[i] == c) {
      return i;
    }
  }
  return -1;
}

int main(int argc, char** argv) {
  std::string key;
  while (getline(std::cin, key)) {
    if (key == "#") {
      return 0;
    }
    // value input
    std::string value;
    getline(std::cin, value);

    // statistic
    int count[5] = {0};
    char *p = &value[0], *limit = p + value.size();
    for (; p < limit; ++p) {
      int idx = index(key, *p);
      if (idx != -1) {
        count[idx]++;
      } 
    }
    // output
    for (int i = 0; i < key.size(); i++) {
      std::cout << key[i] << " " << count[i] << std::endl;
    }
  }
  return 0;
}
