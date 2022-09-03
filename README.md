

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
6. BaseDao<E>
7. BaseJoinDao<E>


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


### 5. ```@Query ``` Annotation'u

> Bu annotation method seviyesinde çalışmaktadır. Bu annotationun sahip olduğu bir tane alan vardır :
> *  extraConditions (String array değer alır)
> 
> Bu annotation ekstra durumlar için var olan bir annotationdur. Örnek vermek gerekirse: sorgumuzda müşteri tipleri diye bir alan olacak ve bu alan birden fazla müşteri tipine sahip diyelim
> bu birden fazla müşteri tipini sorgumuza bu parametre içinde yazıp ekletebiliriz. 
> aynı zamanda ilgili fonksiyona bu alanda geçilecek olan değerler verilmelidir : 
> 
#### Örnek Kullanım 1: 

```java

    @Query(extraConditions = "A.CUSTOMER_TYPE IN (?, ? ,?)")
    public String getAllByNameAndSurnameAndAge(AJoinBDto aJoinBDto, String firstTypeCustomer, String secondTypeCustomer, String thirdTypeCustomer){
        return select(aJoinBDto,firstTypeCustomer,secondTypeCustomer,thirdTypeCustomer);
    }

```

> Yuykarıdaki örnekte veri tabanından A tablosu ile B tablosunun joinlenmiş değerlerini çekiyoruz. 
> Bu değerler Name,Surname ve Age alanına göre filtreli bir şekilde verilecektir . Ekstra olarak A tablosundaki müşteri tipleri belirtilen 3 değerde olmasını istiyoruz.
> Bu 3 değeri şekilde görüldüğü gibi select sorgusuna sırayla veriyoruz. Ve bu alan sorgumuza eklenmiş oluyor.Bu işlemin sonucunda oluşacak sorgu :

```sql
Select  ..... FROM .... ON ... WHERE ..... AND  A.CUSTOMER_TYPE IN (?, ? ,?)
```
>şeklinde olacaktır.

#### Örnek Kullanım 2: 

```java

    @Query(extraConditions = {"A.CUSTOMER_TYPE IN (?, ? ,?)","B.CONTRACT_TYPE IN (? , ?)"})
    public String getAllByNameAndSurnameAndAge(AJoinBDto aJoinBDto, String firstTypeCustomer, String secondTypeCustomer, String thirdTypeCustomer,
            String contractType1,String contractType2){
        return select(aJoinBDto,firstTypeCustomer,secondTypeCustomer,thirdTypeCustomer,
            contractType1,contractType2);
    }

```

> Eğer birden fazla böyle bir alan varsa select fonksiyonumuza gönderdiğimiz değerler. @Query annotationuna verdiğimiz değerlerin sırasında olması gerekmektedir.
> Yukarıda da görüldüğü gibi ilk başta CUSTOMER_TYPE IN(?,?,? ) sorgu parçası verilmiş ve ilk sırada bu sorgu parçasındaki 
> değerler verilmiştir sonradan da ikinci sorgu parçacığı olan CONTRACT_TYPE IN (?,?) değerleri sırayla verilmiştir.
> Bu işlemin sonucunda oluşacak sorgu : 
>
```sql
Select  ..... FROM .... ON ... WHERE ..... AND  A.CUSTOMER_TYPE IN (?, ? ,?) AND B.CONTRACT_TYPE IN (? , ?)
```

>şeklinde olacaktır.
> 
>
### 6. BaseDao<E> Class'ı

>Bu class veri tabanında bir tabloya karşılık gelen pojolar için kullanılmaktadır. Generic kısımda bulunan E bölümüne 
> yukarıdaki @Table ve @Column anahtarlarıyla oluşturulmuş pojo nesnesi gelmelidir. Sahip olduğu tek fonksiyon vardır: 
>
> *  ````public List<E> select(E e,Object ... args)````
> 

####  ````public List<E> select(E e,Object ... args)````

> Bu fonksiyon belirtilen kurallara göre verilen obje üzerinden select sorgusunu oluşturup fonksiyonun sonunda "Jdbc template bu kısmda kullanılacak."
> yazdığım yere jdbc template kodları geldikten sonrada select sorgusu sonucunda üretilen sonuçları dönecektir.
> 
> Bu fonksiyon BaseDao clasını extend eden SubDao classlarında kullanılmalıdır. 
> 
#### Örenk Kullanım : 

````java
    public class ExampleDao2 extends BaseDao<ExamplePojo2> {
    
        public List<ExamplePojo2> getExamplePojo2ByField1AndField2AndField3AndField7(ExamplePojo2 examplePojo2){
            return select(examplePojo2);
        }
    
        public List<ExamplePojo2>  getExamplePojo2ByField5AndField3AndField1AndField2(ExamplePojo2 examplePojo2){
            return select(examplePojo2);
        }
    
        public List<ExamplePojo2>  getExamplePojo2ByField5(ExamplePojo2 examplePojo2){
            return select(examplePojo2);
        }
    
        @Query(extraConditions = "FIELD_1 IN(?,?)")
        public List<ExamplePojo2>  getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(ExamplePojo2 examplePojo2,String field1Type1,String field1Type2){
            return select(examplePojo2,field1Type1,field1Type2);
        }
    }


````

 Yukarıda da görüldüğü gibi bu fonksiyonu kullanacak olan fonksiyonların isimlendirilmesinde belirli kurallar vardır. Bu kuralları 4. fonksiyon olan getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10 üzerinden ele alalım.
Bu kodun çalışmasıyla ortaya çıkacak sorgu şu şekildedir:
````sql

    SELECT ...pojoda @Column annotationu ile belirtilen alanlar ... FROM ... pojo da @Table annotationu ile belirtilen tablo adı...
        WHERE  FIELD_1 = ? AND  FIELD_2 = ? AND  FIELD_3 = ? AND  FIELD_5 = ? 
        AND  FIELD_9 = ? AND  FIELD_10 = ? AND FIELD_1 IN(?,?)
````


> 
> #### 1. isimlendirme standartı : 
> Bu fonksiyonu kullanacak olan methodların ismi iki bölümden oluşacaktır. Örnek üzerinden gidersek bunlar : 
> getExamplePojo2   ve   ByField5AndField3AndField1AndField2AndField9AndField10 .
>  İlk kısım opsiyoneldir kişi istediği gibi yazabilir. İkinci kısım ise BaseDao clasındaki select sorgusunun nasıl oluşturulacağına yön vermektedir. 
> 1. ikinci kısım By ile başlamalıdır
> 2. By dan sonra filtrelenmek istenen alanlar sırasıyla And ile ayırılarak verilmelidir. Bu verilen isimler BaseClass a generic bir şekilde geçilmiş olan objenin 
> field alanlarıyla birebir aynı olmalıdır.(Case sensetive değildir. Arka planda bunları küçük harfe çevirip kontrol ediyor ).
> 3. Filtrelemede kullanılacak alanlar fonksiyona geçilecek olan ilk parametredike objenin filtrelemede kullanılacak alanları dolu olmalıdır.
> 
> 
> #### Örnekteki fonskiyona geçilen 2.ve 3. parametre : 
> Bu parametreler örnketeverilen @Query annotationu ile verilen değerlerdir. Örnekteki @Query annotationu ile verilen sorgu parçacığı
> method isimlendirilmesinde kullanıldığında Javanın isimlendirme standartlarını bozma ihtimale ve birden fazla parametre geçilebilme ihtimali olduğu için bu şekilde kullanılmıştır.
> 