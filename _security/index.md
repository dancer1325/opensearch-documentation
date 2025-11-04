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


* The backend used for validation can be OpenSearch's built-in internal user database—used for storing user and role configurations and
* A common practice is to chain together more than one authentication method to create a more robust defense against unauthorized access
* This might involve, for example, HTTP basic authentication followed by a backend configuration that specifies the LDAP protocol
* [how to configure the Security backend](configuration/configuration.md)

### Access control

Access control (or authorization) generally involves selectively assigning permissions to users that allow them to perform specific tasks, such as clearing the cache for a particular index or taking a snapshot of a cluster. However, rather than assign individual permissions directly to users, OpenSearch assigns these permissions to roles and then maps the roles to users. For more on setting up these relationships, see [Users and roles]({{site.url}}{{site.baseurl}}/security/access-control/users-roles/). Roles, therefore, define the actions that users can perform, including the data they can read, the cluster settings they can modify, the indexes to which they can write, and so on. Roles are reusable across multiple users, and users can have multiple roles.

Another notable characteristic of access control in OpenSearch is the ability to assign user access through levels of increasing granularity. Fine-grained access control (FGAC) means that a role can control permissions for users at not only the cluster level but also the index level, the document level, and even the field level. For example, a role may provide a user access to certain cluster-level permissions but at the same time prevent the user from accessing a given group of indexes. Likewise, that role may grant access to certain types of documents but not others, or it may even include access to specific fields within a document but exclude access to other sensitive fields. Field masking further extends FGAC by providing options to mask certain types of data, such as a list of emails, which can still be aggregated but not made viewable to a role.

To learn more about this feature, see the [Access control]({{site.url}}{{site.baseurl}}/security/access-control/index/) section of the security documentation.

### Audit logging and compliance

Finally, audit logging and compliance refer to mechanisms that allow for tracking and analysis of activity within a cluster. This is important after data breaches (unauthorized access) or when data suffers unintended exposure, as could happen when the data is left vulnerable in an unsecured location. However, audit logging can be just as valuable a tool for assessing excessive loads on a cluster or surveying trends for a given task. This feature allows you to review changes made anywhere in a cluster and track access patterns and API requests of all types, whether valid or invalid.

How OpenSearch archives logging is configurable at many levels of detail, and there are a number of options for where those logs are stored. Compliance features also ensure that all data is available if and when compliance auditing is required. In this case, the logging can be automated to focus on data especially pertinent to those compliance requirements.

See the [Audit logs]({{site.url}}{{site.baseurl}}/security/audit-logs/index/) section of the security documentation to read more about this feature.

## Other features and functionality

OpenSearch includes other features that complement the security infrastructure.

### Dashboards multi-tenancy

One such feature is OpenSearch Dashboards multi-tenancy. Tenants are work spaces that include visualizations, index patterns, and other Dashboards objects. Multi-tenancy allows for the sharing of tenants among users of Dashboards and leverages OpenSearch roles to manage access to tenants and safely make them available to others.
For more information about creating tenants, see [OpenSearch Dashboards multi-tenancy]({{site.url}}{{site.baseurl}}/security/multi-tenancy/tenant-index/).

### Cross-cluster search

Another notable feature is cross-cluster search. This feature provides users with the ability to perform searches from one node in a cluster across other clusters that have been set up to coordinate this type of search. As with other features, cross-cluster search is supported by the OpenSearch access control infrastructure, which defines the permissions users have for working with this feature.
To learn more, see [Cross-cluster search]({{site.url}}{{site.baseurl}}/security/access-control/cross-cluster-search/).

## Next steps

- To get started with OpenSearch security, read the [Getting started guide]({{site.url}}{{site.baseurl}}/security/getting-started).

- For practical recommendations, follow the [Best practices for OpenSearch security]({{site.url}}{{site.baseurl}}/security/configuration/best-practices/), which include 10 key considerations.

- To configure security in your OpenSearch implementation, use the [Security configuration overview]({{site.url}}{{site.baseurl}}/security/configuration/index/) for step-by-step instructions and links to customization options for your environment.