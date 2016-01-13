from sqlalchemy import *
import pymysql

db = create_engine("mysql+pymysql://meta_reader:wq4ixcis429isvm@10.75.26.85/_metadata?host=10.75.26.85?port=3306")
db.echo = True

metadata = BoundMetaData(db)
metadata_tables = Table("metadata_table", metadata, autoload = True)

def run(stmt):
    rs = stmt.execute()
    for row in rs:
        print row

s = metadata_tables.select()
run(s)
