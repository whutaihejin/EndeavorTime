#include <iostream>

class Person {
public:
  Person(std::string name, std::string address): name_(name), address_(address) { }
  std::string GetName() const { return name_; }
  std::string GetAddress() const { return address_; }
private:
  std::string name_;
  std::string address_;
};

int main(int argc, char** argv) {
  Person persion("taihejin", "whu");  
  return 0;
}
