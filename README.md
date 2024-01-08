### Сервис для авторизации и управления доступом пользователей для мобильного приложения на Spring Boot 3.

Подробнее об API можно узнать здесь - [Swagger](https://gribanoveu.github.io/Spring-auth-backend-service/)

Запуск сервиса:
1. Генерация сертификатов RSA для работы с JWT через OpenSSL

	    openssl genrsa -out keypair.pem 2048
	    openssl rsa -in keypair.pem -pubout -out public.pem
	    openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
  
  Полученные файлы сертификатов нужно положить по пути `src/main/resources/certs`
  Или указать путь для закрытого и открытого ключа в файле `application.yaml`

      rsa:  
        private-key: classpath:certs/private.pem  
        public-key: classpath:certs/public.pem

2. Для работы по HTTPS нужно так же указать сертификат в `application.yaml`

	    server:  
	      port: 8443  
	      ssl:  
	      key-store: classpath:certs/mykeys.jks  
	        key-store-password: letmein  
	        key-password: letmein

Для локальной работы можно сгенерировать локальный сертификат

`keytool -keystore mykeys.jks -genkey -alias  tomcat -keyalg RSA`

3. Так же необходимо произвести настройку подключения к базе данных
