import sys
import urllib2
from bs4 import BeautifulSoup

req = urllib2.Request('http://10.77.109.36:10010/')
response = urllib2.urlopen(req)
page = response.read()
# print page

soup = BeautifulSoup(page, "lxml")
string_t = type(soup.title.string)

table = soup.find_all("table")[1]
array = []
for tr in table.find_all("tr"):
    for td in tr.find_all("td"):
        array.append(unicode(td.text))
    length = len(array)
    if length != 0 and array[length - 1] == "running http":
        print array
    del array[0:]


# handler = open("/data0/index.html", "w");
# handler.write(page);
# handler.close();
