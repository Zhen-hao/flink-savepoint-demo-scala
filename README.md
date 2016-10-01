# flink-savepoint-demo-scala

This demo requires you to use InfluxDB and Grafana; see http://data-artisans.com/robust-stream-processing-flink-walkthrough/. The InfluxDB sink in this repo is built on top of that in https://github.com/dataArtisans/oscon. 
We also assume you have Kafka 0.9 or 0.10 installed and know how to use it. 
The purpose of this demo is to show how to use the savepoint of Flink to do easy continuous integration. 
There are four processing components in the code. The logic of them is expressed in the UDF object as higher order functions. 


## Quick start
* You can start with the first 3 components by commenting out the sumInOnePerioRolling2 parts in both UDF and Demo. 
* After building the jar and running it on a cluster for a while, use the savepoint command to get the name (Flink calls it a path) of the savepoint and then stop the running job. 
* Then uncomment the sumInOnePerioRolling2 parts and rebuild the jar and summit it to the cluster with the savepoint from the previous step. You will observe that the old components continue to run as if no change has happened, and the new component has been successfully added.  

### Data source
The Kafka topic expects pure string messages that represent (time:[long],value:[double]) pairs. For example,
1475182679053,0.5735764363510459

For a quick start, you can use the EventsRunner in https://github.com/Zhen-hao/kafka-fsm/tree/github to produce those pairs to the Kafka topic. It uses a Akka finite state machine to simulate event logs with lateness. 
