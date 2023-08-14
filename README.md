# NORMA FINAL PROJECT

## Overview

Online bankacılık sistemi, banka şubesine gitmeden internet erişimi olan bir bilgisayar üzerinden erişilebilen bir backend uygulamasıdır. Bu uygulama birçok bankacılık işlemini kapsamaktadır. Projenizde müşterilere sağlanan 4 temel hizmet şunlardır:

1-Müşteri Yönetimi: Müşteri hesapları ve bilgileri yönetimi, yeni müşteri oluşturma, müşteri bilgilerini güncelleme, müşteri arama ve aktif /i naktif durumlarını kontrol etme gibi işlemleri içerir.

2-Hesap Yönetimi: Müşterilere sunulan farklı hesap türlerini yönetme, yeni hesap açma, hesap hareketlerini görüntüleme, hesap hareketlerini tarih aralığına göre filtreleme ve hesapları kapatma işlemlerini içerir. Ayrıca tasarruf hesaplarında faiz kazancı ve vade sonu ödeme işlemleri gibi özel hesap işlemleri de yer alır.

3-Kart Yönetimi: Müşterilere sunulan banka kartları ve kredi kartlarını yönetme, yeni kart oluşturma, kartlara ait hesap hareketlerini görüntüleme, kartları kapatma ve kartların güvenlik işlemlerini yönetme gibi işlemleri içerir.

4-Transfer Yönetimi: Müşterilere para transfer işlemleri yapma imkanı sağlar. Hesaplar arası para transferi, Iban ve e-posta üzerinden transfer, farklı para birimlerinde anlık para transferi gibi işlemler bu kategoride yer alır. Ayrıca transferlerde güvenlik kontrolleri ve bakiye yeterliliği gibi kontroller de yapılır.




----
* Customer Management
* Account Management
* Card Management
* Transfer Management
* Atm management

----

### Used Technologies

* Java 17
* Spring Boot
* Hibernate
* PostgresSql
* Junit Test
* Integration Test
* Lombok
* Postman 

---

## Help

---

### Quick start in locale

```
git clone https://github.com/YusufAkin27/Bank-Application
$ postgresql'de 'norma' adında bir veritabanı oluşturun
$ mvn clean install
$ mvn spring-boot:run

```

----

### Quick start in docker

```
git clone https://github.com/YusufAkin27/Bank-Application
$ mvn clean install
$ docker build -t engbank:0.0.1 .
$ cd ./compose
$ docker-compose up
```

----

### services provided to customers details

* Bir kontrol hesabı oluştur

* Kontrol hesabının hesap hareketlerinin detaylı görüntülenmesi

* Kontrol hesabının hesap hareketlerinin filtreli (tarih aralığı) görüntülenmesi

* Bir tasarruf hesabı oluştur

* Tasarruf hesabının hesap hareketlerinin detaylı görüntülenmesi

* Tasarruf hesabının hesap hareketlerinin filtreli (tarih aralığı) görüntülenmesi 

* Bir tasarruf hesabından belirli bir vade ile para biriktirme (faiz) (zamanlanmış görevle yapıldı)

* Bir banka kartı oluştur

* Bir kredi kartı oluştur

* Kredi kartının önceki ekstresinden güncel borcu ve kalan borcu görüntüleme

* Kredi kartı hesabının hesap hareketlerinin detaylı görüntülenmesi

* Hesaplar arasında para transferi

* Iban yöntemi ile transfer yapma
---

## Swagger

--------------
### All end-points
![presentation](https://i.imgur.com/LGyO088.png)
--------------
### Customer endpoints

![presentation](https://i.imgur.com/l0F3Scb.png)


------------

### Checking account endpoints

![presentation](https://i.imgur.com/iuQYCOo.png)

------------

### Saving account endpoints

![presentation](https://i.imgur.com/k4Ium2z.png)

------------

### Debit card endpoints

![presentation](https://i.imgur.com/BxGRnWv.png)

------------

### Credit card endpoints

![presentation](https://i.imgur.com/AoPPhxA.png)

------------

### Transfer endpoints

![presentation](https://i.imgur.com/QlaX3lq.png)

------------


### Database Diagrams

![presentation](https://i.imgur.com/WeGwvtb.png)



