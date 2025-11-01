---
layout: default
title: Intro to OpenSearch
nav_order: 2
has_math: true
redirect_from: 
 - /intro/
---

# Introduction to OpenSearch

* goal
  * OpenSearch essential concepts

## Overview

* [Youtube video](https://www.youtube.com/watch?v=GbkRaxj-bJw)
  * TODO:

## Document

* _document_
  * := 👀unit👀 / stores information (text or structured data)
    * | [JSON](https://www.json.org/) format
    * == | traditional database, 
      * 1 row
    * == 👀information / returned -- by -- OpenSearch👀
    * [document IDs assignation](communicate.md#indexing-documents)
    * _Example:_ database of students,
      * document == 1 student
        
        ID   | Name      | GPA   | Graduation year | 
        :--- |:----------|:------|:----------------| 
        1    | John Doe  | 3.89  | 2022            | 

        ```json
        {
          "name": "John Doe",
          "gpa": 3.89,
          "grad_year": 2022
        }
        ```

## Index

* _index_
  * := collection of documentS 
  * | search for information,
    * you query data | 1 index
  * == | traditional database,
    * 1 table
  * _Example:_ database of students,
    * index == ALL students | school

        ID   | Name            | GPA  | Graduation year 
        :--- |:----------------|:-----| :---- 
        1    | John Doe        | 3.89 | 2022
        2    | Jonathan Powers | 3.85 | 2025
        3    | Jane Doe        | 3.52 | 2024
        .... |                 |      |

## Clusters and nodes

* _nodes_
  * == servers / 
    * preprocess data BEFORE index
    * store your data
    * process search requests 
    * manage cluster's state
  * [types](/opensearch-documentation/_tuning-your-cluster/index.md#nodes)

* OpeanSearch
  * 👀ways to run it👀
    * | 1! node
      * MINIMAL system requirement
      * -> perform EVERY task
    * | >=1 nodes
      * recommendations
        * nodes /
          * fast disks & plenty of RAM -> index & search data
          * plenty of CPU power & tiny disk -> manage cluster state 
      * Reason:🧠it's distributed🧠
 
* OpenSearch *cluster*
  * == collection of nodeS /
    * 👀_cluster manager_ node👀
      * responsible for
        * orchestrates cluster-level operations
          * _Example:_ create an index
    * communicated with EACH OTHER
      * if your request is routed to a node -> node
        * sends requests -- to -- other nodes,
        * gathers other nodes' responses,
        * returns the final response

## Shards

* OpenSearch index
  * == MULTIPLE shardS

* _shard_
  * == subset of ALL documents | 1 index
  * == 💡FULL lucence index💡
    * EACH Lucene instance == running process / consumes CPU & memory
  * recommendations
    * shard MAXIMUM size: [10 GB, 50 GB]
    * MORE shards != better
  * uses
    * even distribution | cluster's nodes 

    ![](/opensearch-documentation/images/intro/index-shard.png)
  * _Example:_ cluster / 2 indexes 
    * index 1 
      * split | 2 shards
    * index 2
      * split | 4 shards 
    * shards
      * distributes | 2 nodes

    ![](/opensearch-documentation/images/intro/cluster.png)

## Primary and replica shards

* types of shards
  * _primary_ (original) shard
  * _replica_ (copy) shard
    * ⚠️| node != | node / primary shar⚠️
    * uses
      * backups
      * improve search request processing
    * by default, 1 / EACH primary shard
    * | search-heavy workload,
      * you can specify >1 replica shard

* _Example:_ [PREVIOUS](#shards)

    ![](/opensearch-documentation/images/intro/cluster-replicas.png)

---OpenSearch distributes replica shards to different nodes than their corresponding primary shards---

## Inverted index

* _inverted index_
  * == data structure /
    * 👀maps words -- to the -- documents | they occur👀
  * _Example:_ 
    * 1 index / has 2 documents
      - Document 1: "Beauty is in the eye of the beholder"
      - Document 2: "Beauty and the beast"
    * inverted index

    Word     | Document Id | word's position \| document 
    :------- | :---------- | :----------
    beauty   | 1, 2        | 0, 0
    is       | 1           | 1
    in       | 1           | 2
    the      | 1, 2        | 3, 2
    eye      | 1           | 4
    of       | 1           | 5
    beholder | 1           | 6
    and      | 2           | 1
    beast    | 2           | 3

## Relevance

* | search for a document | OpenSearch,
  * 👀OpenSearch matches the words | query -- to the -- words | documents👀
  * return ALL documents / has a _relevance score_

* _search terms_
  * == individual words | search query /  
    * 👀scored -- according to -- [Okapi BM25](https://en.wikipedia.org/wiki/Okapi_BM25) 👀
      1. _term frequency_
         * if search term occurs frequently | 1 document -> higher score 
      2. _inverse document frequency_
         * if search term occurs | >1 documents -> lower score 
      3. _length normalization_
         * if search term occurs | short document -> score > search term occurs | long document

## Next steps

- Learn how to install OpenSearch within minutes in [Installation quickstart]({{site.url}}{{site.baseurl}}/getting-started/quickstart/).
