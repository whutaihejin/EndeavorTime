#include <iostream>
#include <assert.h>
#include <set>

int main(int argc, char** argv) {
  std::set<int> cover;
  int n = 0;
  while (std::cin >> n) {
    if (n == 0) {
      break;
    }
    assert(n >= 1);
    // input
    int* arr = new int[n];
    for (int i = 0; i < n; i++) {
      std::cin >> arr[i];
      for (int k = arr[i]; k > 1;) {
        if ((k & 0x01) == 0) {
	  k >>= 1;
	} else {
	  k = 3 * k + 1;
	  k >>= 1;
	}
	if (k != 1) cover.insert(k);
      }
    }
    // output
    bool flag = true;
    for (int i = n-1; i >= 0; --i) {
      if (cover.count(arr[i]) == 1 || arr[i] == 1) continue;
      if (flag) { std::cout << arr[i]; flag = false; }
      else std::cout << " " << arr[i];
    }
    std::cout << std::endl;
    cover.clear();
    delete[] arr;
  }
  return 0;
}
