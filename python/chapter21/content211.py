#!/user/bin/env python

import os
from random import randrange as rrange

def connect(dbName):
    import _mysql
    #jdbc:mysql://10.75.26.85:3306/_metadata?user=meta_reader&password=wq4ixcis429isvm
    cxn = _mysql.connect(host='10.75.26.85', user='meta_reader', passwd='wq4ixcis429isvm', db=dbName)
    return cxn;

def show(db):
    db.query('SELECT * FROM users')
    print 'excute select operation'
    for data in cur.fetchall():
        print data

def main():
    db = connect('_metadata')
    if not db:
        print 'ERROR: %r not supported, exiting' % db
        return
    cur = .cursor()
    print '\n*** Display the content'
    show(db)

if __name__ == '__main__':
    main()
