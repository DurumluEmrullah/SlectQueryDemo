

# Otomatik sorgu oluşturucu 


## Uygulamanın amacı
> JDBC template ve benzeri veri tabanına bağlantı için kendimiz manuel olarak sorgu yazmamız gereken 
> yapılar için bu sorgu yazma maliyetini düşüren ve kodların tekrar etmesinin önüne geçerek geliştirme yapmayı daha 
> pratik ve hızlı bir hale getirmeyi amaçlayan bir yapıdır.
> 
> Uygulama temel olarak iki Base sınıf ve bunlara yardımcı annotationlardan oluşmaktadır.
> 

## Uygulamanın Bileşenleri 

1. ```@Column ``` Annotation'u 
2. ```@Table ``` Annotation'u
3. ```@JoinTable``` Annotation'u
4. ```@JoinColumn``` Annotation'u
5. ```@Query``` Annotation'u
6. BaseDao
7. BaseJoinDao


### 1. ```@Column``` Annotation'u

> Bu annotation pojolar için fieldlerde kullanılmalıdır. 
> Bu annotationun sahip olduğu name alanına veritabanında ilgili sütunun adı verilmelidir.


#### Örnek Kullanım : 

```java 
    @Column(name="ID")
    private Integer id;

```

> Yukarıdaki örnekte verilen Integer tipinde olan id alanının sahip olduğu Collumn annotation'u 
> onun veri tabanındaki ID sutununa karşılk geldiğini göstermektedir. Bu annotation sayesinde sorgu oluştururken Sutunların adlarını öğreniyoruz ve ona göre sorgular oluşturuyoruz.
>

### 2. ```@Table ``` Annotation'u

> Bu annotation pojolar için class seviyesinde kullanılmalıdır. Bu anotation sayesinde tanımlanan pojonun veri tabanında hangi tabloya denk geldiğini anlayabiliyoruz.

#### Örnek Kullanım : 
```java 
   @Table(name="Example_Table")
   public class ExamplePojo {
   
   }

```

> Yukarıdaki örnekte verilen ExamplePojonun sahip olduğu Table annotation'u onun veri tabanında Example_Table tablosuna denk geldiğini göstermektedir.

### 3. ```@JoinTable ``` Annotation'u


> Bu annotation iki tablo joinleneceği zaman kullanılmalıdır(DTO larda kullanılmalıdır.). Anotationun sahip olduğu 4 alan vardır. 
> Bunlar :
> * leftTableName (String değer alır)
> * leftTableAlias (String değer alır)
> * rightTableName (String değer alır)
> * rightTableAlias (String değer alır)
> 
> #### leftTableName
> Bu alana joinleme işleminde kullanılacak ve sol tarafta olacak olan tablonun adı gelir. Bu kişiye kalmış bir şeydir illa bir tablo solda yada sağda olacak diye bir durum söz konusu değildir. Burda verdiğiniz
> değerleri daha sonrasında @JoinColumn annotationa vereceğiniz değerlerle tutarlı olması gerekmetedir.
> Default değeri yoktur ,boş geçilemez.
> #### leftTableAlias
> 
> Bu alanda joinleme işleminde kullanılacak olan ve solda yer alan tabloya takma ad vermek için kullanılır. Hiç bir değer verilmezse default olarak 'L' değerini alır. 
> 
> #### rightTableName
> 
> Bu alan joinleme işleminde kullanılacak olan ve join ifadesinin sağında kalacak olan tablo adını belirtmek için kullanılır.
> Default değeri yoktur ,boş geçilemez.
> 
> #### rightTableAlias
> 
> Bu alanda joinleme işleminde kullanılacak olan ve sağda yer alan tabloya takma ad vermek için kullanılır. Hiç bir değer verilmezse default olarak 'R' değerini alır. 
> 

#### Örnek Kullanım : 

````java
@JoinTable(leftTableName = "FirstTable",
        leftTableAlias = "A",
        rightTableName = "SecondTable",
        rightTableAlias = "B")
public class AJoinBDto {

}

````

 Yukarıdaki örnekteki annotationun kullanımı sonucunda şöyle bir çıktı olacaktır:

````sql

Select ...... FROM FirstTable A INNER JOIN SecondTable ......

````

### 4. ```@JoinColumn ``` Annotation'u

>Bu annotation DTO ların fieldlerinde kullanılmak üzere yapılmıştır.Annotationun sahip olduğu 3 alan vardır. Bunlar:
> 
> * isLeft (boolean değer alır)
> * columnName (String değer alır)
> * joinColumnName (String değer alır)
> 
> #### isLeft
> 
> Bu alan boolean değer almaktadır. Bu alan sayesinde tanımlanan fieldin @JoinTable annotationu ile verilen tablolardan sağdaki tabloda mı yer aldığı
> yoksa soldaki tablodamı yer aldığı anlaşılmaktadır.Eğer true değeri verirsek bu alan @JoinTable annotationunda verilen leftTable özelliklerini taşıyacak olup
>onun alias değerini alacaktır . Eğer false değeri verirsek de rightTable özelliklerini taşıyacaktır.
>Default değeri yoktur. Doldurulması zorunludur.
> 
> #### columnName
> 
> Bu alan Dtoda tanımlanan fieldin veritabanındaki sutun adını almaktadır. Default değeri yoktur. Boş geçilemez.
> 
> #### joinColumnName
> 
> Bu lana ilgili fieldin joinleme işlemine katılıp katılmayacağını anlamak için eklenmiştir. Default değeri vardı eğer ilgili column joinleme işleminde kullanılmayacaksa bu alan kullanılmamalıdır.
> 
>

#### Örnek Kullanım 1: 

```java

    @JoinColumn(isLeft = true,columnName = "FIRST_TABLE_ID",joinColumnName = "SECOND_TABLE_ID")
    private Integer firstTableId;

```

>Yukarıdaki örnekte verilen Integer tipinde firstTableId alanı ,joinColumnName alanının doldurulmasıyla joinleme işlemine katılacağı belirtilmiştir.
> Burada isLeft alanı true verildiği için columName ifadesinde yer alan değerin sol tablonun özelliklerini taşıyacağı , joinColumnName ifadesinde yer alan değerin ise sağ tablonun özelliklerini taşıyacağı belirtilmiştir.
> Bu değer join ifadesinin ON alanında olacağı anlamını taşımaktadır.Bu işlem sonucunda oluşacak sorgu : 
> 
```sql
Select  ..... FROM TableA L INNER JOIN  TableB R ON L.FIRST_TABLE_ID=R.SECOND_TABLE_ID
```

>şeklinde olacaktır.
> 
#### Örnek Kullanım 2: 

```java

    @JoinColumn(isLeft = true,columnName = "NAME")
    private String name;

```

> Yukarıdaki örnekte verilen name alanı isLeft değeri true olmasından dolayı sol tablonun alanı olup ,sutun adı NAME olan bir değerdir.
> joinColumnName değeri boş geçildiği için bu alan joinleme işlemine dahil edilmeyecektir. Ama BaseJoinDao yu kalıtan classta yazılan fonksiyonun ismine bağlı olarak filtrelemeye dahil olabilir.
>


### 4. ```@Query ``` Annotation'u

> Bu annotation method seviyesinde çalışmaktadır. 