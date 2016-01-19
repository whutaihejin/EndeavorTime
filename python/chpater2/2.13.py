print 'I like to use the Internet for:'
for item in ['email', 'netsurfing', 'homework', 'chat']:
    print item
print

for item in ['email', 'netsurfing', 'homework', 'chat']:
    print item,
print

who = 'knights'
what = 'Ni!'
print 'We are the', who, 'who say', what, what, what, what
print 'We are the %s who say %s' % \
      (who, ((what + ' ') * 4))

for e in [0, 1, 2]:
    print e,
print

for e in range(3):
    print e,
print

foo = 'abc'
for c in foo:
    print c,
print

for i in range(len(foo)):
    print foo[i], '%d' % i
print

for i, ch in enumerate(foo):
    print ch, '%d' % i
print
