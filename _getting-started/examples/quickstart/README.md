## ways
### -- via -- `docker run`
* 
    ```bash
    docker pull opensearchproject/opensearch:latest && docker run -it -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" -e "DISABLE_SECURITY_PLUGIN=true" opensearchproject/opensearch:latest
    ```
  * disables security
  * it can take SOME time
* hit [quickstart.http](quickstart.http)
  * Reason: 🧠check OpenSearch is running🧠
