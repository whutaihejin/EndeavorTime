#include <iostream>

class NoName {
public:
  NoName(): pstring_(NULL), ival_(0), dval_(0.0) { }
  NoName(std::string* pstring, int ival, double dval)
    :pstring_(pstring), ival_(ival), dval_(dval) { }
private:
  std::string* pstring_;
  int ival_;
  double dval_;
};

int main(int argc, char** argv) {
  NoName no_name;
  NoName name(NULL, 1, 2.0);
  return 0;
}
