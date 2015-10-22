#include <iostream>

class X {
public:
  X(int val): y_(val), x_(y_) { }
  void print() {
    std::cout << "x_ = " << x_ << " y_ = " << y_ << std::endl;
  } 
private:
  int x_;
  int y_;
};

int main(int argc, char** argv) {
  X x(10);
  x.print();
  return 0;
}
