def deco(func):
    print("before myfunc() called.")
    func()
    print("after myfunc() called.")
    return func

def myfunc():
    print("myfunc() called.")

myfunc = deco(myfunc)

myfunc()
myfunc()
