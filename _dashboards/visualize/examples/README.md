# how has it been created?
* `docker compose up -d`
  * Problems:
    * Problem1: "OpenSearch Dashboards server is not ready yet" -- "getaddrinfo ENOTFOUND opensearch-node1"}"
      * Solution: `OPENSEARCH_HOSTS` == correct container name