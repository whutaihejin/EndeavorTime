#include <iostream>

// typedef double Money;

class Account {
public:
  Money balance() { return bal_; }
private:
  // error: changes meaning of ‘Money’ from ‘typedef double Money’ [-fpermissive]
  Money bal_;
  typedef long double Money;
};

int main(int argc, char** argv) {
  
  return 0;
}
