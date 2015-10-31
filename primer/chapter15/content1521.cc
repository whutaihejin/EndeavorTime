#include <stdio.h>

class Item_base {

public:
	Item_base(const std::string& book = "", double sales_price = 0.0):
		isbn_(book), price_(sales_price) { }
	std::string book() const { return isbn_; }
	virtual double net_price(std::size_t n) const {
		return n * price_;
	}
	virtual ~Item_base() { }

private:
	std::string isbn_;

protected:
	double price_;
};



int main(int argc, char** argv) {
	printf("hello world!\n");
	return 0;
}
