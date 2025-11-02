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

* TODO:
This configuration enables security using demo certificates and requires additional system setup.

1. Before running OpenSearch on your machine, you should disable memory paging and swapping performance on the host to improve performance and increase the number of memory maps available to OpenSearch.
    
    Disable memory paging and swapping:
    
    ```bash
    sudo swapoff -a
    ```
    {% include copy.html %}

    Edit the sysctl config file that defines the host's max map count:

    ```bash
    sudo vi /etc/sysctl.conf
    ```
    {% include copy.html %}

    Set max map count to the recommended value of `262144`:
    
    ```bash
    vm.max_map_count=262144
    ```
    {% include copy.html %}

    Reload the kernel parameters:

    ```bash
    sudo sysctl -p
    ```  
    {% include copy.html %}

1. Download the sample Compose file to your host. You can download the file with command line utilities like `curl` and `wget`, or you can manually copy [docker-compose.yml](https://github.com/opensearch-project/documentation-website/blob/{{site.opensearch_major_minor_version}}/assets/examples/docker-compose.yml) from the OpenSearch Project documentation-website repository using a web browser.

    To use cURL, send the following request:

    ```bash
    curl -O https://raw.githubusercontent.com/opensearch-project/documentation-website/{{site.opensearch_major_minor_version}}/assets/examples/docker-compose.yml
    ```
    {% include copy.html %}

    To use wget, send the following request:

    ```bash
    wget https://raw.githubusercontent.com/opensearch-project/documentation-website/{{site.opensearch_major_minor_version}}/assets/examples/docker-compose.yml
    ```
    {% include copy.html %}

1. First, create a custom admin password. Create (or edit) a `.env` file in the same directory as your `docker-compose.yml` file. This file stores environment variables that Docker Compose automatically reads when starting the containers. Add the following line to define the admin password:

    ```bash
    OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>
    ```
    {% include copy.html %}

1. In your terminal application, navigate to the directory containing the `docker-compose.yml` file you downloaded and run the following command to create and start the cluster as a background process:
    
    ```bash
    docker compose up -d
    ```
    {% include copy.html %}

1. Confirm that the containers are running using the following command:

    ```bash
    docker compose ps
    ```
    {% include copy.html %}

    You should see an output like the following:

    ```bash
    NAME                    COMMAND                  SERVICE                 STATUS              PORTS
    opensearch-dashboards   "./opensearch-dashbo…"   opensearch-dashboards   running             0.0.0.0:5601->5601/tcp
    opensearch-node1        "./opensearch-docker…"   opensearch-node1        running             0.0.0.0:9200->9200/tcp, 9300/tcp, 0.0.0.0:9600->9600/tcp, 9650/tcp
    opensearch-node2        "./opensearch-docker…"   opensearch-node2        running             9200/tcp, 9300/tcp, 9600/tcp, 9650/tcp
    ```

1. Verify that OpenSearch is running. You should use `-k` (also written as `--insecure`) to disable hostname checking because the default security configuration uses demo certificates. Use `-u` to pass the default username and password (`admin:<custom-admin-password>`):

    ```bash
    curl https://localhost:9200 -ku admin:<custom-admin-password>
    ```
    {% include copy.html %}

    You should get a response similar to the one in [Option 1](#option-1-try-opensearch-in-one-command). 

You can now explore OpenSearch Dashboards by opening `https://localhost:5601/` in a web browser on the same host that is running your OpenSearch cluster. The default username is `admin`, and the default password is set in your `docker-compose.yml` file in the `OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>` setting.

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
