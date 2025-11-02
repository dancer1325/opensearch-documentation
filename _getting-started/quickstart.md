---
layout: default
title: Installation quickstart
nav_order: 3
redirect_from: 
  - /about/quickstart/
  - /opensearch/install/quickstart/
  - /quickstart/
---

# Installation quickstart

* supported [installation methods](/opensearch-documentation/_install-and-configure)
  * Docker, 
  * Debian,
  * Helm,
  * RPM,
  * tarball,
  * Windows

* goal
  * installation -- via -- [Docker](https://www.docker.com/)

## Prerequisite

* install [Docker](https://docs.docker.com/get-docker/)

## ways
### -- via -- `docker run`
* uses
  * | test environments

### -- via -- Docker Compose
* uses
  * run custom OpenSearch & OpenSearch Dashboards cluster

#### cluster WITHOUT security

* [here](../_install-and-configure/install-opensearch/docker.md#---via----docker-compose)

#### Set up a cluster with security (recommended for most use cases)

* [here](../_install-and-configure/install-opensearch/docker.md#linux-settings)

## COMMON issues, | fail to start your containers

### Docker commands require elevated permissions

* Solution:
  * `sudo usermod -aG docker $USER`
    * == your user is added | `docker` user group
    * == ❌NO need to run your Docker commands -- via -- `sudo`❌ 
    * [Docker's  ost-installation steps -- for -- Linux](https://docs.docker.com/engine/install/linux-postinstall/)

### Error message: "max virtual memory areas vm.max_map_count [65530] is too low"

* Reason: 🧠your host's `vm.max_map_count` is too low🧠
* Solution: set `vm.max_map_count` appropriately
  * [important system settings](/opensearch-documentation/_install-and-configure/install-opensearch/index.md#important-settings)

## Next steps

- [how to send requests -- to -- OpenSearch](communicate)
