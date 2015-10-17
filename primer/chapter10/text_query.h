#ifndef TEXT_QUERY_H
#define TEXT_QUERY_H

#include <string>
#include <vector>
#include <map>
#include <set>

class TextQuery {
public:
  typedef std::vector<std::string>::size_type index_t;
  TextQuery() {}
  void ReadFile(std::ifstream& is) {
    StoreFile(is);
    BuildMap();
  }
  std::set<index_t> RunQuery(std::string&) const;
  std::string TextLine(index_t) const;
private:
  void StoreFile(std::ifstream&);
  void BuildMap();

  std::vector<std::string> lines_of_text_;
  std::map<std::string, std::set<index_t> > word_map_;
};

#endif
