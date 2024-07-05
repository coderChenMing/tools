Aspose的jar破解文档
==============

项目代码仓库：[https://gitee.com/search-and-search/ming](https://gitee.com/search-and-search/ming)  
体验地址：[http://www.mitchconvert.cn/](http://www.mitchconvert.cn/)

### 1、引入jar包

#### 方式一：

1、在pom.xml配置aspose的jar包仓库

```xml
<repositories>
        <repository>
            <id>AsposeJavaAPI</id>
            <name>Aspose Java API</name>
            <url>https://repository.aspose.com/repo/</url>
        </repository>
    </repositories>
```

2、然后分别加对应的依赖并刷新pom文件进行下载：

```xml
		<dependency><!--word-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-words</artifactId>
            <version>21.11</version>
			<classifier>jdk17</classifier>
        </dependency>
        <dependency><!--pdf-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-pdf</artifactId>
            <version>21.8</version>
        </dependency>
        <dependency><!--excel-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-cells</artifactId>
            <version>21.8</version>
        </dependency>
        <dependency><!--pptx-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-slides</artifactId>
            <version>21.8</version>
			<classifier>jdk16</classifier>
        </dependency>
        <dependency><!--jar破解工具-->
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.20.0-GA</version>
        </dependency>
```

#### 方式二：

1、自行官网下载for Java版本：[On Premise File Format API Releases | Aspose](https://releases.aspose.com/)

然后把下载好的jar包放到项目的lib目录下，我的如图：

![](https://img2022.cnblogs.com/blog/2009683/202211/2009683-20221113214544201-545742094.png)

使用加载本地lib目录下jar的方式导入：

```xml
        <!--  aspose 文档操作工具包,项目本地lib目录引入  -->
        <dependency><!--word-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-words</artifactId>
            <scope>system</scope>
            <systemPath>${basedir}/lib/aspose-words-21.1.0-jdk17.jar</systemPath>
        </dependency>
        <dependency><!--pdf-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-pdf</artifactId>
            <scope>system</scope>
            <systemPath>${basedir}/lib/aspose-pdf-21.8.jar</systemPath>
        </dependency>
        <dependency><!--excel-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-cells</artifactId>
            <scope>system</scope>
            <systemPath>${basedir}/lib/aspose-cells-21.8.jar</systemPath>
        </dependency>
        <dependency><!--pptx-->
            <groupId>com.aspose</groupId>
            <artifactId>aspose-slides</artifactId>
            <scope>system</scope>
            <systemPath>${basedir}/lib/aspose-slides-21.8-jdk16.jar</systemPath>
        </dependency>
        <dependency><!--aspose的jar破解工具-->
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.20.0-GA</version>
        </dependency>
```

### 2、破解前做好的准备：

1、首先是记住你下载完jar本地的绝对路径，比如我是以方式一下载的，我本地的jar包就保存在C:\\Users\\PC.m2\\repository\\com\\aspose\\下,下面破解各个jar的时候需要用到各个jar包的绝对路径去获取类的内容

2、新建一个类，写一个方法，按住ctrl键点击对应的setLicense()类里面看源码

**最后记住：每个版本的代码破解的地方大多类似，只是名称被换了而已，道理都是一样的**

```java
public class AsposePdfJarCrack {
private static void viewSetLicenseCode()throws Exception{
        com.aspose.pdf.License pdfLicense=new com.aspose.pdf.License();
        com.aspose.words.License wordsLicense=new com.aspose.words.License();
        com.aspose.slides.License slidesLicense = new com.aspose.slides.License();
        com.aspose.cells.License cellsLicense = new com.aspose.cells.License();
        //请点击这里的setLicense方法进入查看源码，不同版本对words 的破解方法名不一样，但道理差不多
        pdfLicense.setLicense("");
        wordsLicense.setLicense("");
        slidesLicense.setLicense("");
        cellsLicense.setLicense("");
	}
}
```

### 3、pdf-21.8版本破解

```java
private static final String Desktop="C:\\Users\\PC\\Desktop\\";
private static void crackAsposePdfJar(String jarName) {
    try {
        ClassPool.getDefault().insertClassPath(jarName);
        CtClass ctClass = ClassPool.getDefault().getCtClass("com.aspose.pdf.ADocument");
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        int num = 0;
        for (int i = 0; i < declaredMethods.length; i++) {
            if (num == 2) {
                break;
            }
            CtMethod method = declaredMethods[i];
            CtClass[] ps = method.getParameterTypes();
            if (ps.length == 2
                    && method.getName().equals("lI")
                    && ps[0].getName().equals("com.aspose.pdf.ADocument")
                    && ps[1].getName().equals("int")) {
                //源码ADocument类的这个方法限制页数：
                // static boolean lI(ADocument var0, int var1) {
                //        return !lb() && !lj() && !var0.lt() && var1 > 4;
                //    }
                // 最多只能转换4页，处理返回false，无限制页数
                System.out.println(method.getReturnType());
                System.out.println(ps[1].getName());
                method.setBody("{return false;}");
                num = 1;
            }
            if (ps.length == 0 && method.getName().equals("lt")) {
                // 水印处理
                method.setBody("{return true;}");
                num = 2;
            }
        }
        //修改完，把类的输出到指定目录（桌面下）
		ctClass.writeFile(Desktop);
    } catch (NotFoundException e) {
        e.printStackTrace();
    } catch (CannotCompileException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
```

接下来就是调用这个crackAsposePdfJar("C:\\Users\\PC.m2\\repository\\com\\aspose\\aspose-pdf\\aspose-pdf-21.8.jar")方法了，运行完就会在桌面生成一个名为com的目录。

先备份aspose-pdf-21.8.jar包，然后右击以压缩包的方式打开，找到对应目录，把上面生成的这个class类替换一下，然后把下面目录下两个文件删掉：

```x86asm
META-INF/37E3C32D.SF
META-INF/37E3C32D.RSA
```

最后刷新一下pom就可以了

下面的操作都是一样的，只是破解代码不一样而已，所以下面只附上破解代码

### 4、cells-21.8版本破解：

```java
public static void crackAsposeCells(String JarPath) throws NotFoundException,CannotCompileException, IOException {
    // 这个是得到反编译的池
    ClassPool pool = ClassPool.getDefault();

    // 取得需要反编译的jar文件，设定路径
    pool.insertClassPath(JarPath);

    CtClass ctClass = pool.get("com.aspose.cells.License");

    CtMethod method_isLicenseSet = ctClass.getDeclaredMethod("isLicenseSet");
    method_isLicenseSet.setBody("return true;");
    CtMethod method_setLicense = ctClass.getDeclaredMethod("setLicense");
    method_setLicense.setBody("{    a = new com.aspose.cells.License();\n" +
            "    com.aspose.cells.zbkl.a();}");
    CtMethod methodL = ctClass.getDeclaredMethod("l");
    methodL.setBody("return new java.util.Date(Long.MAX_VALUE);");

    ctClass.writeFile(Desktop);
}
```

### 5、words-21.11-jdk17版本破解代码：

```java
private static void crackAsposeWordsJarAddLicense1(String jarName){
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath(jarName);
            //获取指定的class文件对象
            CtClass zzZJJClass = 				ClassPool.getDefault().getCtClass("com.aspose.words.zzXDb");
            //从class对象中解析获取指定的方法
            CtMethod[] methodA = zzZJJClass.getDeclaredMethods("zzY0J");
            //遍历重载的方法
            for (CtMethod ctMethod : methodA) {
                CtClass[] ps = ctMethod.getParameterTypes();
                if (ctMethod.getName().equals("zzY0J")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    //替换指定方法的方法体
                    ctMethod.setBody("{this.zzZ3l = new java.util.Date(Long.MAX_VALUE);this.zzWSL = com.aspose.words.zzYeQ.zzXgr;zzWiV = this;}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzZJJClass.writeFile(Desktop);

            //获取指定的class文件对象
            CtClass zzZJJClassB = ClassPool.getDefault().getCtClass("com.aspose.words.zzYKk");
            //从class对象中解析获取指定的方法
            CtMethod methodB = zzZJJClassB.getDeclaredMethod("zzWy3");
            //替换指定方法的方法体
            methodB.setBody("{return 256;}");
            //这一步就是将破译完的代码放在桌面上
            zzZJJClassB.writeFile(Desktop);
        } catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }
```

### 6、slides-21.8-jdk16版本破解代码

```java
private static void modifyPptJar(String jarName) {
    try {
        //这一步是完整的jar包路径,选择自己解压的jar目录
        ClassPool.getDefault().insertClassPath(jarName);
        CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.slides.internal.of.public");
        CtMethod[] methodA = zzZJJClass.getDeclaredMethods();
        for (CtMethod ctMethod : methodA) {
            CtClass[] ps = ctMethod.getParameterTypes();
            if (ps.length == 3 && ctMethod.getName().equals("do")) {
                System.out.println("ps[0].getName==" + ps[0].getName());
                ctMethod.setBody("{}");
            }
        }
        //这一步就是将破译完的代码放在桌面上
        zzZJJClass.writeFile(Desktop);
    } catch (Exception e) {
        System.out.println("错误==" + e);
    }
}
```

### 7、注意：

破解完创建一个license.xml的文件放到resources目录下，内容如下：

```xml
<License>
    <Data>
        <Products>
            <Product>Aspose.Total for Java</Product>
            <Product>Aspose.Words for Java</Product>
        </Products>
        <EditionType>Enterprise</EditionType>
        <SubscriptionExpiry>20991231</SubscriptionExpiry>
        <LicenseExpiry>20991231</LicenseExpiry>
        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>
    </Data>
    <Signature>
        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=
    </Signature>
</License>
```

然后在转换之前，先手动调一下校验，才能实现去水印和页数限制：

```java
FileInputStream fis = new FileInputStream("src/main/resources/license.xml");
License license=new License();
license.setLicense(fis);
//开始转换代码。。。（省略）
```

本文转自 <https://www.cnblogs.com/cxll863/p/16887080.html>，如有侵权，请联系删除。