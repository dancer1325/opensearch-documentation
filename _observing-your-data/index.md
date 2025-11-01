---
layout: default
title: Observability
nav_order: 1
has_children: true
nav_exclude: true
permalink: /observing-your-data/
redirect_from:
  - /observability-plugin/index/
  - /observing-your-data/index/
---

# Observability

* Observability
  * == pluginS + applications /
    * let you, about data-driven events -- via -- Piped Processing Language, 
      * visualize
      * explore,
      * discover,
      * query

* recommended workflow
  1. explore data | certain timeframe, -- via -- [Piped Processing Language](../_search-plugins/sql/ppl/index)
  2. turn data-driven events -- , via [event analytics](event-analytics), into -- visualizations  
    ![Sample Event Analytics View](../images/event-analytics.png)
  3. if you want to compare data -> create [operational panels](operational-panels) & add visualizations 
    ![Sample Operational Panel View](../images/operational-panel.png)
  4. if you want to transform unstructured log data -> use [log analytics](log-ingestion) 
  5. if you want to create traces -> use [trace analytics](trace/index)
    ![Sample Trace Analytics View](../images/observability-trace.png)
  6. if you want to combine visualizationS + code blockS / share them -> use [notebooks](notebooks) 
    ![Sample Notebooks View](../images/notebooks.png)
