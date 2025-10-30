# Run OpenSearch | Docker container
## -- via -- `docker run`
### pull the images
```
# 1. -- via -- Docker Hub images
docker pull opensearchproject/opensearch
docker pull opensearchproject/opensearch-dashboards

# 2. -- via -- Amazon ECR images
docker pull public.ecr.aws/opensearchproject/opensearch
docker pull public.ecr.aws/opensearchproject/opensearch-dashboards
```
### run the images
#### | OpenSearch 2.12-
* 
* 
#### | OpenSearch 2.12+
* `docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" -e "OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>" opensearchproject/opensearch:latest`
  * `OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>`
    * == CUSTOM admin password
## -- via -- `docker compose`
### | OpenSearch 2.12-
* `docker compose up -d -f docker-compose-beforev212.yml`
* | browser,
  * http://localhost:5601/
    * Problems:
      * Problem1: "OpenSearch Dashboards server is not ready yet"
        * Solution: Wait 2-3 minutes
    * admin/admin
* `docker compose down -v`
  * `-v`
    * == remove ALL existing Docker volumes
### | OpenSearch 2.12+
* `docker compose up -d`
* | browser,
  * http://localhost:5601/
    * Problems:
      * Problem1: "OpenSearch Dashboards server is not ready yet"
        * Solution: Wait 2-3 minutes
    * admin/admin
* `export OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>`
  * == CUSTOM admin password

