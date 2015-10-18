#include <iostream>

class Person {
public:
  Person(std::string name, std::string address): name_(name), address_(address) { }
private:
  std::string name_;
  std::string address_;
};

int main(int argc, char** argv) {
  Person persion("taihejin", "whu");  
  return 0;
}
