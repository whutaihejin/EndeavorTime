#include <iostream>

class HasPtr {
public:
  HasPtr(int* p, int i): ptr(p), val(i) { }
  ~HasPtr() { delete ptr; }
  int *get_ptr() const { return ptr; }
  int get_val() const { return val; }

  void set_ptr(int* p) { ptr = p; }
  void set_int(int i) { val = i; }

  int get_ptr_val() const { return *ptr; }
  int set_ptr_val(int val) const { *ptr = val; }
  
  void display() { 
    std::cout << ptr << " => " << *ptr << std::endl;
  }
   
private:
  int* ptr;
  int val;
};


int main(int argc, char** argv) {
  int val = 22;
  HasPtr h(&val, 1);
  h.display();
  HasPtr b(h);
  b.display();
  b.set_ptr_val(66);
  h.display();
  b.display();

  int* p = new int(33);
  HasPtr ptr(p, 10);
  delete p;
  ptr.set_ptr_val(0);

  return 0;
}
