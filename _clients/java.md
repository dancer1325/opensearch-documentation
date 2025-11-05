---
layout: default
title: Java client
nav_order: 30
---

# Java client

* OpenSearch Java client
  * allows you to
    * 👀interact -- , through Java methods & data structures, with -- your OpenSearch clusters👀
      * != HTTP methods OR raw JSON
* [MORE](https://github.com/dancer1325/opensearch-java)

## ways to install the OpenSearch Java client
### -- via -- Apache HttpClient 5 Transport

* `ApacheHttpClient5TransportBuilder` 
  * == default transport /
    * built-in

* ways to install
  * -- via -- Maven

    ```xml
    <dependency>
      <groupId>org.opensearch.client</groupId>
      <artifactId>opensearch-java</artifactId>
      <version>3.0.0</version>
    </dependency>
    
    <dependency>
      <groupId>org.apache.httpcomponents.client5</groupId>
      <artifactId>httpclient5</artifactId>
      <version>5.2.1</version>
    </dependency>
    ```

  * -- via --  Gradle

    ```
    dependencies {
      implementation 'org.opensearch.client:opensearch-java:3.0.0'
      implementation 'org.apache.httpcomponents.client5:httpclient5:5.2.1'
    }
    ```


### -- via -- RestClient Transport

* ways to install
  * -- via -- Maven

    ```xml
    <dependency>
      <groupId>org.opensearch.client</groupId>
      <artifactId>opensearch-rest-client</artifactId>
      <version>{{site.opensearch_version}}</version>
    </dependency>
    
    <dependency>
      <groupId>org.opensearch.client</groupId>
      <artifactId>opensearch-java</artifactId>
      <version>2.6.0</version>
    </dependency>
    ```
  * -- via -- Gradle

    ```
    dependencies {
      implementation 'org.opensearch.client:opensearch-rest-client:{{site.opensearch_version}}'
      implementation 'org.opensearch.client:opensearch-java:2.6.0'
    }
    ```

## Security

* steps
  * BEFORE using the REST client | your Java application
    * 👀configure the application's truststore / connect -- to the -- Security plugin👀
      * if you are using 
        * self-signed certificates OR demo configurations -> create a custom truststore and add in root authority certificates.

          ```bash
          keytool -import <path-to-cert> -alias <alias-to-call-cert> -keystore <truststore-name>
          ```
        * trusted Certificate Authority (CA)'s certificates -> ❌NOT need to configure the truststore❌
    * point your Java client -- to the -- truststore 
    * set basic authentication credentials / can access a secure cluster

* Problems
  * [common issues](/opensearch-documentation/_troubleshoot/index.md)
  * [troubleshoot TLS](/opensearch-documentation/_troubleshoot/tls.md)

## Initializing the client / SSL & TLS enabled
### -- via -- Apache HttpClient 5 Transport

* TODO: This code example uses basic credentials that come with the default OpenSearch configuration
* If you’re using the Java client with your own OpenSearch cluster, be sure to change the code so that it uses your own credentials.

The following sample code initializes a client with SSL and TLS enabled:


```java
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.function.Factory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.reactor.ssl.TlsDetails;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;

public class OpenSearchClientExample {
  public static void main(String[] args) throws Exception {
    System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
    System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

    final HttpHost host = new HttpHost("https", "localhost", 9200);
    final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    // Only for demo purposes. Don't specify your credentials in code.
    credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials("admin", "admin".toCharArray()));

    final SSLContext sslcontext = SSLContextBuilder
      .create()
      .loadTrustMaterial(null, (chains, authType) -> true)
      .build();

    final ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(host);
    builder.setHttpClientConfigCallback(httpClientBuilder -> {
      final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
        .setSslContext(sslcontext)
        // See https://issues.apache.org/jira/browse/HTTPCLIENT-2219
        .setTlsDetailsFactory(new Factory<SSLEngine, TlsDetails>() {
          @Override
          public TlsDetails create(final SSLEngine sslEngine) {
            return new TlsDetails(sslEngine.getSession(), sslEngine.getApplicationProtocol());
          }
        })
        .build();

      final PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder
        .create()
        .setTlsStrategy(tlsStrategy)
        .build();

      return httpClientBuilder
        .setDefaultCredentialsProvider(credentialsProvider)
        .setConnectionManager(connectionManager);
    });

    final OpenSearchTransport transport = builder.build();
    OpenSearchClient client = new OpenSearchClient(transport);
  }
}
```

### -- via -- RestClient Transport

This code example uses basic credentials that come with the default OpenSearch configuration
If you’re using the Java client with your own OpenSearch cluster, be sure to change the code so that it uses your own credentials.

The following sample code initializes a client with SSL and TLS enabled:

```java
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;

public class OpenSearchClientExample {
  public static void main(String[] args) throws Exception {
    System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
    System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

    final HttpHost host = new HttpHost("https", "localhost", 9200);
    final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    //Only for demo purposes. Don't specify your credentials in code.
    credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials("admin", "admin".toCharArray()));

    //Initialize the client with SSL and TLS enabled
    final RestClient restClient = RestClient.builder(host).
      setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
        @Override
        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        }
      }).build();

    final OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
    final OpenSearchClient client = new OpenSearchClient(transport);
  }
}
```

## Connecting -- to -- 
### Amazon OpenSearch Service

The following example illustrates connecting to Amazon OpenSearch Service:

```java
SdkHttpClient httpClient = ApacheHttpClient.builder().build();

OpenSearchClient client = new OpenSearchClient(
    new AwsSdk2Transport(
        httpClient,
        "search-...us-west-2.es.amazonaws.com", // OpenSearch endpoint, without https://
        "es",
        Region.US_WEST_2, // signing service region
        AwsSdk2TransportOptions.builder().build()
    )
);

InfoResponse info = client.info();
System.out.println(info.version().distribution() + ": " + info.version().number());

httpClient.close();
```

### Amazon OpenSearch Serverless

The following example illustrates connecting to Amazon OpenSearch Serverless Service:

```java
SdkHttpClient httpClient = ApacheHttpClient.builder().build();

OpenSearchClient client = new OpenSearchClient(
    new AwsSdk2Transport(
        httpClient,
        "search-...us-west-2.aoss.amazonaws.com", // OpenSearch endpoint, without https://
        "aoss"
        Region.US_WEST_2, // signing service region
        AwsSdk2TransportOptions.builder().build()
    )
);

InfoResponse info = client.info();
System.out.println(info.version().distribution() + ": " + info.version().number());

httpClient.close();
```
