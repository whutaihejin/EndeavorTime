class MyData(object):
    pass

mathObj = MyData()
mathObj.x = 4
mathObj.y = 5
print mathObj.x + mathObj.y
print mathObj.x * mathObj.y

class MyDataWithMethod(object):
    def printFoo(self):
        print "You invoked printFoo()!"


myObj = MyDataWithMethod()
myObj.printFoo()

class AddrBookEntry(object):
    "address book entry class"
    def __init__(self, nm, ph):
        self.name = nm
        self.phone = ph
        print 'Created instance for:', self.name
    def updataPhone(self, newph):
        self.phone = newph
        print 'Updated phone# for:', self.name

john = AddrBookEntry("John Doe", "408-555-1212")
jane = AddrBookEntry("Jane Doe", "650-555-1212")

print john
print john.name
print john.phone
print jane.name
print jane.phone
