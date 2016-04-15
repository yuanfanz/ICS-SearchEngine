# CS221_Proj

## Storage: 
Use MongoDB to store data. Every change will reflect to "online database", which means we are now using the same database. After the function is stable, I'll change it to local to run on ICS machine.
### Classes: 
  * FileStorage: store whole pages, mainly for 遠帆
  * TokenStorage: store tokens and 3-grams, mainly for 建霖學長
  * Please do not use reset() function when connecting to ICS server
  * For function details, please generate javadoc.
### How to check stat online:
  * MongoLab: https://mongolab.com/databases/cs221_rawpages
    * login with UCInetID with psd: "abc123"
  * ICS: 
    * Need to download mongoDB client, or use it on ICS
    * on ICS: "module load mongodb/3.0.1" before using mongoDB command

      ```
      mongo ramon-limon.ics.uci.edu:8888
      use cs221_rawpages
      db.auth("UCI_Handsomes","UCI_Handsomes")
      db.printCollectionStats()
      ```
      
    * Use "mongo ramon-limon.ics.uci.edu:8888" to login to ICS mongoDB server
    * "use cs221_rawpages" to change to cs221_rawpages DB
    * "db.auth("UCI_Handsomes","UCI_Handsomes")" to authenticate as proper user
    * "db.printCollectionStats()" to print out stats

## Tokenizer:
tokenize(text, URL)
divide URL into domain & subdomain


## Crawler:


