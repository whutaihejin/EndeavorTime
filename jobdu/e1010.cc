#include <iostream>
#include <map>

std::string replace(std::string line, std::map<std::string, std::string>& emap) {
  std::map<std::string, std::string>::iterator iter = emap.begin();
  std::string::size_type index;
  for (; iter != emap.end(); ++iter) {
    while((index = line.find(iter->first)) != std::string::npos) {
      line = line.replace(index, iter->first.size(), iter->second);
    }
  }
  return line;
}

int main(int argc, char** argv) {
  // initialize the emap
  static std::map<std::string, std::string> emap;
  emap.insert(std::make_pair("zero", "0"));
  emap.insert(std::make_pair("one", "1"));
  emap.insert(std::make_pair("two", "2"));
  emap.insert(std::make_pair("three", "3"));
  emap.insert(std::make_pair("four", "4"));
  emap.insert(std::make_pair("five", "5"));
  emap.insert(std::make_pair("six", "6"));
  emap.insert(std::make_pair("seven", "7"));
  emap.insert(std::make_pair("eight", "8"));
  emap.insert(std::make_pair("nine", "9"));
  // cope with input 
  std::string line;
  while (getline(std::cin, line)) {
    if (line == "zero + zero =") {
      break;
    }
    int sum = 0;
    int val = 0, base = 10;
    line = replace(line, emap);
    for (int i = 0; i < line.size(); i++) {
      if (line[i] == ' ' || line[i] == '=') {
        continue;
      }
      if (line[i] == '+') {
        sum += val;
        val = 0;
        continue;
      }
      val = val * base + line[i] - '0';
    }
    sum += val;
    std::cout << sum << std::endl;
  }
  return 0;
}
