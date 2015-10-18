#include <iostream>

class Screen {
public:
  Screen(): height(66) { }
  int GetHeight(int height) {
    // parameter height
    // return height;
    // member height
    // return this->height;
    // member height
    return Screen::height;
  }
private:
  int height;
};

int main(int argc, char** argv) {
  Screen s;
  int height = s.GetHeight(1);
  std::cout << "Height = " << height << std::endl;
  return 0;
}
