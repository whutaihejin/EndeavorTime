#include <iostream>
#include <vector>
#include <algorithm>

void display(std::vector<int>& wealth) {
  for (int i = wealth.size() - 1; i >= 0; --i) {
    if (i == wealth.size() - 1) {
      std::cout << wealth[i];
      continue;
    }
    std::cout << " " << wealth[i];
  }
  std::cout << std::endl;
}

bool Comparator(int x, int y) {
  return x < y;
}

int main(int argc, char** argv) {
   std::vector<int> wealth;
   int n = 0, m = 0;
   int val = 0;
   while (std::cin >> n >> m) {
     if (n == 0 && m == 0) {
       return 0;
     }
     for (int i = 0; i < n; i++) {
       std::cin >> val;
       // heap 
       if (wealth.size() < m) {
         wealth.push_back(val);
       } else if (val > *wealth.begin()) {
         wealth.erase(wealth.begin());
         wealth.push_back(val); 
       }
       std::sort(wealth.begin(), wealth.end(), Comparator);
     }
     display(wealth);
     wealth.clear();
   } 
}
