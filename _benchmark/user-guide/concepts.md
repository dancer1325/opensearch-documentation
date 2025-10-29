---
layout: default
title: Concepts
nav_order: 3
parent: User guide
has_toc: false
redirect_from: 
  - /benchmark/user-guide/concepts/
---

# Concepts

* goal
  * Opensearch Bechmark concepts /
    * enables you effectively, about your benchmarks to,
      * design, 
      * run,
      * analyze 

## Benchmark architecture

* OpenSearch Benchmark running -- against a -- local host

  ![Benchmark workflow](/images/benchmark/osb-workflow.jpg)

## Core concepts and definitions

- **Workload**
  - == 💡>=1 benchmarking scenarios 💡/ 
    - perform a benchmark -- , via specific document corpus, against -- your cluster 
      - document corpus == indexes + data files + operations / invoked | run the workload
  - == 👀
    - \>=1 data streams / ingested | indexes +
    - queries & operations / invoked -- as part of the -- benchmark👀
  - 👀ways to list the available workloads👀
    - `opensearch-benchmark list workloads`
    - [OpenSearch Benchmark Workloads repository](https://github.com/opensearch-project/opensearch-benchmark-workloads/)
  - ['s anatomy](understanding-workloads/anatomy-of-a-workload)
  - [how to build a custom workload](working-with-workloads/creating-custom-workloads)

- **Pipeline**
  - == 👀stepS before and after a workload runs👀 /
    - determines benchmark results
    - supported pipelines
      - `from-sources`
        - steps
          - builds and provisions OpenSearch
          - runs a benchmark
          - publishes the benchmark results
      - `from-distribution`
        - steps
          - downloads an OpenSearch distribution,
          - provisions it,
          - runs a benchmark,
          - publishes the benchmark results
      - `benchmark-only`
        - default
        - requirements
          - OpenSearch instance running
        - steps
          - run a benchmark | that OpenSearch instance,
          - publishes the benchmark results

- **Test**
  - == 1! invocation of the OpenSearch Benchmark binary

## Test concepts

At the end of each test, OpenSearch Benchmark produces a table that summarizes the following: 

  - [Processing time](#processing-time)
  - [Took time](#took-time)
  - [Service time](#service-time)
  - [Latency](#latency)
  - [Throughput](#throughput)

The following diagram illustrates how each component of the table is measured during the lifecycle of a request involving the OpenSearch cluster, the OpenSearch client, and OpenSearch Benchmark.

<img src="{{site.url}}{{site.baseurl}}/images/benchmark/concepts-diagram.png" alt="">

### Differences between OpenSearch Benchmark and a traditional client-server system

While the definition for _throughput_ remains consistent with other client-server systems, the definitions for `service time` and `latency` differ from most client-server systems in the context of OpenSearch Benchmark. The following table compares the OpenSearch Benchmark definition of service time and latency versus the common definitions for a client-server system.

| Metric | Common definition | **OpenSearch Benchmark definition**	|
| :--- | :--- |:--- |
| **Throughput** | The number of operations completed in a given period of time.	| The number of operations completed in a given period of time. |
| **Service time**	| The amount of time that the server takes to process a request, from the point it receives the request to the point the response is returned. It includes the time spent waiting in server-side queues but _excludes_ network latency, load balancer overhead, and deserialization/serialization. | The amount of time that it takes for `opensearch-py` to send a request and receive a response from the OpenSearch cluster. It includes the amount of time that it takes for the server to process a request and also _includes_ network latency, load balancer overhead, and deserialization/serialization.  |
| **Latency** | The total amount of time, including the service time and the amount of time that the request waits before responding. | Based on the `target-throughput` set by the user, the total amount of time that the request waits before receiving the response, in addition to any other delays that occur before the request is sent. |

For more information about service time and latency in OpenSearch Benchmark, see the [Service time](#service-time) and [Latency](#latency) sections.


### Processing time

*Processing time* accounts for any extra overhead tasks that OpenSearch Benchmark performs during the lifecycle of a request, such as setting up a request context manager or calling a method to pass the request to the OpenSearch client. This is in contrast to *service time*, which only accounts for the difference between when a request is sent and when the OpenSearch client receives the response.

### Took time

*Took time* measures the amount of time that the cluster spends processing a request on the server side. It does not include the time taken for the request to transit from the client to the cluster or for the response to transit from the cluster to the client.

### Service time


OpenSearch Benchmark does not have insight into how long OpenSearch takes to process a request, apart from extracting the [took time](#took-time) for the request. It makes function calls to `opensearch-py` to communicate with an OpenSearch cluster. 

OpenSearch Benchmark measures *service time*, which is the amount of time between when the `opensearch-py` client sends a request to and receives a response from the OpenSearch cluster. Unlike the traditional definition of service time, the OpenSearch Benchmark definition includes overhead, such as network latency, load balancer overhead, or deserialization/serialization. The following image shows the differences between the traditional definition and the OpenSearch Benchmark definition.

<img src="{{site.url}}{{site.baseurl}}/images/benchmark/service-time.png" alt="">

### Latency

*Latency* measures the total time that the request waits before receiving the response as well as any delays that occur prior to sending the request. In most circumstances, latency is measured in the same way as service time, unless you are testing in [throughput-throttled mode]({{site.url}}{{site.baseurl}}/benchmark/user-guide/target-throughput/). In this case, latency is measured as service time plus the time that the request spends waiting in the queue.


### Throughput

**Throughput** measures the rate at which OpenSearch Benchmark issues requests, assuming that responses will be returned instantaneously. 



