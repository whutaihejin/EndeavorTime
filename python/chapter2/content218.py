class Book():
    "This is optional documentation string"
    version = 2.0
    def __init__(self, name = 'John Doe'):
        """Constructor"""
        self.name = name;
        print 'create a class instance for', name
    def showname(self):
        print 'your name is', self.name
        print 'my name is', self.__class__.__name__
    
    def showVersion(self):
        print self.version

    def add(self, x):
        return x + x

foo = Book()
foo.showname()
foo.showVersion()
print foo.add(5)
print foo.add("adb")
