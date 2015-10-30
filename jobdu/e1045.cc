#include <iostream>
#include <cstdlib>
#include <sstream>

static const int TOTAL = 100;
void buy(int N) {
  std::stringstream stream;
  int bill = 0;
  std::string out_right;
  std::string out_wrong;
  for (int x = 0; x <= 100; x++) {
    for (int y = 0; y <= 100; y++) {
      bill = 15 * x + 9 * y + (TOTAL - x - y);
      // for right answer
      if (bill <= 3 * N) {
        stream << "x=" << x << ",y=" << y << ",z=" << TOTAL - x - y;
        out_right = stream.str();
        stream.str("");
      }
      // for wrong answer
      if (5 * x + 3 * y + (TOTAL - x - y) / 3.0 <= N) {
       stream << "x=" << x << ",y=" << y << ",z=" << TOTAL - x - y; 
       out_wrong = stream.str();       
       stream.str(""); 
      }
      if (out_right != out_wrong) {
        std::cout << x << " => " << y << " => " << TOTAL - x - y << std::endl;
      }
    }
  }
}

int main(int argc, char** argv) {
  int N = 0;
  for (int i = 0; i < 10000; i++) {
    N = rand() % 1000;
    buy(N);
  }
  return 0;
}
