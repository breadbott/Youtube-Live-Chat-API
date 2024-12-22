# YouTube-Live-Chat-API

## Installation

1. Add YouTube-Live-Chat-API as a dependency to your project.

Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.breadbott:YouTube-Live-Chat-API:1.0.0'
}
```

maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.breadbott</groupId>
        <artifactId>YouTube-Live-Chat-API</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

```


2. Generate a YouTube API token or an OAuth token at the [Google Cloud Console](https://console.cloud.google.com).

3. Make a new YouTubeLive instance. [see examples](https://github.com/breadbott/Youtube-Live-Chat-API/blob/master/src/test/java/com/breadbot/YouTubeBot.java)
```java
YouTubeLive.newClient()
    .apiKey("API-KEY")
    // Or optionally oAuthToken.
    //.oAuthToken("OAUTH-TOKEN")
    // Use events here.
    .onTextMessage((event, client) -> {
        String sender = event.getAuthorChannelName();
        String message = event.getMessageText();
        System.out.println(sender+" > "+message);
    })
    .buildAndConnect("https://www.youtube.com/watch?v=jfKfPfyJRdk");
```

## More features to come!
