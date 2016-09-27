package demo

import java.util.Properties

import demo.data.{DataPoint, DoubleNumber}
import demo.sinks.InfluxDBSink
import demo.udfs.UDFHelper._
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
/**
  * Created by zhenhao.li on 17/09/16.
  */
class Demo {

    def run(sensorTopic: String, consumerID: String, measurementUDF: DataStream[(String, Long, Double)] => DataStream[DataPoint[DoubleNumber]], measurementName: String) = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        /*******    Check Point                *************/
        //env.enableCheckpointing(5000) // checkpoint every 5000 msecs

        /*******    Process Time vs Event Time   ***********/
        //env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)


        val properties = new Properties()
        properties.setProperty("bootstrap.servers", "localhost:9092")
        properties.setProperty("zookeeper.connect", "localhost:2181")
        properties.setProperty("group.id", consumerID)

        val rawStream = env
          .addSource(new FlinkKafkaConsumer09[String](sensorTopic, new SimpleStringSchema(), properties))

        val keyedStream = rawStream.map(string => string.split(","))
                           .map(array => ("key", array(0).toLong, array(1).toDouble))

        /*******    Extract Eevent Time          *************/
        val keyedStreamWithEventTime = keyedStream.assignAscendingTimestamps(_._2)

        //pointStreamWithTime.print

        val aggregatedStream =
            keyedStreamWithEventTime.applyUDF(measurementUDF)

        aggregatedStream.addSink(new InfluxDBSink[DataPoint[DoubleNumber]](measurementName))


        env.execute("Consuming from the Kafka topic " + sensorTopic + " with groupID " + consumerID + " and writing aggregations to measurement name" + measurementName)

    }

}
