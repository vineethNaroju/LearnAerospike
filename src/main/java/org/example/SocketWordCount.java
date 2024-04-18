package org.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.List;

public class SocketWordCount {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World !");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> txt = env.fromData(List.of("hello", "world", "123"));

        env.disableOperatorChaining();

        DataStream<Tuple2<Character, Integer>> dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap(new FlatMapFunction<String, Tuple2<Character, Integer>>() {
                    @Override
                    public void flatMap(String str, Collector<Tuple2<Character, Integer>> collector) throws Exception {
                        for(String word : str.split("")) {
                            for(int i=0; i<word.length(); i++) {
                                collector.collect(new Tuple2<>(word.charAt(i), 1));
                            }
                        }
                    }
                })
                .keyBy(val -> val.f0)
                .window(TumblingProcessingTimeWindows.of(Duration.ofSeconds(5)))
                .sum(1);

        dataStream.print();

        // System.out.println(env.getStreamGraph().getStreamingPlanAsJSON());

        env.execute("socket word count");
    }
}
