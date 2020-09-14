import sys
from textblob import TextBlob
import re
from nltk.corpus import stopwords
from nltk.stem.wordnet import WordNetLemmatizer
# Load model
import pickle
from pymongo import MongoClient

client = MongoClient('mongodb://localhost:27017/')
db = client.aisma
collection = db.tweets


def text_processing(tweet):
    def get_mentions(tw):
        result = re.findall("(^|[^@\w])@(\w{1,15})", tw)
        if len(result) != 0:
            result = [i[1] for i in result]
        return result

    mentions = get_mentions(tweet)

    # Generating the list of words in the tweet (hastags and other punctuations removed)
    def form_sentence(tw):
        tweet_blob = TextBlob(tw)
        return ' '.join(tweet_blob.words)

    new_tweet = form_sentence(tweet)

    # Removing stopwords and words with unusual symbols
    def no_user_alpha(tw):
        tweet_list = [ele for ele in tw.split() if ele not in mentions]
        clean_tokens = [t for t in tweet_list if re.match(r'[^\W\d]*$', t)]
        clean_s = ' '.join(clean_tokens)
        clean_mess = [word for word in clean_s.split() if word.lower() not in stopwords.words('english')]
        return clean_mess

    no_punc_tweet = no_user_alpha(new_tweet)

    # Normalizing the words in tweets
    def normalization(tweet_list):
        lem = WordNetLemmatizer()
        normalized_tweet = []
        for word in tweet_list:
            normalized_text = lem.lemmatize(word, 'v')
            normalized_tweet.append(normalized_text)
        return normalized_tweet

    return normalization(no_punc_tweet)


f = open('classifier.pickle', 'rb')
pipeline = pickle.load(f)
f.close()

# print(pipeline.predict(('@user #cnn calls #michigan middle school \'build the wall\' chant \'\' #tcot',
# 'no comment!  in #australia   #opkillingbay #seashepherd #helpcovedolphins #thecove  #helpcovedolphins')))
# predections = pipeline.predict([tw['tweet_text'] for tw in collection.find({"tag": "covid"})]) predections =
# pipeline.predict([tw['tweet_text'] for tw in collection.find()])
#
# print(predections)
if len(sys.argv)>1:
    for tw in collection.find({"tag": sys.argv[1]}):
        sentiment = pipeline.predict([tw['tweet_text']])[0]
        collection.update_one({'_id': tw['_id']}, {"$set": {"sentiment": int(sentiment)}})
else:
    for tw in collection.find():
        sentiment = pipeline.predict([tw['tweet_text']])[0]
        collection.update_one({'_id': tw['_id']}, {"$set": {"sentiment": int(sentiment)}})