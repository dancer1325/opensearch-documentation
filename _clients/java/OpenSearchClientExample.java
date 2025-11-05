import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.base.RestClientTransport;
import org.opensearch.client.base.Transport;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._global.IndexRequest;
import org.opensearch.client.opensearch._global.IndexResponse;
import org.opensearch.client.opensearch._global.SearchResponse;
import org.opensearch.client.opensearch.indices.*;
import org.opensearch.client.opensearch.indices.put_settings.IndexSettingsBody;

import java.io.IOException;

public class OpenSearchClientExample {
  public static void main(String[] args) {
    RestClient restClient = null;
    try{
      System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
      System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

      //Only for demo purposes. Don't specify your credentials in code.
      final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials("admin", "admin"));

      // initialize the client with SSL and TLS enabled
      restClient = RestClient.builder(new HttpHost("localhost", 9200, "https")).
        setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
          @Override
          public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
          return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
          }
        }).build();
      Transport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
      OpenSearchClient client = new OpenSearchClient(transport);

      // x-4. create an index
      String index = "sample-index";
      CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index(index).build();
      client.indices().create(createIndexRequest);

      // add some settings to the index
      IndexSettings indexSettings = new IndexSettings.Builder().autoExpandReplicas("0-all").build();
      IndexSettingsBody settingsBody = new IndexSettingsBody.Builder().settings(indexSettings).build();
      PutSettingsRequest putSettingsRequest = new PutSettingsRequest.Builder().index(index).value(settingsBody).build();
      client.indices().putSettings(putSettingsRequest);

      // x-3. index some data    == add document
      IndexData indexData = new IndexData("first_name", "Bruce");
      IndexRequest<IndexData> indexRequest = new IndexRequest.Builder<IndexData>().index(index).id("1").document(indexData).build();
      client.index(indexRequest);

      // x-2. search for document
      SearchResponse<IndexData> searchResponse = client.search(s -> s.index(index), IndexData.class);
      for (int i = 0; i< searchResponse.hits().hits().size(); i++) {
        System.out.println(searchResponse.hits().hits().get(i).source());
      }

      // x-1. delete the document
      client.delete(b -> b.index(index).id("1"));

      // x. delete the index
      DeleteIndexRequest deleteIndexRequest = new DeleteRequest.Builder().index(index).build();
      DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);

    } catch (IOException e){
      System.out.println(e.toString());
    } finally {
      try {
        if (restClient != null) {
          restClient.close();
        }
      } catch (IOException e) {
        System.out.println(e.toString());
      }
    }
  }
}
