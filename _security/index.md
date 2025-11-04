---
layout: default
title: About Security
nav_order: 1
has_children: false
has_toc: false
nav_exclude: true
permalink: /security/
redirect_from:
  - /security-plugin/
  - /security-plugin/index/
  - /security/index/
---

# About Security in OpenSearch

* OpenSearch's security
  * 's MAIN features
    * are
      * [Encryption](#encryption)
      * [Authentication](#authentication)
      * [Access control](#access-control)
      * [Audit logging & compliance](#audit-logging-and-compliance) 
    * allow
      * safeguard data
      * track activity | cluster

## Features

### Encryption

* allows
  * protecting data |
    * [rest](../_troubleshoot/index.md#encryption-at-rest)
      * == 👀client -- to -- OpenSearch node 👀
      * -- via -- TLS protocol
      * managed -- by -- OS | EACH OpenSearch node
      * == protect data |
        * cluster 
          * _Examples:_ indexes, logs, swap files, automated snapshots
        * application directory
    * transit
      * 👀are
        * -- to -- OpenSearch cluster
        * -- from -- OpenSearch cluster
        * | OpenSearch cluster
      * -- via -- TLS protocol👀
      * managed -- by -- OpenSearch security

* [Configure TLS certificates](configuration/tls.md)

### Authentication

* Authentication
  * allows
    * validating the identity of users
      * _Examples:_ name & password, JSON web token, TLS certificate 
  * steps
    * authentication domain extracts those credentials -- from a -- user’s request
    * verify an end user’s credentials -- against a -- backend configuration
  * recommendations
    * 👀chain >1 authentication method👀
      * Reason:🧠create a robust defense -- against -- unauthorized access🧠
  * [how to configure the Security backend](configuration/configuration.md)

### Access control

* Access control (== authorization)
  * allows
    * assign permissions -- , through roles, to -- users /
      * enable them -- to -- perform specific tasks
  * [MORE](access-control/users-roles)

* Roles
  * == actions + data (cluster's settings, indexes, ...) / users can perform OR read
  * are
    * reusable | MULTIPLE users
    * 👀Fine-grained access control (FGAC)👀
      * == role control permissions / users |
        * cluster level 
        * index level
        * document level
        * field level
    * Field masking
      * == FGAC extension /
        * 👀certain types of data can be masked BUT can be aggregated👀 

* Users
  * can have >1 roles

* [MORE](access-control/index)

### Audit logging & compliance

* audit logging and compliance
  * == mechanisms /
    * allow for, about activities | cluster,
      * tracking
      * analysing

* uses
  * AFTER
    * data breaches 
    * exposing data
  * optimization

* [MORE](audit-logs/index)

## Other features and functionality

### OpenSearch Dashboards multi-tenancy

* Tenants
  * == work spaces /
    * include 
      * visualizations,
      * index patterns,
      * OTHER Dashboards objects

* Multi-tenancy
  * allows for,
    * sharing tenants AMONG users of Dashboards /
      * access managed -- through -- OpenSearch roles
  * [MORE](multi-tenancy/tenant-index)

### Cross-cluster search

* allows
  * users can perform searches | 1 cluster's node -- ACROSS -- OTHER clusters
* supported -- by -- OpenSearch access control infrastructure,
* [MORE](access-control/cross-cluster-search)
