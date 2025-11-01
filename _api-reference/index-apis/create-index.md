---
layout: default
title: Create index
parent: Core index APIs
grand_parent: Index APIs
nav_order: 10
redirect_from:
  - /opensearch/rest-api/index-apis/create-index/
  - /opensearch/rest-api/create-index/
---

# Create Index API
**Introduced 1.0**
{: .label .label-purple }

* ways to create an index
  * via document -- as a -- base 
  * empty index
* ALLOWED configurations / set
  * mappings,
  * settings,
  * aliases 

## Endpoints

```json
PUT <index>
```

## Index naming restrictions

OpenSearch indexes have the following naming restrictions:

- All letters must be lowercase.
- Index names can't begin with underscores (`_`) or hyphens (`-`).
- Index names can't contain spaces, commas, or the following characters:

  `:`, `"`, `*`, `+`, `/`, `\`, `|`, `?`, `#`, `>`, or `<`

## Path parameters

Parameter | Data type | Description
:--- | :--- | :---
index | String | The index name. Must conform to the [index naming restrictions](#index-naming-restrictions). Required. 

## Query parameters

You can include the following query parameters in your request. All parameters are optional.

Parameter | Type | Description
:--- | :--- | :---
wait_for_active_shards | String | Specifies the number of active shards that must be available before OpenSearch processes the request. Default is 1 (only the primary shard). Set to `all` or a positive integer. Values greater than 1 require replicas. For example, if you specify a value of 3, the index must have two replicas distributed across two additional nodes for the request to succeed.
cluster_manager_timeout | Time | How long to wait for a connection to the cluster manager node. Default is `30s`.
timeout | Time | How long to wait for the request to return. Default is `30s`.

## Request body

* OPTIONAL ALL
  * [index settings]({{site.url}}{{site.baseurl}}/im-plugin/index-settings/),
  * [mappings]({{site.url}}{{site.baseurl}}/mappings/index/),
  * [aliases]({{site.url}}{{site.baseurl}}/opensearch/index-alias/),
  * [index context]({{site.url}}{{site.baseurl}}/opensearch/index-context/) 

## Example request

response = client.indices.create(
  index = "sample-index1",
  body =   {
    "settings": {
      "index": {
        "number_of_shards": 2,
        "number_of_replicas": 1
      }
    },
    "mappings": {
      "properties": {
        "age": {
          "type": "integer"
        }
      }
    },
    "aliases": {
      "sample-alias1": {}
    }
  }
)
