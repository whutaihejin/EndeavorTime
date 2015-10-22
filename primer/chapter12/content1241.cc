#include <iostream>

class X {
public:
  X() {
    std::cout << "default constructor of class X" << std::endl;
  }
private:
  int x_;
  char c_;
  double d_;
};

class Y {
public:
  Y() {
    std::cout << "default constructor of class Y" << std::endl;
  }
private:
  X x_;
  int z_;
};

int main(int argc, char** argv) {
  Y y;
  return 0;
}
