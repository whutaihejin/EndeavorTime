#include <iostream>
#include <iomanip>

inline int abs(int x) {
  return x < 0 ? -x : x;
}

int main(int argc, char** argv) {
  double grade = 0.0;
  int p = 0 , t = 0, g1 = 0 , g2 = 0, g3 = 0, gj = 0;
  while( std::cin >> p >> t >> g1 >> g2 >> g3 >> gj) {
    if (abs(g1 - g2) <= t) {
      grade = (g1 + g2) / 2.0;
    } else if (abs(g3 - g1) <= t && abs(g3 - g2) > t) {
      grade = (g3 + g1) / 2.0;
    } else if (abs(g3 - g1) > t && abs(g3 - g2) <= t) {
      grade = (g3 + g2) / 2.0;
    } else if (abs(g3 - g1) <= t && abs(g3 - g2) <= t) {
      int larger = g1;
      if (larger < g2) larger = g2;
      if (larger < g3) larger = g3;
      grade = larger;
    } else {
      grade = gj;
    }
    std::cout << std::setiosflags(std::ios::fixed)<< std::setprecision(1) << grade << std::endl;
  }
  return 0;
}
