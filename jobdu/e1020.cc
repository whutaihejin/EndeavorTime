#include <iostream>
int main(int argc, char** argv) {
  int x = 0, y = 0;
  bool first = true;
  int min_x = 0, min_y = 0, max_x = 0, max_y = 0;
  while (std::cin >> x >> y) {
    if ( x == 0 && y == 0) {
      if (first) {
        break;
      } else {
        std::cout << min_x << " " << min_y << " "
		  << max_x << " " << max_y << std::endl;
	first = true;
      }
      continue;
    }
    if (first) {
      min_x = x;
      max_x = x;
      min_y = y;
      max_y = y;
      first = false;
    } else {
      if (x < min_x) min_x = x;
      else if (x > max_x) max_x = x;
      if (y < min_y) min_y = y;
      else if (y > max_y) max_y = y;
    }
  }  
  return 0;
}
