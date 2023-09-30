# WebCrawler

## Overview

This application is a versatile web crawling tool designed to efficiently discover and gather information from the web based on specific keywords. What sets it apart is its capability to perform multiple searches simultaneously. This is achieved by providing partial results of the POST request on the GET route, allowing users to access ongoing search results while the application continues to crawl the web.

## Features

1. **Keyword-Driven Web Crawling:**
   - Specify a keyword, and the web crawler meticulously searches the web, returning URLs relevant to that keyword.

2. **Concurrency for Performance Boost:**
   - Utilizes multi-threading to enhance performance, ensuring a faster and more efficient web crawling experience.

3. **Customizable Depth Control:**
   - Tailor the depth of web exploration by setting a maximum limit, providing control over the extent of the crawling process.

## How to run
```
docker build . -t axreng/backend
docker run -e BASE_URL=http://example.com/ -p 4567:4567 --rm axreng/backend
```
## Usage
- GET a search by ID:
```
curl --request GET --url http://localhost:4567/crawl/AofnexRJ
```

- POST a search:
```
curl --request POST \
  --url 'http://localhost:4567/crawl?limit=3000' \
  --header 'Content-Type: application/json' \
  --data '{
	"keyword": "misteriousword"
}'
```


Github: [github.com/arthurgabriel73](https://github.com/arthurgabriel73/) |
Linkedin: [linkedin.com/in/arthurgabriel73](https://www.linkedin.com/in/arthurgabriel73/)