#!/usr/bin/python

import MySQLdb

# Open database connection
db = MySQLdb.connect("10.75.26.85","meta_reader","wq4ixcis429isvm","_metadata" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

# execute SQL query using execute() method.
cursor.execute("SELECT VERSION()")

# Fetch a single row using fetchone() method.
data = cursor.fetchone()

print "Database version : %s " % data

# Prepare SQL query to INSERT a record into the database.
sql = "SELECT * FROM metadata_column where meta_table_id = '%d'" % (2)

try:
    # Execute the SQL command
    cursor.execute(sql)
    # Fetch all the rows in a list of lists
    results = cursor.fetchall()
    for row in results:
        print "id=%d, meta_table_id=%d, table_column_name=%s" % \
                (row[0], row[1], row[2])
except:
    print "Error: unable to fetch data"

# disconnect from server
db.close()
