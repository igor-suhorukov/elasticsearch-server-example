@Grab(group='org.elasticsearch', module='elasticsearch', version='1.1.1')
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.util.concurrent.TimeUnit;
        
int durationInSeconds = args.length == 0 ? 3600 : Integer.parseInt(this.args[0]);

String template;
InputStream templateStream = new URL("https://raw.githubusercontent.com/logstash-plugins/logstash-output-elasticsearch/master/lib/logstash/outputs/elasticsearch/elasticsearch-template.json").openStream()
try{
    template = new String(IOUtils.readFully(templateStream, -1, true));
} finally{
    templateStream.close()
}

Node elasticsearchServer = NodeBuilder.nodeBuilder().settings(ImmutableSettings.settingsBuilder().put("http.cors.enabled","true")).clusterName("elasticsearchServer").data(true).build();
Node node = elasticsearchServer.start();
node.client().admin().indices().preparePutTemplate("logstash").setSource(template).get();
System.out.println("ES STARTED");

Thread.sleep(TimeUnit.SECONDS.toMillis(durationInSeconds));
