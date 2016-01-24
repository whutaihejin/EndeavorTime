list_array = [1, 2, 3, 4, 5]
sum = 0
for i in list_array:
    sum += i
print 'sum = %d' % sum

sum = 0
i = 0
while i < len(list_array):
    sum += list_array[i]
    i += 1
print 'sum = %d' % sum

tuple_array = (1, 2, 3, 4, 5)
sum = 0
for i in tuple_array:
    sum += i
print 'sum = %d' % sum

sum = 0
i = 0
while i < len(tuple_array):
    sum += list_array[i]
    i += 1
print 'sum = %d' % sum