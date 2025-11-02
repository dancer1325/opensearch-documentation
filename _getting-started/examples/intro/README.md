# Document
## stored -- as -- JSON format
* steps
  * [index](../communicate/README.md#indexing-documents)
  * [get documents](intro.http) & check the response
## == | traditional database, 1 row
* [get ALL documents](intro.http) & check the response / index

# Index
## == collection of documentS
* [get ALL documents](intro.http) & check the response / index "students"
## | search for information, you query data | 1 index
* [get specific document](intro.http)
  * -> you specify `<index-name>`
## == | traditional database, 1 table
* [get ALL documents](intro.http) & check the response / index

# Clusters and nodes
## nodes
### == servers
* hit [about nodes](intro.http) & check response has: IP, host, OS, ...
#### store your data 
* hit [about nodes](intro.http) & check response has: store.size
#### process search requests 
* hit [process search requests](intro.http) & check response has search related information
#### manage cluster's state
* hit [manage cluster's state](intro.http)

## OpenSearch
### ways to run it
#### 1! node
* [docker-compose.yml / 1! node](../docker-compose.yml)
#### >1 node
* [docker-compose.yml / >1 node](../docker-composeMultipleNodes.yml)
### check number of nodes
* hit [check number of nodes](intro.http)
### recommendations about functionality / EACH node
* [here](../docker-composeFunctionalityPerEachNode.yml)

## OpenSearch cluster
### _cluster manager_ node
#### responsible for orchestrates cluster-level operations
* TODO: