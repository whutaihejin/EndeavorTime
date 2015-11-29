#include <iostream>
#include <set>

int main(int argc, char** argv) {
  std::set<int> s;
  s.insert(1);
  s.insert(2);
  std::set<int>::reverse_iterator iter;
  for (iter = s.rbegin(); iter != s.rend();) {
    std::cout << *iter << " ";
    s.erase((++iter).base());
  }
  return 0;
}
