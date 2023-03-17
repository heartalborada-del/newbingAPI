# newBing API

这是一个newBing API的Java实现

![Commit activity](https://img.shields.io/github/commit-activity/m/heartalborada-del/newbingAPI)

## 目前请先将 `byPassCN` 设置成 `false`

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
        new Chat(
                "Your cookies",
                false
        ).newChat().newQuestion("Your question", new Callback() {
            @Override
            public void onSuccess(JsonObject rawData) {
                //TODO
            }

            @Override
            public void onFailure(JsonObject rawData, String cause) {
                //TODO
            }
            @Override
            public void onUpdate(JsonObject rawData){
                //TODO
            }
        });
    }
}
```

## Version
![Maven central version](https://img.shields.io/maven-central/v/io.github.heartalborada-del/newBingAPI)
