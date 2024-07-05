> 前言 工欲善其事，必先利其器

源码分析
----

### 1\. 下载[Aspose.Words for Java21.11官方jar包](https://link.juejin.cn?target=https%3A%2F%2Fdownloads.aspose.com%2Fwords%2Fjava "https://downloads.aspose.com/words/java")

### 2\. 开始分析

1.  调用授权方法

java

复制代码

`InputStream is = new FileInputStream("..license.xml"); License license = new License(); license.setLicense(is);`

license.xml文件内容这里是个过期的文件主要是格式

xml

复制代码

`<License>     <Data>         <Products>             <Product>Aspose.Total for Java</Product>             <Product>Aspose.Words for Java</Product>         </Products>         <EditionType>Enterprise</EditionType>         <SubscriptionExpiry>20991231</SubscriptionExpiry>         <LicenseExpiry>20991231</LicenseExpiry>         <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>     </Data>     <Signature>         sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=     </Signature> </License>`

2.  分析License类的setLicense方法找到关键代码

java

复制代码

`public void setLicense(String licenseName) throws Exception {     if (licenseName == null) {         throw new NullPointerException(zzVu.zzIR().zzZ42(new byte[]{105, 108, 101, 99, 115, 110, 78, 101, 109, 97, 101}));     } else {         (new zzXDb()).zzY0J(licenseName, zzWJD.zzWIQ());     } } public void setLicense(InputStream stream) throws Exception {     if (stream == null) {         throw new NullPointerException(zzVu.zzIR().zzZ42(new byte[]{116, 115, 101, 114, 109, 97}));     } else {         (new zzXDb()).zzY0J(stream);     } }`

setLicense的两个重载方法最终都调用了`(new zzXDb()).zzY0J(stream);`中的zzY0J方法，进入zzY0J方法观察代码发现重点在于`void` zzY0J`方法下面的 (InputStream var1) throws Exception`这个重载方法里面，但是里面代码很多不太好找到关键代码，所以转头去寻找关于验证对外调用的静态方法，最终找到了在`zzY0J`方法下面的

java

复制代码

`static byte[] zzX8p() {     boolean var0 = zzWiV == null || zzWiV.zzWSL == zzYeQ.zzX0q || (new Date()).after(zzWiV.zzZ3l) || zzYKk.zzWy3() == 4096;     if (zzW5s == 0L) {         zzW5s ^= zzVWj;     }     boolean var1 = false;     if (zzZB8.zzxn() != null) {         var1 = zzZB8.zzZ7p() == zzu3.zzX0q;         byte[] var2 = var0 && var1 ? zzYeQ.zzX0q : zzYeQ.zzXgr;         return var2;     } else {         return null;     } } static byte[] zzWQR() {     boolean var0 = zzWiV == null || zzWiV.zzWSL == zzYeQ.zzX0q || (new Date()).after(zzWiV.zzZ3l) || zzYKk.zzWy3() == 4096;     boolean var1 = zzZB8.zzZ7p() == zzu3.zzX0q;     byte[] var2 = var0 && var1 ? zzYeQ.zzX0q : zzYeQ.zzXgr;     return var2; }`

这两个方法主要在于对外返回了一个byte数组，返回值是`zzYeQ`中的静态常量，所以重点就在于上面的判断语句`boolean var0 = zzWiV == null || zzWiV.zzWSL == zzYeQ.zzX0q || (new Date()).after(zzWiV.zzZ3l) || zzYKk.zzWy3() == 4096;`让它返回什么数据。  
这里需要分析`zzWiV.zzWSL` `zzWiV.zzZ3l` `zzYKk.zzWy3()`这三个数据，在当前`zzXDb`class文件中搜索找到在`void zzY0J(InputStream var1) throws Exception`方法中关键的关键位置赋值了

java

复制代码

`this.zzWSL = zzYeQ.zzXgr; zzWiV = this;`

观察它上下位置代码发现看起来都是做验证错误的处理，所以可以尝试去掉上下的验证。  
再来看`zzWiV.zzZ3l`变量属性为Date应该是时间什么的可以直接给个最大值。  
然后是`zzYKk.zzWy3()`进入看到

java

复制代码

`static int zzWy3() {     return zzYU8 == 128 && !zzyS ? 256 : 4096; }`

那么返回值就是256和4096二选一，尝试后选择返回256。

### 3\. 分析结果

*   修改`void zzY0J(InputStream var1)`方法体为

