---
layout: default
title: Join
nav_order: 44
has_children: false
parent: Object field types
grand_parent: Supported field types
redirect_from:
  - /field-types/supported-field-types/join/
  - /opensearch/supported-field-types/join/
  - /field-types/join/
---

# Join field type
**Introduced 1.0**
{: .label .label-purple }

* join field type
  * == 👀parent/child relationship BETWEEN documents | SAME index👀
  * restrictions
    * ⚠️1! join field mapping / index⚠️
    * | index, retrieve, update, or delete a child document,
      * ⚠️provide the [`routing`](/opensearch-documentation/_mappings/metadata-fields/routing.md) parameter⚠️
        * Reason:🧠parent & child documents | SAME relation, have to be indexed | SAME shard🧠
    * ❌NOT ALLOWED MULTIPLE parents❌
    * | EXISTING join field,
      * you can add a NEW relation
    * | EXISTING document,
      * ONLY if the EXISTING document is ALREADY marked as a parent -> you can add a child document

* child document
  * 's `parent` field
    * == parent's ID
    * == way -- to -- refer to its parent

## Querying a join field

When you query a join field, the response contains subfields that specify whether the returned document is a parent or a child
* For child objects, the parent ID is also returned.

### Search for all documents

```json
GET testindex1/_search
{
  "query": {
    "match_all": {}
  }
}
```
{% include copy-curl.html %}

The response indicates whether a document is a parent or a child:

```json
{
  "took" : 4,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 3,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 1.0,
        "_source" : {
          "name" : "Brand 1",
          "product_to_brand" : {
            "name" : "brand"
          }
        }
      },
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 1.0,
        "_routing" : "1",
        "_source" : {
          "name" : "Product 1",
          "product_to_brand" : {
            "name" : "product",
            "parent" : "1"
          }
        }
      },
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "4",
        "_score" : 1.0,
        "_routing" : "1",
        "_source" : {
          "name" : "Product 2",
          "product_to_brand" : {
            "name" : "product",
            "parent" : "1"
          }
        }
      }
    ]
  }
}
```

### Search for all children of a parent 

Find all products associated with Brand 1:

```json
GET testindex1/_search
{
  "query" : {
    "has_parent": {
      "parent_type":"brand",
      "query": {
        "match" : {
          "name": "Brand 1"
        }
      }
    }
  }
}
```
{% include copy-curl.html %}

The response contains Product 1 and Product 2, which are associated with Brand 1:

```json
{
  "took" : 7,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 2,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 1.0,
        "_routing" : "1",
        "_source" : {
          "name" : "Product 1",
          "product_to_brand" : {
            "name" : "product",
            "parent" : "1"
          }
        }
      },
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "4",
        "_score" : 1.0,
        "_routing" : "1",
        "_source" : {
          "name" : "Product 2",
          "product_to_brand" : {
            "name" : "product",
            "parent" : "1"
          }
        }
      }
    ]
  }
}
```

### Search for the parent of a child

Find the parent of Product 1:

```json
GET testindex1/_search
{
  "query" : {
    "has_child": {
      "type":"product",
      "query": {
        "match" : {
            "name": "Product 1"
        }
      }
    }
  }
}
```
{% include copy-curl.html %}

The response returns Brand 1 as Product 1's parent:

```json
{
  "took" : 4,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "testindex1",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 1.0,
        "_source" : {
          "name" : "Brand 1",
          "product_to_brand" : {
            "name" : "brand"
          }
        }
      }
    ]
  }
}
```

## Parent with many children

One parent can have many children
* Create a mapping with multiple children:

```json
PUT testindex1
{
  "mappings": {
    "properties": {
      "parent_to_child": {
        "type": "join",
        "relations": {
          "parent": ["child 1", "child 2"]  
        }
      }
    }
  }
}
```
{% include copy-curl.html %}



## Next steps

- Learn about [joining queries]({{site.url}}{{site.baseurl}}/query-dsl/joining/) on join fields.
- Learn more about [retrieving inner hits]({{site.url}}{{site.baseurl}}/search-plugins/searching-data/inner-hits/).