---
layout: default
title: Communicate with OpenSearch
nav_order: 30
---

# Communicate with OpenSearch

* ways to communicate -- with -- OpenSearch
  * REST API
    * goal here
  * [OpenSearch language clients](../_clients)

## OpenSearch REST API

* allows
  * making ALMOST everything
    * _Example:_ OpenSearch settings, modify indexes, check cluster health, get statistics
* provides
  * flexibility
* ways to trigger HTTP requests
  * | your terminal
  * | [OpenSearch Dashboards' Dev Tools console](../_dashboards/index.md#dev-tools-) 

### Sending requests | terminal

* -- depend on -- whether you're using the Security plugin
  - **WITHOUT Security plugin**
    - "http://" URLs & NO authentication
  - **WITH Security plugin**
    - "https://" URLs & provide username/password credentials

* [COMMON REST parameters](../_api-reference/common-parameters.md)

#### `?pretty`

* OpenSearch's responses
  * by default, 
    * flat JSON format
  * if you request / pass `?pretty` -> human-readable response body

#### Request body

* requirements
  * `Content-Type` header
  * request payload | `-d` (data) option

### Sending requests | Dev Tools

* [here](/opensearch-documentation/_dashboards/visualize/run-queries.md)

## Indexing documents

* == 💡add a JSON document | OpenSearch index💡
  * 👀if the index does NOT exist -> ALSO create it👀
* syntaxes
  * specifying `<document-id>`

      ```
      PUT /<index-name>/_doc/<document-id>
      ```
  * WITHOUT specifying `<document-id>` -> OpenSearch generates a document ID
      ```
      POSTT /<index-name>/_doc
      ```

* [managing indexes](/opensearch-documentation/_im-plugin/)

## Dynamic mapping

When you index a document, OpenSearch infers the field types from the JSON types submitted in the document
* This process is called _dynamic mapping_
* For more information, see [Dynamic mapping]({{site.url}}{{site.baseurl}}/mappings/#dynamic-mapping).

To view the inferred field types, send a request to the `_mapping` endpoint:

```json
GET /students/_mapping
```
{% include copy-curl.html %}

OpenSearch responds with the field `type` for each field:

```json
{
  "students": {
    "mappings": {
      "properties": {
        "gpa": {
          "type": "float"
        },
        "grad_year": {
          "type": "long"
        },
        "name": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        }
      }
    }
  }
}
```

OpenSearch mapped the numeric fields to the `float` and `long` types. Notice that OpenSearch mapped the `name` text field to `text` and added a `name.keyword` subfield mapped to `keyword`. Fields mapped to `text` are analyzed (lowercased and split into terms) and can be used for full-text search. Fields mapped to `keyword` are used for exact term search.

OpenSearch mapped the `grad_year` field to `long`. If you want to map it to the `date` type instead, you need to [delete the index](#deleting-an-index) and then recreate it, explicitly specifying the mappings. For instructions on how to explicitly specify mappings, see [Index mappings and settings](#index-mappings-and-settings).

## Searching for documents

To run a search for the document, specify the index that you're searching and a query that will be used to match documents. The simplest query is the `match_all` query, which matches all documents in an index:

```json
GET /students/_search
{
  "query": {
    "match_all": {}
  }
}
```
{% include copy-curl.html %}

OpenSearch returns the indexed document:

```json
{
  "took": 12,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 1,
    "hits": [
      {
        "_index": "students",
        "_id": "1",
        "_score": 1,
        "_source": {
          "name": "John Doe",
          "gpa": 3.89,
          "grad_year": 2022
        }
      }
    ]
  }
}
```

For more information about search, see [Search your data]({{site.url}}{{site.baseurl}}/getting-started/search-data/).

## Updating documents

In OpenSearch, documents are immutable. However, you can update a document by retrieving it, updating its information, and reindexing it. You can update an entire document using the Index Document API, providing values for all existing and added fields in the document. For example, to update the `gpa` field and add an `address` field to the previously indexed document, send the following request:

```json
PUT /students/_doc/1
{
  "name": "John Doe",
  "gpa": 3.91,
  "grad_year": 2022,
  "address": "123 Main St."
}
```
{% include copy-curl.html %}

Alternatively, you can update parts of a document by calling the Update Document API:

```json
POST /students/_update/1/
{
  "doc": {
    "gpa": 3.91,
    "address": "123 Main St."
  }
}
```
{% include copy-curl.html %}

For more information about partial document updates, see [Update Document API]({{site.url}}{{site.baseurl}}/api-reference/document-apis/update-document/).

## Deleting a document

To delete a document, send a delete request and provide the document ID:

```json
DELETE /students/_doc/1
```
{% include copy-curl.html %}

## Deleting an index

To delete an index, send the following request:

```json
DELETE /students
```
{% include copy-curl.html %}

## Index mappings and settings

OpenSearch indexes are configured with mappings and settings:

- A _mapping_ is a collection of fields and the types of those fields. For more information, see [Mappings and field types]({{site.url}}{{site.baseurl}}/mappings/).
- _Settings_ include index data like the index name, creation date, and number of shards. For more information, see [Configuring OpenSearch]({{site.url}}{{site.baseurl}}/install-and-configure/configuring-opensearch/index/).

You can specify the mappings and settings in one request. For example, the following request specifies the number of index shards and maps the `name` field to `text` and the `grad_year` field to `date`:

```json
PUT /students
{
  "settings": {
    "index.number_of_shards": 1
  }, 
  "mappings": {
    "properties": {
      "name": {
        "type": "text"
      },
      "grad_year": {
        "type": "date"
      }
    }
  }
}
```
{% include copy-curl.html %}

Now you can index the same document that you indexed in the previous section:

```json
PUT /students/_doc/1
{
  "name": "John Doe",
  "gpa": 3.89,
  "grad_year": 2022
}
```
{% include copy-curl.html %}

To view the mappings for the index fields, send the following request:

```json
GET /students/_mapping
```
{% include copy-curl.html %}

OpenSearch mapped the `name` and `grad_year` fields according to the specified types and inferred the field type for the `gpa` field:

```json
{
  "students": {
    "mappings": {
      "properties": {
        "gpa": {
          "type": "float"
        },
        "grad_year": {
          "type": "date"
        },
        "name": {
          "type": "text"
        }
      }
    }
  }
}
```

Once a field is created, you cannot change its type. Changing a field type requires deleting the index and recreating it with the new mappings. 
{: .note}

## Further reading

- For information about the OpenSearch REST API, see the [REST API reference]({{site.url}}{{site.baseurl}}/api-reference/).
- For information about OpenSearch language clients, see [Clients]({{site.url}}{{site.baseurl}}/clients/).
- For information about mappings, see [Mappings and field types]({{site.url}}{{site.baseurl}}/mappings/).
- For information about settings, see [Configuring OpenSearch]({{site.url}}{{site.baseurl}}/install-and-configure/configuring-opensearch/index/).

## Next steps

- [ingestion options](ingest-data)
