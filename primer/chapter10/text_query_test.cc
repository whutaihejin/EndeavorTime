#include <iostream>
#include <fstream>
#include "text_query.h"

void PrintResult(std::set<TextQuery::index_t>, std::string&, TextQuery&);

int main(int argc, char** argv) {
  
  std::ifstream is;
  if (argc < 2) {
    std::cerr << "No input file!" << std::endl;
    return -1;
  }

  is.open(argv[1]);
  
  TextQuery dict;
  dict.ReadFile(is);
  for (;;) {
    std::cout << "Enter word to look for, or q to quit:";
    std::string word;
    std::cin >> word;
    if (!std::cin || word == "q") {
      break;
    }
    std::set<TextQuery::index_t> r = dict.RunQuery(word);
    PrintResult(r, word, dict);
  }
  return 0;
}

void PrintResult(std::set<TextQuery::index_t> r, std::string& word, TextQuery& dict) {
  std::cout << word << " occurs " << r.size() << std::endl;
  std::set<TextQuery::index_t>::iterator iter = r.begin();
  for (; iter != r.end(); ++iter) {
    std::cout << "(line " << (*iter + 1) << ") "
              << dict.TextLine(*iter) << std::endl;
  }
}
