---
layout: default
title: OpenSearch Benchmark
nav_order: 1
has_children: false
nav_exclude: true
has_toc: false
permalink: /benchmark/
redirect_from:
  - /benchmark/index/
  - /benchmark/tutorials/index/
tutorial_cards:
  - heading: "Get started with OpenSearch Benchmark"
    description: "Run your first OpenSearch Benchmark workload and receive performance metrics"
    link: "/benchmark/quickstart/"
  - heading: "Choosing a workload"
    description: "Choose a benchmark workload based on your cluster's use case"
    link: "/benchmark/user-guide/understanding-workloads/choosing-a-workload/"
more_cards:
  - heading: "User guide"
    description: "Learn how to benchmark the performance of your cluster"
    link: "/benchmark/user-guide/index/"
  - heading: "Reference"
    description: "Learn about OpenSearch Benchmark commands and options"
    link: "/benchmark/reference/index/"
items:
  - heading: "Install and configure OpenSearch Benchmark"
    description: "Install OpenSearch Benchmark and configure your experience"
    link: "/benchmark/user-guide/install-and-configure/installing-benchmark/"
  - heading: "Run a workload"
    description: "Run a workload and receive performance metrics"
    link: "/benchmark/user-guide/working-with-workloads/running-workloads/"
  - heading: "Analyze performance metrics"
    description: "View your benchmark report and analyze your metrics"
    link: "/benchmark/user-guide/understanding-results/summary-reports/"
---

# OpenSearch Benchmark

* used terminology
  * follows OpenSearch Benchmark `2.X`

* [version history](version-history)
* [migration assistance](migration-assistance)

* OpenSearch Benchmark
  * == macrobenchmark utility 
    * provided -- by the -- [OpenSearch Project](https://github.com/opensearch-project)
  * uses
    * gather OpenSearch cluster's performance metrics
  * use cases
    - enable you to decide when to upgrade your cluster
    - measure cluster impact / EACH change

## Get started

* see ☝️items listed☝️


## Resources

* TODO:
{% include cards.html cards=page.tutorial_cards %}
{% include cards.html cards=page.more_cards %}
