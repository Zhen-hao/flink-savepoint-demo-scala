package demo


import java.io.File
import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
  * Created by zli01 on 16/08/2016.
  */


class JobSettings(configFilePath: String) extends Serializable {

  val log = LoggerFactory.getLogger(this.getClass.getName)
  val config = ConfigFactory.parseFile(new File(configFilePath))

  val sensorTopic = config.getString("kafka.topic")
  val consumerID = config.getString("kafka.group-id")

  val brokerList = config.getString("kafka.brokers")
  val zookeeper = config.getString("kafka.zookeeper")

  val kafkaProperties = new Properties()
  kafkaProperties.setProperty("bootstrap.servers", brokerList)
  kafkaProperties.setProperty("zookeeper.connect", zookeeper)
  kafkaProperties.setProperty("group.id", consumerID)

}
