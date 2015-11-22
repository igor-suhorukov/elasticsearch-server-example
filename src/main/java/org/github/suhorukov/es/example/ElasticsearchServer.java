package org.github.suhorukov.es.example;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 */
public class ElasticsearchServer {
    public static void main(String[] args) throws Exception{

        int durationInSeconds = args.length == 0 ? 360 : Integer.parseInt(args[0]);

        String template;
        try(InputStream templateStream = new URL("https://raw.githubusercontent.com/logstash-plugins/logstash-output-elasticsearch/master/lib/logstash/outputs/elasticsearch/elasticsearch-template.json").openStream()){
            template = IOUtils.toString(templateStream);
        }

        Node elasticsearchServer = NodeBuilder.nodeBuilder().settings(ImmutableSettings.settingsBuilder().put("http.cors.enabled","true")).clusterName("elasticsearchServer").data(true).build();
        Node node = elasticsearchServer.start();
        node.client().admin().indices().preparePutTemplate("logstash").setSource(template).get();
        System.out.println("ES STARTED");

        Thread.sleep(TimeUnit.SECONDS.toMillis(durationInSeconds));
    }
}
