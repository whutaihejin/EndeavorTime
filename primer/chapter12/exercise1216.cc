#include <iostream>

class Screen {
public:
  typedef std::string::size_type index_t;

  Screen(): head_(0) { }
  index_t GetCursor() const;
  index_t GetHead() const;
private:
  const index_t head_;
  index_t cursor_;
  std::string contents_;
};

Screen::index_t Screen::GetCursor() const {
  return cursor_;
}

Screen::index_t Screen::GetHead() const {
  return head_;
}

int main(int argc, char** argv) {
  Screen screen;
  screen.GetCursor();
  screen.GetHead();
  return 0;
}
