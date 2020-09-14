import sys
from pymongo import MongoClient

client = MongoClient('mongodb://localhost:27017/')
db = client.aisma
collection = db.tweets

if len(sys.argv)>1:
    print(collection.count_documents({"tag": sys.argv[1], "sentiment": 0}))
    print(collection.count_documents({"tag": sys.argv[1], "sentiment": 1}))
else:
    print(collection.count_documents({"sentiment": 0}))
    print(collection.count_documents({"sentiment": 1}))