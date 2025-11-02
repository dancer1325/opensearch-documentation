---
layout: default
title: Docker
parent: Installing OpenSearch
nav_order: 5
redirect_from: 
  - /opensearch/install/docker/
  - /install-and-configure/install-opensearch/docker/
---

# Docker

* pull images |
  * [Docker Hub](https://hub.docker.com/u/opensearchproject) 
  * [Amazon Elastic Container Registry (Amazon ECR)](https://gallery.ecr.aws/opensearchproject/)

## Install Docker and Docker Compose

* recommendations
  * | Docker Desktop users,
    * **Settings** > **Resources** > host memory utilization >= 4 GB

## Configure important host settings

* Reason:🧠 affect your services' performance🧠
* [ADDITIONAL ones](index.md#important-settings)

### Linux settings

* `sudo swapoff -a`
  * disable memory paging & swapping performance
    * Reason:🧠improve performance🧠
* increase the number of memory maps / AVAILABLE | OpenSearch
   ```bash
   # Edit the sysctl config file
   sudo vi /etc/sysctl.conf

   # Add a line to define the desired value
   # or change the value if the key exists,
   # and then save your changes.
   vm.max_map_count=262144

   # Reload the kernel parameters using sysctl
   sudo sysctl -p

   # Verify that the change was applied by checking the value
   cat /proc/sys/vm/max_map_count
   ```

### Windows settings
* | Windows workloads / use WSL through Docker Desktop

```bash
wsl -d docker-desktop
sysctl -w vm.max_map_count=262144
```   

## Run OpenSearch | Docker container
### ways
#### -- via -- `docker run`

1. Start OpenSearch in Docker.
    OpenSearch 2.12 or later requires that you set a custom admin password when starting
    For more information, see [Setting a custom admin password](#setting-a-custom-admin-password)
    
    ```bash
    
    ```
    Older versions do not include a password when starting:
    ```bash
    # This command maps ports 9200 and 9600, sets the discovery type to "single-node" and requests the newest image of OpenSearch
    docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" opensearchproject/opensearch:latest
    ```
1. After waiting a few minutes for OpenSearch to start, send a request to port `9200`. For versions earlier than 2.12, the default username and password are `admin`.
    ```bash
    curl https://localhost:9200 -ku admin:"<custom-admin-password>"
    ```
    {% include copy.html %}

    - You should get a response that looks like this:
      ```bash
      {
        "name" : "a937e018cee5",
        "cluster_name" : "docker-cluster",
        "cluster_uuid" : "GLAjAG6bTeWErFUy_d-CLw",
        "version" : {
          "distribution" : "opensearch",
          "number" : <version>,
          "build_type" : <build-type>,
          "build_hash" : <build-hash>,
          "build_date" : <build-date>,
          "build_snapshot" : false,
          "lucene_version" : <lucene-version>,
          "minimum_wire_compatibility_version" : "7.10.0",
          "minimum_index_compatibility_version" : "7.0.0"
        },
        "tagline" : "The OpenSearch Project: https://opensearch.org/"
      }
      ```
1. Before stopping the running container, display a list of all running containers and copy the container ID for the OpenSearch node you are testing. In the following example, the container ID is `a937e018cee5`:
    ```bash
    $ docker container ls
    CONTAINER ID   IMAGE                                 COMMAND                  CREATED          STATUS          PORTS                                                                NAMES
    a937e018cee5   opensearchproject/opensearch:latest   "./opensearch-docker…"   19 minutes ago   Up 19 minutes   0.0.0.0:9200->9200/tcp, 9300/tcp, 0.0.0.0:9600->9600/tcp, 9650/tcp   wonderful_boyd
    ```
1. Stop the running container by passing the container ID to `docker stop`.
    ```bash
    docker stop <containerId>
    ```
    {% include copy.html %}

Remember that `docker container ls` does not list stopped containers
If you would like to review stopped containers, use `docker container ls -a`
You can remove unneeded containers manually with `docker container rm <containerId_1> <containerId_2> <containerId_3> [...]` (pass all container IDs you want to stop, separated by spaces), or if you want to remove all stopped containers, you can use the shorter command `docker container prune`.
{: .tip}

#### -- via -- Docker Compose
 
* 👀[demo security configuration](/_security/configuration/demo-configuration.md)👀

### demo security configuration

* [here](/_security/configuration/demo-configuration.md)

### Setting a custom admin password

* | 
  * OpenSearch 2.12-,
    * ❌NOT need custom admin password❌
      * Reason:🧠ALREADY "opensearch.yml" configured
        * -> installation tool will NOT run🧠
  * OpenSearch 2.12+,
    * requirements
      * ⚠️custom admin password⚠️

* ⚠️if the password is NOT enough strong ->
  * error logging
  * OpenSearch quits⚠️
  
- Create an `.env` file in the same folder as your `docker-compose.yml` file with the
`OPENSEARCH_INITIAL_ADMIN_PASSWORD` and a strong password value.

### Password requirements

OpenSearch enforces strong password security by default, using the [`zxcvbn`](https://github.com/dropbox/zxcvbn) password strength estimation library developed by Dropbox. 

This library evaluates passwords based on entropy, rather than rigid complexity rules, using the following guidelines:

- **Focus on entropy, not only rules**
  - Instead of only adding numbers or special characters, prioritize overall unpredictability
  - Longer passwords composed of random words or characters provide higher entropy, making them more secure than short passwords that meet conventional complexity rules.

- **Avoid common patterns and dictionary words**
  - The `zxcvbn` library detects commonly used words, dates, sequences (for example, `1234` or `qwerty`), 
  and even predictable character substitutions (for example, `3` for `E`)
  - To ensure strong security, avoid using these patterns in your passwords.

- **Length matters**
  - Longer passwords generally offer greater security
  - For example, a passphrase such as `correct horse battery staple` is considered to be strong because of its length and randomness, 
  even though it does not contain special characters or numbers.

- **Unpredictability is key**
  - Whether you choose a string of random characters or a passphrase made of unrelated words, the key to password security is unpredictability
  - Higher entropy significantly increases the number of required guesses, making the password more resistant to attacks.

To learn more about `zxcvbn`, see [this Dropbox blog post](https://dropbox.tech/security/zxcvbn-realistic-password-strength-estimation)
To experiment with password strength, use [this demo](https://lowe.github.io/tryzxcvbn). 

OpenSearch uses the following default password requirements:

- Minimum password length: 8 characters.
- Maximum password length: 100 characters.
- No requirements for special characters, numbers, or uppercase letters.
- Passwords must be rated `strong` using the `zxcvbn` entropy-based calculation.

You can customize the default password requirements by updating the [password cluster settings]({{site.url}}{{site.baseurl}}/security/configuration/yaml/#password-settings).

### `docker run -e` vs Docker Compose's `environment`

* `parent1.child1` == `PARENT1_CHILD1`   
  * _Example:_ `opensearch.hosts` == `OPENSEARCH_HOSTS`

## Configure OpenSearch

* ".yml" /
  * by default, hosted | "/usr/share/opensearch/config/opensearch.yml"

- `discovery.type` == `single-node`
  - -> bootstrap checks do NOT fail / this single-node deployment

### Configuring basic security settings

Before making your OpenSearch cluster available to external hosts, it's a good idea to review the deployment's security configuration
* You may recall from the first [Sample docker-compose.yml](#sample-docker-composeyml) file that, unless disabled by setting `DISABLE_SECURITY_PLUGIN=true`
a bundled script will apply a default demo security configuration to the nodes in the cluster
* Because this configuration is used for demo purposes, the default usernames and passwords are known
* For that reason, we recommend that you create your own security configuration files and use `volumes` to pass these files to the containers
* For specific guidance on OpenSearch security settings, see [Security configuration]({{site.url}}{{site.baseurl}}/security/configuration/index/).

To use your own certificates in your configuration, add all of the necessary certificates to the volumes section of the compose file:
```yml
volumes:
  - ./root-ca.pem:/usr/share/opensearch/config/root-ca.pem
  - ./admin.pem:/usr/share/opensearch/config/admin.pem
  - ./admin-key.pem:/usr/share/opensearch/config/admin-key.pem
  - ./node1.pem:/usr/share/opensearch/config/node1.pem
  - ./node1-key.pem:/usr/share/opensearch/config/node1-key.pem
```
{% include copy.html %}

When you add TLS certificates to your OpenSearch nodes with Docker Compose volumes, 
you should also include a custom `opensearch.yml` file that defines those certificates
* For example:
```yml
volumes:
  - ./root-ca.pem:/usr/share/opensearch/config/root-ca.pem
  - ./admin.pem:/usr/share/opensearch/config/admin.pem
  - ./admin-key.pem:/usr/share/opensearch/config/admin-key.pem
  - ./node1.pem:/usr/share/opensearch/config/node1.pem
  - ./node1-key.pem:/usr/share/opensearch/config/node1-key.pem
  - ./custom-opensearch.yml:/usr/share/opensearch/config/opensearch.yml
```
{% include copy.html %}

Remember that the certificates you specify in your compose file must be the same 
as the certificates defined in your custom `opensearch.yml` file
You should replace the root, admin, and node certificates with your own
For more information see [Configure TLS certificates]({{site.url}}{{site.baseurl}}/security/configuration/tls).
```yml
plugins.security.ssl.transport.pemcert_filepath: node1.pem
plugins.security.ssl.transport.pemkey_filepath: node1-key.pem
plugins.security.ssl.transport.pemtrustedcas_filepath: root-ca.pem
plugins.security.ssl.http.pemcert_filepath: node1.pem
plugins.security.ssl.http.pemkey_filepath: node1-key.pem
plugins.security.ssl.http.pemtrustedcas_filepath: root-ca.pem
plugins.security.authcz.admin_dn:
  - CN=admin,OU=SSL,O=Test,L=Test,C=DE
```
{% include copy.html %}

After configuring security settings, your custom `opensearch.yml` file might look something like the following example, 
which adds TLS certificates and the distinguished name (DN) of the admin certificate, 
defines a few permissions, and enables verbose audit logging:
```yml
plugins.security.ssl.transport.pemcert_filepath: node1.pem
plugins.security.ssl.transport.pemkey_filepath: node1-key.pem
plugins.security.ssl.transport.pemtrustedcas_filepath: root-ca.pem
transport.ssl.enforce_hostname_verification: false
plugins.security.ssl.http.enabled: true
plugins.security.ssl.http.pemcert_filepath: node1.pem
plugins.security.ssl.http.pemkey_filepath: node1-key.pem
plugins.security.ssl.http.pemtrustedcas_filepath: root-ca.pem
plugins.security.allow_default_init_securityindex: true
plugins.security.authcz.admin_dn:
  - CN=A,OU=UNIT,O=ORG,L=TORONTO,ST=ONTARIO,C=CA
plugins.security.nodes_dn:
  - 'CN=N,OU=UNIT,O=ORG,L=TORONTO,ST=ONTARIO,C=CA'
plugins.security.audit.type: internal_opensearch
plugins.security.enable_snapshot_restore_privilege: true
plugins.security.check_snapshot_restore_write_privileges: true
plugins.security.restapi.roles_enabled: ["all_access", "security_rest_api_access"]
cluster.routing.allocation.disk.threshold_enabled: false
opendistro_security.audit.config.disabled_rest_categories: NONE
opendistro_security.audit.config.disabled_transport_categories: NONE
```
{% include copy.html %}

For a full list of settings, see [Security]({{site.url}}{{site.baseurl}}/security/configuration/index/).

Use the same process to specify a [Backend configuration]({{site.url}}{{site.baseurl}}/security/configuration/configuration/) in `/usr/share/opensearch/config/opensearch-security/config.yml`
as well as new internal users, roles, mappings, action groups, and tenants in their respective [YAML files]({{site.url}}{{site.baseurl}}/security/configuration/yaml/).

#### Complete Docker Compose example with custom configuration

After creating your own certificates, `internal_users.yml`, `roles.yml`, `roles_mapping.yml`, and the rest of the security configuration files, your `docker-compose.yaml` file should appear similar to the following:

```yaml
version: '3'
services:
  opensearch-node1:
    image: opensearchproject/opensearch:${OS_VER}
    container_name: opensearch-node1_${OS_VER}
    environment:
      - cluster.name=opensearch-cluster
      - node.name=opensearch-node1
      - discovery.seed_hosts=opensearch-node1,opensearch-node2,opensearch-node3
      - cluster.initial_master_nodes=opensearch-node1,opensearch-node2,opensearch-node3
      - bootstrap.memory_lock=true
      - "OPENSEARCH_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
      nofile:
        soft: 65536
        hard: 65536
    volumes:
      - ./opensearch.yml:/usr/share/opensearch/config/opensearch.yml
      - ./esnode.pem:/usr/share/opensearch/config/esnode.pem
      - ./esnode-key.pem:/usr/share/opensearch/config/esnode-key.pem
      - ./root-ca.pem:/usr/share/opensearch/config/root-ca.pem
      - ./kirk-key.pem:/usr/share/opensearch/config/kirk-key.pem
      - ./kirk.pem:/usr/share/opensearch/config/kirk.pem
      - ./config.yml:/usr/share/opensearch/config/opensearch-security/config.yml
      - ./roles_mapping.yml:/usr/share/opensearch/config/opensearch-security/roles_mapping.yml
      - ./roles.yml:/usr/share/opensearch/config/opensearch-security/roles.yml
      - ./action_groups.yml:/usr/share/opensearch/config/opensearch-security/action_groups.yml
      - ./allowlist.yml:/usr/share/opensearch/config/opensearch-security/allowlist.yml
      - ./audit.yml:/usr/share/opensearch/config/opensearch-security/audit.yml
      - ./internal_users.yml:/usr/share/opensearch/config/opensearch-security/internal_users.yml
      - ./nodes_dn.yml:/usr/share/opensearch/config/opensearch-security/nodes_dn.yml
      - ./tenants.yml:/usr/share/opensearch/config/opensearch-security/tenants.yml
      - ./whitelist.yml:/usr/share/opensearch/config/opensearch-security/whitelist.yml
    ports:
      - 9201:9200
      - 9600:9600
    networks:
      - opensearch-net

  opensearch-node2:
    image: opensearchproject/opensearch:${OS_VER}
    container_name: opensearch-node2_${OS_VER}
    environment:
      - cluster.name=opensearch-cluster
      - node.name=opensearch-node2
      - discovery.seed_hosts=opensearch-node1,opensearch-node2,opensearch-node3
      - cluster.initial_master_nodes=opensearch-node1,opensearch-node2,opensearch-node3
      - bootstrap.memory_lock=true
      - "OPENSEARCH_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
      nofile:
        soft: 65536
        hard: 65536
    volumes:
      - ./opensearch.yml:/usr/share/opensearch/config/opensearch.yml
      - ./esnode.pem:/usr/share/opensearch/config/esnode.pem
      - ./esnode-key.pem:/usr/share/opensearch/config/esnode-key.pem
      - ./root-ca.pem:/usr/share/opensearch/config/root-ca.pem
      - ./kirk-key.pem:/usr/share/opensearch/config/kirk-key.pem
      - ./kirk.pem:/usr/share/opensearch/config/kirk.pem
      - ./config.yml:/usr/share/opensearch/config/opensearch-security/config.yml
      - ./roles_mapping.yml:/usr/share/opensearch/config/opensearch-security/roles_mapping.yml
      - ./roles.yml:/usr/share/opensearch/config/opensearch-security/roles.yml
      - ./action_groups.yml:/usr/share/opensearch/config/opensearch-security/action_groups.yml
      - ./allowlist.yml:/usr/share/opensearch/config/opensearch-security/allowlist.yml
      - ./audit.yml:/usr/share/opensearch/config/opensearch-security/audit.yml
      - ./internal_users.yml:/usr/share/opensearch/config/opensearch-security/internal_users.yml
      - ./nodes_dn.yml:/usr/share/opensearch/config/opensearch-security/nodes_dn.yml
      - ./tenants.yml:/usr/share/opensearch/config/opensearch-security/tenants.yml
      - ./whitelist.yml:/usr/share/opensearch/config/opensearch-security/whitelist.yml
    ports:
      - 9200:9200
    networks:
      - opensearch-net

  opensearch-node3:
    image: opensearchproject/opensearch:${OS_VER}
    container_name: opensearch-node3_${OS_VER}
    environment:
      - cluster.name=opensearch-cluster
      - node.name=opensearch-node3
      - discovery.seed_hosts=opensearch-node1,opensearch-node2,opensearch-node3
      - cluster.initial_master_nodes=opensearch-node1,opensearch-node2,opensearch-node3
      - bootstrap.memory_lock=true
      - "OPENSEARCH_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
      nofile:
        soft: 65536
        hard: 65536
    volumes:
      - ./opensearch.yml:/usr/share/opensearch/config/opensearch.yml
      - ./esnode.pem:/usr/share/opensearch/config/esnode.pem
      - ./esnode-key.pem:/usr/share/opensearch/config/esnode-key.pem
      - ./root-ca.pem:/usr/share/opensearch/config/root-ca.pem
      - ./kirk-key.pem:/usr/share/opensearch/config/kirk-key.pem
      - ./kirk.pem:/usr/share/opensearch/config/kirk.pem
      - ./config.yml:/usr/share/opensearch/config/opensearch-security/config.yml
      - ./roles_mapping.yml:/usr/share/opensearch/config/opensearch-security/roles_mapping.yml
      - ./roles.yml:/usr/share/opensearch/config/opensearch-security/roles.yml
      - ./action_groups.yml:/usr/share/opensearch/config/opensearch-security/action_groups.yml
      - ./allowlist.yml:/usr/share/opensearch/config/opensearch-security/allowlist.yml
      - ./audit.yml:/usr/share/opensearch/config/opensearch-security/audit.yml
      - ./internal_users.yml:/usr/share/opensearch/config/opensearch-security/internal_users.yml
      - ./nodes_dn.yml:/usr/share/opensearch/config/opensearch-security/nodes_dn.yml
      - ./tenants.yml:/usr/share/opensearch/config/opensearch-security/tenants.yml
      - ./whitelist.yml:/usr/share/opensearch/config/opensearch-security/whitelist.yml
    ports:
      - 9202:9200
    networks:
      - opensearch-net

  opensearch-dashboards:
    image: opensearchproject/opensearch-dashboards:${OSD_VER}
    container_name: opensearch-dashboards_${OSD_VER}
    volumes:
      - ./opensearch_dashboards.yml:/usr/share/opensearch-dashboards/config/opensearch_dashboards.yml
      - ./opensearch_dashboards.crt:/usr/share/opensearch-dashboards/config/opensearch_dashboards.crt
      - ./opensearch_dashboards.key:/usr/share/opensearch-dashboards/config/opensearch_dashboards.key
    ports:
      - 5601:5601
    expose:
      - "5601"
    environment:
      OPENSEARCH_HOSTS: '["https://opensearch-node1:9200", "https://opensearch-node2:9200", "https://opensearch-node3:9200" ]'
    networks:
      - opensearch-net
    depends_on:
      - opensearch-node1
      - opensearch-node2
      - opensearch-node3

networks:
  opensearch-net:

```
{% include copy.html %}

Use Docker Compose to start the cluster:
```bash
docker compose up -d
```
{% include copy.html %}

The password for the `admin` user provided in the `.env` file is overridden by the password provided in the `internal_users.yml` file.
{: .note}

### Working with plugins

To use the OpenSearch image with a custom plugin, you must first create a [`Dockerfile`](https://docs.docker.com/engine/reference/builder/). Review the official Docker documentation for information about creating a Dockerfile.
```
FROM opensearchproject/opensearch:latest
RUN /usr/share/opensearch/bin/opensearch-plugin install --batch <pluginId>
```

Then run the following commands:
```bash
# Build an image from a Dockerfile
docker build --tag=opensearch-custom-plugin .
# Start the container from the custom image
docker run -p 9200:9200 -p 9600:9600 -v /usr/share/opensearch/data opensearch-custom-plugin
```

Alternatively, you might want to remove a plugin from an image before deploying it. This example Dockerfile removes the Security plugin:
```
FROM opensearchproject/opensearch:latest
RUN /usr/share/opensearch/bin/opensearch-plugin remove opensearch-security
```
{% include copy.html %}

You can also use a Dockerfile to pass your own certificates for use with the [Security plugin]({{site.url}}{{site.baseurl}}/security/):
```
FROM opensearchproject/opensearch:latest
COPY --chown=opensearch:opensearch opensearch.yml /usr/share/opensearch/config/
COPY --chown=opensearch:opensearch my-key-file.pem /usr/share/opensearch/config/
COPY --chown=opensearch:opensearch my-certificate-chain.pem /usr/share/opensearch/config/
COPY --chown=opensearch:opensearch my-root-cas.pem /usr/share/opensearch/config/
```
{% include copy.html %}

## Related links

- [OpenSearch configuration]({{site.url}}{{site.baseurl}}/install-and-configure/configuring-opensearch/)
- [Performance analyzer]({{site.url}}{{site.baseurl}}/monitoring-plugins/pa/index/)
- [Install and configure OpenSearch Dashboards]({{site.url}}{{site.baseurl}}/install-and-configure/install-dashboards/index/)
- [About Security in OpenSearch]({{site.url}}{{site.baseurl}}/security/index/)
