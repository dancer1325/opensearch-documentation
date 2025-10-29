# Run OpenSearch | Docker container
## pull the images
```
# 1. -- via -- Docker Hub images
docker pull opensearchproject/opensearch
docker pull opensearchproject/opensearch-dashboards

# 2. -- via -- Amazon ECR images
docker pull public.ecr.aws/opensearchproject/opensearch
docker pull public.ecr.aws/opensearchproject/opensearch-dashboards
```
## run the images
### | OpenSearch 2.12-
* 
* `docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" -e "OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>" opensearchproject/opensearch:latest`
### | OpenSearch 2.12+


