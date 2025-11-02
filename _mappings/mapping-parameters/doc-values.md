---
layout: default
title: Doc values
parent: Mapping parameters
redirect_from:
  - /field-types/mapping-parameters/doc-values/
nav_order: 25
has_children: false
has_toc: false
---

# Doc values

* Sorting, aggregations, and field access | scripts,
  * == operations / find terms -- from -- documents
  * ❌NOT work -- via -- inverted index❌

* doc values
  * == 💡data structure💡 /
    * on-disk, 
    * column-oriented
    * created | index time 
  * use cases
    * sorting, aggregations, and field access | scripts,
  * vs [`_source` field](../metadata-fields/source.md)
    * store the SAME values
    * format / optimized -- for -- fast sorting & aggregations
  * by default,
    * 👀enabled | ALL field types👀
      * except for `text` fields
      * if a field will NOT be used for sorting, aggregations, or scripting -> disable doc values
        * Reason:🧠reduce disk usage🧠

## Example

To understand how `doc_values` affect fields, create a sample index
* In this index, the `status_code` field  has `doc_values` enabled by default, allowing it to support sorting and aggregations
* The `session_id` field has `doc_values` disabled, so it does not support sorting or aggregations but can still be queried:

```json
PUT /web_analytics
{
  "mappings": {
    "properties": {
      "status_code": {
        "type": "keyword"
      },
      "session_id": {
        "type": "keyword",
        "doc_values": false
      }
    }
  }
}
```
{% include copy-curl.html %}

Add some sample data to the index:

```json
PUT /web_analytics/_doc/1
{
  "status_code": "200",
  "session_id": "abc123"
}
```
{% include copy-curl.html %}

```json
PUT /web_analytics/_doc/2
{
  "status_code": "404",
  "session_id": "def456"
}
```
{% include copy-curl.html %}

```json
PUT /web_analytics/_doc/3
{
  "status_code": "200",
  "session_id": "ghi789"
}
```
{% include copy-curl.html %}

Perform an aggregation on the `status_code` field:

```json
GET /web_analytics/_search
{
  "size": 0,
  "aggs": {
    "status_codes": {
      "terms": {
        "field": "status_code"
      }
    }
  }
}
```
{% include copy-curl.html %}

This aggregation returns correct results because `status_code` has `doc_values` enabled:

```json
{
  "took": 37,
  "timed_out": false,
  "terminated_early": true,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 3,
      "relation": "eq"
    },
    "max_score": null,
    "hits": []
  },
  "aggregations": {
    "status_codes": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "200",
          "doc_count": 2
        },
        {
          "key": "404",
          "doc_count": 1
        }
      ]
    }
  }
}
```

Attempt to aggregate on the `session_id` field:

```json
GET /web_analytics/_search
{
  "size": 0,
  "aggs": {
    "session_counts": {
      "terms": {
        "field": "session_id"
      }
    }
  }
}
```
{% include copy-curl.html %}

This aggregation fails because `session_id` has `doc_values` disabled, preventing the document-to-field lookup required for aggregations.
