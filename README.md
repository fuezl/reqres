## Инструкция по запуску тестов

Запуск тестов происходит через джобу в [Jenkins](https://jenkins.autotests.cloud/job/07-grach17-reqres/)

![Jenkins](./images/Jenkins.png)

### Run tests:

```bash
gradle clean test -Dthreads=5
```

### Serve report:

```bash
allure serve build/allure-results
```

## Оповещение о результатах прохождения тестов через бот в [телеграмм](https://t.me/autotest_result)

![Telegram](./images/Telegram.png)

## Анализ результатов

Более подробно с результатми тестов можно ознакомиться в:

* Jenkins через Allure Reports
  (перейти по ссылке в отчете телеграм)

### Анализ результатов в Jenkins через Allure Reports

![alt "Allure Reports"](./images/Allure_Report_1.png)
![alt "Allure Reports"](./images/Allure_Report_2.png)
![alt "Allure Reports"](./images/Allure_Report_3.png)
