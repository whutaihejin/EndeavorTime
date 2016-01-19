# function defined


def addMe2Me(x):
    "apply + operation to argument"
    return (x + x)

# function call
print addMe2Me(4.25)
print addMe2Me(10)
print addMe2Me('Python')
print addMe2Me([-1, 'abc'])

# default argument


def foo(debug=True):
    "determine if in debug mode with default argument"
    if debug:
        print 'in debug mode'
    print 'done'

foo()
foo(False)