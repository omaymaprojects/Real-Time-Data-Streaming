public class Main {
    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Set up the Kafka source
        KafkaSource<Weather> source = KafkaSource.<Weather>builder()
            .setBootstrapServers("kafka:9092")
            .setTopics("weather")
            .setGroupId("flink-consumer-group")
            .setValueOnlyDeserializer(new WeatherDeserializer())
            .build();

        // Consume from Kafka and process the data
        DataStream<Weather> weatherStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        // Group by city, create a tumbling window, and calculate the average temperature
        DataStream<Tuple2<String, Double>> avgTempStream = weatherStream
            .keyBy(Weather::getCity)
            .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
            .aggregate(new AverageAggregate());

        // Print the results to the console
        avgTempStream.print();

        // Execute the Flink job
        env.execute("Flink Weather Aggregator");
    }
}
