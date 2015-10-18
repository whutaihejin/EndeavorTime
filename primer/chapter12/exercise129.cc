#include <iostream>

class Screen {
public:
  typedef std::string::size_type index_t;

  Screen(Screen::index_t height, Screen::index_t width, std::string contents):
    height_(height), width_(width), contents_(contents) { }

  char Get() const { return contents_[cursor_]; }
  char Get(index_t ht, index_t wd) const;
private:
  std::string contents_;
  index_t cursor_;
  index_t height_;
  index_t width_;
};

char Screen::Get(Screen::index_t height, Screen::index_t width) const {
  return 'c';
}

int main(int argc, char** argv) {
  Screen screen(100, 80, "taihejin's pc");
  std::cout << screen.Get() << std::endl;
  std::cout << screen.Get(1, 1) << std::endl;
  return 0;
}
