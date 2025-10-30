---
layout: default
title: Snapshots
nav_order: 5
has_children: true
parent: Availability and recovery
redirect_from:
  - /opensearch/snapshots/
  - /opensearch/snapshots/index/
  - /tuning-your-cluster/availability-and-recovery/snapshots/
has_toc: false
---

# Snapshots

* Snapshots
  * == 👀backups of a cluster's indexes & state👀
  * uses
    * **Recovering from failure**
      * _Example:_ if cluster health goes red -> you might restore the red indexes -- from a -- snapshot
    * **Migrating from one cluster to another**
      * _Example:_ if you're moving from a POC to a production cluster -> you might 
        * take a snapshot of the former
        * | later, restore it 
  * [snapshot API](snapshot-restore)
  * [snapshot management](snapshot-management)
    * == feature /
      * enables AUTOMATICALLY create a snapshot

* cluster's state
  * == cluster settings + node information + index metadata (mappings, settings, or templates) + shard allocation
