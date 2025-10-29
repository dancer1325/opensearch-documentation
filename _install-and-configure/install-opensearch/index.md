---
layout: default
title: Installing OpenSearch
nav_order: 2
has_children: true
redirect_from:
  - /opensearch/install/
  - /opensearch/install/compatibility/
  - /opensearch/install/important-settings/
  - /install-and-configure/index/
  - /opensearch/install/index/
  - /install-and-configure/install-opensearch/
---

# Installing OpenSearch

* goal
  * how to install OpenSearch | your host /
    * which [ports to open](#network-requirements)
    * which [important settings](#important-settings)

* [Compatible OS](../os-comp)

## Installation steps

* -- depend on the -- deployment method
  - [Docker](docker)
  - [Helm](helm)
  - [Tarball](tar)
  - [RPM](rpm)
  - [Debian](debian)
  - [Ansible playbook](ansible)
  - [Windows](windows)


## File system recommendations

* | production workflow's node storage
  * ❌NOT use a network file system❌  
    * Reason:🧠cluster's performance -- due to -- network conditions (latency or limited throughput) OR read/write speeds OR ...🧠
  * use solid-state drives (SSDs) / installed | host

## Java compatibility

* OpenSearch distribution for Linux
  * ships -- with a -- compatible [Adoptium JDK](https://adoptium.net/) version | `jdk` directory
    * `./jdk/bin/java -version`
      * check JDK version

OpenSearch Version | Compatible Java Versions | Bundled Java Version
:---------- | :-------- | :-----------
1.0--1.2.x    | 11, 15     | 15.0.1+9
1.3.x          | 8, 11, 14  | 11.0.25+9
2.0.0--2.11.x    | 11, 17     | 17.0.2+8
2.12.0+        | 11, 17, 21 | 21.0.5+11
3.2.0+        | 11, 17, 21 | 24.0.2+12


* 
  ```
  # 1. -- via -- OPENSEARCH_JAVA_HOME
  export OPENSEARCH_JAVA_HOME=/path/to/opensearch-{{site.opensearch_version}}/jdk
  
  # 2. -- via -- JAVA_HOME
  export JAVA_HOME=/path/to/opensearch-{{site.opensearch_version}}/jdk
  ```
  * use a DIFFERENT Java installation

## Network requirements

TCP Port number | OpenSearch component
:--- | :--- 
443 | OpenSearch Dashboards in AWS OpenSearch Service with encryption in transit (TLS)
5601 | OpenSearch Dashboards
9200 | OpenSearch REST API
9300 | Node communication and transport (internal), cross cluster search
9600 | Performance Analyzer

* ❌NO use UDP ports❌


## Important settings

* | production workloads /
  * `vm.max_map_count` >= `262144`
    * | Linux, 
      * see [Linux setting](https://www.kernel.org/doc/Documentation/sysctl/vm.txt)
      * `cat /proc/sys/vm/max_map_count` 
        * check the current value
      * steps to modify the value
        * | "/etc/sysctl.conf", add 

          ```
          vm.max_map_count=262144
          ```

        * `sudo sysctl -p`
    * | Windows
      * steps

        ```bash
        wsl -d docker-desktop
        sysctl -w vm.max_map_count=262144
        ```

* | production clusters
  * `bootstrap.memory_lock=true`
    * disables swapping (data | RAM is moved -- to -- hard disk) + `memlock`
      * -> ⚠️decrease performance and stability⚠️
    * -> JVM to reserve ANY memory / it needs
      * by default, 1 gigabyte (GB) Class Metadata native memory reservation 
        * [Java SE Hotspot VM Garbage Collection Tuning Guide](https://docs.oracle.com/javase/9/gctuning/other-considerations.htm#JSGCT-GUID-B29C9153-3530-4C15-9154-E74F44E3DAD9) 
      * Problems:
        * Problem1: + Java heap, NO VMs' native memory / fit these requirements
          * Solution: 👀
            * limit the reserved memory size -- via -- `-XX:CompressedClassSpaceSize` OR `-XX:MaxMetaspaceSize`
            * set the Java heap's size / enough system memory👀

- `OPENSEARCH_JAVA_OPTS=-Xms512m -Xmx512m`
  - Java heap's size /
    - use `-Xms` & `-Xmx` notation
  - recommendation
    - 1/ system RAM
  - by default, 
    - heap memory allocation == `-Xms1g -Xmx1g` /
      - ⚠️precedence over configurations -- via -- percentage notation (`-XX:MinRAMPercentage`, `-XX:MaxRAMPercentage`)⚠️
        - _Example:_ if you set `OPENSEARCH_JAVA_OPTS=-XX:MinRAMPercentage=30 -XX:MaxRAMPercentage=70` -> the predefined `-Xms1g -Xmx1g` values override these settings

- `nofile 65536`
  - == OpenSearch user can ONLY open 65536 files 

- `port 9600`
  - allows you to 
    - access Performance Analyzer | port 9600

* ❌NOT declare the same JVM options | MULTIPLE locations❌
  * Reason: 🧠it can cause unexpected behavior OR failure of the OpenSearch service to start🧠
  * _Example:_ if you declare `OPENSEARCH_JAVA_OPTS=-Xms3g -Xmx3g` as environment variable -> comment out | `config/jvm.options`

## Important system properties

* | `config/jvm.options` OR CL's argument `-D` `OPENSEARCH_JAVA_OPTS`

Property | Description
:---------- | :-------- 
`opensearch.xcontent.string.length.max=<value>` | By default, OpenSearch does not impose any limits on the maximum length of the JSON/YAML/CBOR/Smile string fields. To protect your cluster against potential distributed denial-of-service (DDoS) or memory issues, you can set the `opensearch.xcontent.string.length.max` system property to a reasonable limit (the maximum is 2,147,483,647), for example, `-Dopensearch.xcontent.string.length.max=5000000`.  | 
`opensearch.xcontent.fast_double_writer=[true|false]` | By default, OpenSearch serializes floating-point numbers using the default implementation provided by the Java Runtime Environment. Set this value to `true` to use the Schubfach algorithm, which is faster but may lead to small differences in precision. Default is `false`. |
`opensearch.xcontent.name.length.max=<value>` | By default, OpenSearch does not impose any limits on the maximum length of the JSON/YAML/CBOR/Smile field names. To protect your cluster against potential DDoS or memory issues, you can set the `opensearch.xcontent.name.length.max` system property to a reasonable limit (the maximum is 2,147,483,647), for example, `-Dopensearch.xcontent.name.length.max=50000`. |
`opensearch.xcontent.depth.max=<value>` | By default, OpenSearch does not impose any limits on the maximum nesting depth for JSON/YAML/CBOR/Smile documents. To protect your cluster against potential DDoS or memory issues, you can set the `opensearch.xcontent.depth.max` system property to a reasonable limit (the maximum is 2,147,483,647), for example, `-Dopensearch.xcontent.depth.max=1000`. |
`opensearch.xcontent.codepoint.max=<value>` | By default, OpenSearch imposes a limit of `52428800` on the maximum size of the YAML documents (in code points). To protect your cluster against potential DDoS or memory issues, you can change the `opensearch.xcontent.codepoint.max` system property to a reasonable limit (the maximum is 2,147,483,647). For example, `-Dopensearch.xcontent.codepoint.max=5000000`. |
