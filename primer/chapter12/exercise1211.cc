#include <iostream>

// declare for class Y;
class Y;

class X {
  Y* y;
};

class Y {
  X x;
};

int main(int argc, char** argv) {
  X x;
  Y y;
  return 0;
}
