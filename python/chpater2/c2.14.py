squared = [x ** 2 for x in range(4)]
for i in squared:
    print i,
print

sqdEvents = [x ** 2 for x in range(8) if not x % 2]
for i in sqdEvents:
    print i,
print