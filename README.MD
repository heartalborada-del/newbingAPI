# newBing API

这是一个newBing API的Java实现


## How to use it

```xml
<dependency>
    <groupId>io.github.heartalborada-del</groupId>
    <artifactId>newBingAPI</artifactId>
    <version>latest</version>
</dependency>
```

```java
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        new chat(
                "Your cookies",
                true
        ).newChat().newQuestion("Your question", new Callback() {
            @Override
            public void onSuccess(JsonObject rawData) {
                //TODO
            }

            @Override
            public void onFailed(JsonObject rawData, String cause) {
                //TODO
            }
        });
    }
}
```

## Version
![https://mvnrepository.com/artifact/io.github.heartalborada-del/newBingAPI](https://img.shields.io/maven-central/v/io.github.heartalborada-del/newBingAPI)