# newBing API

这是一个newBing API的Java实现

![CodeQL](https://github.com/heartalborada-del/newbingAPI/actions/workflows/codeql.yml/badge.svg)
![CommitActivity](https://img.shields.io/github/commit-activity/m/heartalborada-del/newbingAPI)
![issues](https://img.shields.io/github/issues/heartalborada-del/newBingAPI)
![license](https://img.shields.io/github/license/heartalborada-del/newBingAPI)

## ~~目前请先将 `byPassCN` 设置成 `false`~~(已移除)

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
                "Your cookies(or you can set it empty)",
                "Set new bing mode(Creative/Balanced/Precise)"
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
![MavenCentralVersion](https://img.shields.io/maven-central/v/io.github.heartalborada-del/newBingAPI)