java

复制代码

`this.zzZ3l = new java.util.Date(Long.MAX_VALUE);//Date赋值最大值 this.zzWSL = zzYeQ.zzXgr;//直接返回验证成功的执行 zzWiV = this;//直接返回验证成功的执行`

*   修改`zzYKk`类下的`static int zzWy3()`方法体为

java

复制代码

`return 256;`

执行操作
----

### 1\. 添加Javassist修改class字节码文件

xml

复制代码

`<dependency>     <groupId>org.javassist</groupId>     <artifactId>javassist</artifactId>     <version>3.28.0-GA</version> </dependency>`

### 2\. 添加修改方法

java

复制代码

`/**  * 修改words jar包里面的校验  */ public static void modifyWordsJar() {     try {         //这一步是完整的jar包路径,选择自己解压的jar目录         ClassPool.getDefault().insertClassPath("D:\\aspose-words-21.11.0-java\\lib\\aspose-words-21.11.0-jdk17.jar");         //获取指定的class文件对象         CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzXDb");         //从class对象中解析获取指定的方法         CtMethod[] methodA = zzZJJClass.getDeclaredMethods("zzY0J");         //遍历重载的方法         for (CtMethod ctMethod : methodA) {             CtClass[] ps = ctMethod.getParameterTypes();             if (ctMethod.getName().equals("zzY0J")) {                 System.out.println("ps[0].getName==" + ps[0].getName());                 //替换指定方法的方法体                 ctMethod.setBody("{this.zzZ3l = new java.util.Date(Long.MAX_VALUE);this.zzWSL = com.aspose.words.zzYeQ.zzXgr;zzWiV = this;}");             }         }         //这一步就是将破译完的代码放在桌面上         zzZJJClass.writeFile("C:\\Users\\roc\\Desktop\\");         //获取指定的class文件对象         CtClass zzZJJClassB = ClassPool.getDefault().getCtClass("com.aspose.words.zzYKk");         //从class对象中解析获取指定的方法         CtMethod methodB = zzZJJClassB.getDeclaredMethod("zzWy3");         //替换指定方法的方法体         methodB.setBody("{return 256;}");         //这一步就是将破译完的代码放在桌面上         zzZJJClassB.writeFile("C:\\Users\\roc\\Desktop\\");     } catch (Exception e) {         System.out.println("错误==" + e);     } }`

运行修改方法后会在桌面生成 com 修改后的文件夹

### 3\. 修改jar包里面的数据

为了不修改原jar包建议复制一份重命名。

1.  打开jar包将桌面com文件夹覆盖到jar包com文件夹

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/51f8663f9b7e4bebb2ab65d9f0a513d6~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?) 2. 删除jar包里面的`.RSA`和`.SF`文件

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5fd5c4f19f3b4ae6b719ab99bb4674d3~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?)

### 4\. 重新导入修改后的jar包进行测试

1.  maven移除旧的jar包，导入修改后的jar包
2.  调用测试方法进行测试转换后的文件是否去除水印和数量限制成功

java

复制代码

`String sourceFile = "D:\\b.doc";//输入的文件 String targetFile = "D:\\转换后.pdf";//输出的文件 /**  * Word转PDF操作  *  * @param sourceFile 源文件  * @param targetFile 目标文件  */ public static void doc2pdf(String sourceFile, String targetFile) {     try {         long old = System.currentTimeMillis();         FileOutputStream os = new FileOutputStream(targetFile);         com.aspose.words.Document doc = new com.aspose.words.Document(sourceFile);         doc.save(os, com.aspose.words.SaveFormat.PDF);         os.close();         long now = System.currentTimeMillis();         System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时     } catch (Exception e) {         e.printStackTrace();     } }`

> 声明 **本方法只做个人研究学习使用，切勿用于商用。** 其他参考 [Aspose.PDF for Java21.11去除水印和数量限制](https://juejin.cn/post/7034398528067764231 "https://juejin.cn/post/7034398528067764231")  
> [Aspose.Cells for Java21.11去除水印和数量限制](https://juejin.cn/post/7034405665376321567 "https://juejin.cn/post/7034405665376321567")  
> [Aspose.Slides for Java21.10去除水印和数量限制](https://juejin.cn/post/7034415238602555405 "https://juejin.cn/post/7034415238602555405")

本文转自 <https://juejin.cn/post/7034387646168186894>，如有侵权，请联系删除。