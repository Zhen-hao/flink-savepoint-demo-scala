package demo


import java.io.File

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
  * Created by zli01 on 16/08/2016.
  */


class JobSettings(configFilePath: String) extends Serializable {

  val log = LoggerFactory.getLogger(this.getClass.getName)
  val config = ConfigFactory.parseFile(new File(configFilePath))

  val groupID = config.getString("sas-poc.groupID")
  val stateBackend = config.getString("sas-poc.stateBackend")

  val kafkaProducerEnrichedTopic = config.getString("sas-poc.topics.enriched")

  val outputSchema = config.getString("sas-poc.schema.output")

  val kafkaConsumerConfView = Map(
    "metadata.broker.list" -> "3.kafkas.prod.slu.faw.bskyb.com:9092,4.kafkas.prod.slu.faw.bskyb.com:9092,1.kafkas.prod.slu.faw.bskyb.com:9092",
    "group.id" -> "kafka-poc-webstream-enrich-view",
    "zookeeper.connect" ->  "1.kafkas.prod.slu.faw.bskyb.com:2181",
    "schema.registry.url" -> "http://schemaregistry.faw.bskyb.com:8081")

}
