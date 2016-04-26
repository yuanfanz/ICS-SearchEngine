# UCI-ICS-search-engine
Chien-Lin Chen, Yen-Feng Cheng, Yuanfan Zhang (Ordered by characters)
It is a search engine for ICS school of UC, Irvine.

![alt tag](https://raw.githubusercontent.com/YenF/UCI-ICS-search-engine/maven/WebInterface.png)

## Crawler:
We use [crawler4j](https://github.com/yasserg/crawler4j) as code base and modify it.

## Data:
We use anchor-text collection to enhance webpage's ranking accuracy. Anchor text is the visible, clickable text in a hyperlink. It can be viewed as a brief and meaningful comment to the webpage pointed by the hyperlink. By anchor-text collection, a webpage can have all the anchor text from webpages pointing to it. Anchor text can let the search engine know what is the key information in this webpage. Therefore, it can enhance ranking accuracy. 

## Storage: 
MongoDB is used to store cached pages, indices and other supplemented data. Go into src/main/java/storage for details.

## Web Interface:
With JSP and Servlets, search engine interface is implemented with Bootstrap and some revise with HTML/CSS.
Future work: We may add asynchronous searching feature with AJAX. 
