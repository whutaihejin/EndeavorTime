#include <iostream>

typedef std::string Type;
Type initVal();

class Exercise {
public:
  typedef double Type;
  Type setVal(Type val);
  Type initVal() { return val_; }
private:
  int val_;
};

Exercise::Type Exercise::setVal(Type val) {
  val_ = val + 0; // initVal();
}

int main(int argc, char** argv) {
  Exercise e;
  e.setVal(10);
  return 0;
}
