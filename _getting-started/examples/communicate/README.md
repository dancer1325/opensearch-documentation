# OpenSearch REST API
## Sending requests | terminal
* hit [communicate.http](communicate.http)
* `curl -X GET "https://localhost:9200/_cluster/health" -ku admin:YWRtaW46YWRtaW4=`
### `?pretty`
* hit [communicate.http](communicate.http)
* `curl -X GET "http://localhost:9200/_cluster/health?pretty`
### Request body
* hit [communicate.http](communicate.http)
* via curl

    ```
    curl -X GET "http://localhost:9200/students/_search?pretty" -H 'Content-Type: application/json' -d'
    {
      "query": {
        "match_all": {}
      }
    }'
    ```

## TODO:

