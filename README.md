# Документация

[RuStoreSDK для подключения платежей на help.rustore.ru](https://help.rustore.ru/rustore/for_developers/developer-documentation/SDK-connecting-payments)

## Условия для работы SDK и проведения платежей
- На устройстве пользователя установлено приложение RuStore.
- Приложение RuStore поддерживает функциональность платежей.
- Пользователь авторизован в приложении RuStore.
- Для приложения включена возможность покупок в системе RuStore Консоль.

## Подключение

### Подключите локальный репозиторий:
```groovy
repositories {
    maven {
        url = uri("https://artifactory-external.vkpartner.ru/artifactory/maven")
    }
}
```

### Подключите зависимость в build.gradle:
```groovy
dependencies {
    implementation("ru.rustore.sdk:billingclient:0.0.7")
}
```

### Укажите в build.gradle файле информацию о подписи приложения:
```groovy
signingConfigs {
    debug {
        storeFile file("../yourkeystorefilename.keystore")
    }
}
```

### Проверьте applicationId и versionCode
- Не меняйте applicationId после первой загрузки приложения. Он будет использоваться при дальнейших обновлениях
- При каждом новом релизе обязательно нужно производить инкремент версии versionCode

## Инициализация
```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        RuStoreBillingClient.init(
            appContext = applicationContext,
            applicationId = BuildConfig.APPLICATION_ID,
            consoleApplicationId = "508863",
            deeplinkPrefix = "yourappscheme://iamback"
        )
    }
}
```
- consoleApplicationId - код приложения из системы RuStore Консоль. Его можно посмотреть в адресной строке браузера на страничке вашего приложения. Пример: https://console.rustore.ru/apps/508863