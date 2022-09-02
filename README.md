

#Otomatik sorgu oluşturucu 


##Uygulamanın amacı
> JDBC template ve benzeri veri tabanına bağlantı için kendimiz manuel olarak sorgu yazmamız gereken 
> yapılar için bu sorgu yazma maliyetini düşüren ve kodların tekrar etmesinin önüne geçerek geliştirme yapmayı daha 
> pratik ve hızlı bir hale getirmeyi amaçlayan bir yapıdır.
> 
> Uygulama temel olarak iki Base sınıf ve bunlara yardımcı annotationlardan oluşmaktadır.
> 

##Uygulamanın Bileşenleri 
1. ```@Column ``` Annotation'u 
2. ```@Table ``` Annotation'u
3. ```@JoinColumn``` Annotation'u
4. ```@JoinTable``` Annotation'u   
5. ```@Query``` Annotation'u
6. BaseDao
7. BaseJoinDao


### 1.``` @Column``` Annotation'u

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

### 3.```@JoinColumn ``` Annotation'u

