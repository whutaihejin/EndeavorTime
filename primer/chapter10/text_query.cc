#include "text_query.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <stdexcept>

void TextQuery::StoreFile(std::ifstream& is) {
  std::string line;
  while (std::getline(is, line)) {
    lines_of_text_.push_back(line);
  }
}

std::string TextQuery::TextLine(index_t index) const {
  if (index < lines_of_text_.size()) {
    return lines_of_text_[index];
  }
  throw std::out_of_range("line index out of range");
}

std::set<TextQuery::index_t> TextQuery::RunQuery(std::string& word) const {
  std::map<std::string, std::set<index_t> >::const_iterator iter
    = word_map_.find(word);
  if (iter != word_map_.end()) {
    return iter->second;
  }
  return std::set<index_t>();
}

void TextQuery::BuildMap() {
  for (int i = 0; i != lines_of_text_.size(); ++i) {
    std::istringstream stream(lines_of_text_[i]);
    std::string word;
    while(stream >> word) {
      word_map_[word].insert(i);
    }
  }
  // show internal map
  std::map<std::string, std::set<index_t> >::const_iterator iter = word_map_.begin();
  for (; iter != word_map_.end(); ++iter) {
    std::cout << iter->first << std::endl;
  }
}
