> 前言 工欲善其事，必先利其器

源码分析
----

### 1\. 下载[Aspose.PDF for Java21.11官方jar包](https://link.juejin.cn?target=https%3A%2F%2Fdownloads.aspose.com%2Fpdf%2Fjava "https://downloads.aspose.com/pdf/java")

### 2\. 开始分析

1.  调用授权方法

java

复制代码

`InputStream is = new FileInputStream("..license.xml"); License license = new License(); license.setLicense(is);`

license.xml文件内容这里是个过期的文件主要是格式

xml

复制代码

`<License>     <Data>         <Products>             <Product>Aspose.Total for Java</Product>             <Product>Aspose.Words for Java</Product>         </Products>         <EditionType>Enterprise</EditionType>         <SubscriptionExpiry>20991231</SubscriptionExpiry>         <LicenseExpiry>20991231</LicenseExpiry>         <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>     </Data>     <Signature>         sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=     </Signature> </License>`

2.  分析License类的`setLicense`方法 `setLicense`代码太长我就不贴了，源码里面有。  
    主要分析里面的核心代码，`setLicense`第两个重载方法主要验证在139和140行

java

复制代码

`l9f var10 = new l9f(); var10.lI(var9);`

进入`l9f`类的`lT`方法

java

复制代码

`void lI(InputStream var1) throws Exception {     l19n.lI(0);     if (var1 == null) {         throw new NullPointerException("stream");     } else {         String[] var2 = new String[]{new String(new byte[]{97, 115, 112, 111, 115, 101, 46, 116, 111, 116, 97, 108}), new String(new byte[]{99, 111, 110, 104, 111, 108, 100, 97, 116, 101, 46, 116, 111, 116, 97, 108})};         Document var3 = com.aspose.pdf.internal.l1f.ld.lc().parse(var1);         boolean var4 = true;         if (!this.lI(var3)) {             var4 = false;         }         this.ly();         Iterator var5 = (com.aspose.pdf.internal.l1f.lu.lI() ? (ArrayList)l0t.get() : l9f.lf.lf).iterator();         while(var5.hasNext()) {             String var6 = (String)var5.next();             if (this.lv.equals(var6)) {                 throw new IllegalStateException(new String(new byte[]{84, 104, 105, 115, 32, 108, 105, 99, 101, 110, 115, 101, 32, 105, 115, 32, 100, 105, 115, 97, 98, 108, 101, 100, 44, 32, 112, 108, 101, 97, 115, 101, 32, 99, 111, 110, 116, 97, 99, 116, 32, 65, 115, 112, 111, 115, 101, 32, 116, 111, 32, 111, 98, 116, 97, 105, 110, 32, 97, 32, 110, 101, 119, 32, 108, 105, 99, 101, 110, 115, 101, 46}));             }         }         if (this.lc() != l9f.lj.lj) {             String var12 = this.lc() == l9f.lj.lI ? var2[0] : var2[1];             if (l19n.lj() <= 0) {                 boolean var13 = false;                 String var7 = "";                 String[] var8 = this.lh;                 int var9 = var8.length;                 int var10 = 0;                 while(var10 < var9) {                     String var11 = var8[var10];                     var7 = l10l.lu(var11);                     if (!var12.equals(var7) && !(var12 + " for java").equals(var7) && !(var12 + " product family").equals(var7)) {                         if (this.lc() == l9f.lj.lI && l10l.lu(com.aspose.pdf.internal.l1f.lI.lt).equals(var7)) {                             var13 = true;                             break;                         }                         if (this.lc() == l9f.lj.lI && var11.equals(com.aspose.pdf.internal.l1f.lI.lt.concat(new String(new byte[]{32, 102, 111, 114, 32, 74, 97, 118, 97})))) {                             var13 = true;                             break;                         }                         if (this.lc() == l9f.lj.lI && l10l.ly(var11).equals(l10l.ly(com.aspose.pdf.internal.l1f.lI.lt.concat(new String(new byte[]{32, 80, 114, 111, 100, 117, 99, 116, 32, 70, 97, 109, 105, 108, 121}))))) {                             var13 = true;                             break;                         }                         if (this.lc() != l9f.lj.lI || !var11.equals(com.aspose.pdf.internal.l1f.lI.lj.concat(new String(new byte[]{32, 102, 111, 114, 32, 74, 97, 118, 97}))) && !var11.equals(com.aspose.pdf.internal.l1f.lI.lj.concat(new String(new byte[]{32, 80, 114, 111, 100, 117, 99, 116, 32, 70, 97, 109, 105, 108, 121})))) {                             ++var10;                             continue;                         }                         var13 = true;                         break;                     }                     var13 = true;                     break;                 }                 if (!var13) {                     throw new IllegalStateException(new String(new byte[]{84, 104, 101, 32, 108, 105, 99, 101, 110, 115, 101, 32, 105, 115, 32, 110, 111, 116, 32, 118, 97, 108, 105, 100, 32, 102, 111, 114, 32, 116, 104, 105, 115, 32, 112, 114, 111, 100, 117, 99, 116, 46, 32, 70, 111, 114, 32, 102, 114, 101, 101, 32, 116, 101, 99, 104, 110, 105, 99, 97, 108, 32, 115, 117, 112, 112, 111, 114, 116, 44, 32, 112, 108, 101, 97, 115, 101, 32, 112, 111, 115, 116, 32, 116, 104, 105, 115, 32, 101, 114, 114, 111, 114, 32, 97, 110, 100, 32, 116, 104, 101, 32, 102, 105, 108, 101, 32, 105, 110, 32, 116, 104, 101, 32, 65, 115, 112, 111, 115, 101, 46, 80, 68, 70, 32, 70, 111, 114, 117, 109, 115, 32, 104, 116, 116, 112, 58, 47, 47, 119, 119, 119, 46, 97, 115, 112, 111, 115, 101, 46, 99, 111, 109, 47, 99, 111, 109, 109, 117, 110, 105, 116, 121, 47, 102, 111, 114, 117, 109, 115, 47, 97, 115, 112, 111, 115, 101, 46, 112, 100, 102, 45, 112, 114, 111, 100, 117, 99, 116, 45, 102, 97, 109, 105, 108, 121, 47, 50, 48, 47, 115, 104, 111, 119, 102, 111, 114, 117, 109, 46, 97, 115, 112, 120}));                 } else {                     Date var14 = (new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)).parse(com.aspose.pdf.internal.l1f.lI.lu);                     if (var14.after(this.lc)) {                         throw new IllegalStateException(new String(new byte[]{84, 104, 101, 32, 115, 117, 98, 115, 99, 114, 105, 112, 116, 105, 111, 110, 32, 105, 110, 99, 108, 117, 100, 101, 100, 32, 105, 110, 32, 116, 104, 105, 115, 32, 108, 105, 99, 101, 110, 115, 101, 32, 97, 108, 108, 111, 119, 115, 32, 102, 114, 101, 101, 32, 117, 112, 103, 114, 97, 100, 101, 115, 32, 117, 110, 116, 105, 108, 32}) + (new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)).format(this.lc) + ", " + new String(new byte[]{98, 117, 116, 32, 116, 104, 105, 115, 32, 118, 101, 114, 115, 105, 111, 110, 32, 111, 102, 32, 116, 104, 101, 32, 112, 114, 111, 100, 117, 99, 116, 32, 119, 97, 115, 32, 114, 101, 108, 101, 97, 115, 101, 100, 32, 111, 110, 32}) + (new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)).format(var14) + ". " + new String(new byte[]{80, 108, 101, 97, 115, 101, 32, 114, 101, 110, 101, 119, 32, 116, 104, 101, 32, 115, 117, 98, 115, 99, 114, 105, 112, 116, 105, 111, 110, 32, 111, 114, 32, 117, 115, 101, 32, 97, 32, 112, 114, 101, 118, 105, 111, 117, 115, 32, 118, 101, 114, 115, 105, 111, 110, 32, 111, 102, 32, 116, 104, 101, 32, 112, 114, 111, 100, 117, 99, 116, 46}));                     } else if ((new Date()).after(this.ly)) {                         throw new IllegalStateException(new String(new byte[]{84, 104, 101, 32, 108, 105, 99, 101, 110, 115, 101, 32, 104, 97, 115, 32, 101, 120, 112, 105, 114, 101, 100, 46}));                     } else if (l19n.lj() > 0) {                         throw new PdfException("Invalid license signature. Please make sure the license file was not modified.");                     } else {                         this.l0if = l10if.lf;                         Helper.help1();                         lI(this);                     }                 }             }         }     } }`

仔细观察你会发现这个方法就是验证的，主要是做验证失败抛异常和添加一些标识，这个分析下来就简单了，我们直接找到验证成功的最终执行176~179行，然后尝试修改试一下。

java

复制代码

`this.l0if = l10if.lf; Helper.help1(); lI(this);`

### 3\. 分析结果

*   修改`void lI(InputStream var1)`方法为

java

复制代码

`void lI(InputStream var1) throws Exception {     this.l0if = l10if.lf;     Helper.help1();     lI(this); }`

执行操作
----

### 1\. 添加Javassist修改class字节码文件

xml

复制代码

`<dependency>     <groupId>org.javassist</groupId>     <artifactId>javassist</artifactId>     <version>3.28.0-GA</version> </dependency>`

### 2\. 添加修改方法

java

复制代码

`/**  * 修改pdf jar包里面的校验  */ public static void modifyPDFJar() {     try {         //这一步是完整的jar包路径,选择自己解压的jar目录         ClassPool.getDefault().insertClassPath("D:\\aspose.pdf-21.11-java\\lib\aspose.pdf-21.11.jar");         //获取指定的class文件对象         CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.pdf.l9f");         //从class对象中解析获取所有方法         CtMethod[] methodA = zzZJJClass.getDeclaredMethods();         for (CtMethod ctMethod : methodA) {             //获取方法获取参数类型             CtClass[] ps = ctMethod.getParameterTypes();             //筛选同名方法，入参是Document             if (ps.length == 1 && ctMethod.getName().equals("lI") && ps[0].getName().equals("java.io.InputStream")) {                 System.out.println("ps[0].getName==" + ps[0].getName());                 //替换指定方法的方法体                 ctMethod.setBody("{this.l0if = com.aspose.pdf.l10if.lf;com.aspose.pdf.internal.imaging.internal.p71.Helper.help1();lI(this);}");             }         }         //这一步就是将破译完的代码放在桌面上         zzZJJClass.writeFile("C:\\Users\\roc\\Desktop\\");     } catch (Exception e) {         System.out.println("错误==" + e);     } }`

运行修改方法后会在桌面生成 com 修改后的文件夹

### 3\. 修改jar包里面的数据

为了不修改原jar包建议复制一份重命名。

1.  打开jar包将桌面com文件夹覆盖到jar包com文件夹

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fb0e0140faf14ceaa35d20981783c946~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?) 2. 删除jar包里面的`.RSA`和`.SF`文件

![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0b7c6999875c48c798b7437d6977fff3~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?)

### 4\. 重新导入修改后的jar包进行测试

1.  maven移除旧的jar包，导入修改后的jar包
2.  调用测试方法进行测试转换后的文件是否去除水印和数量限制成功

java

复制代码

`String sourceFile = "D:\\b.pdf";//输入的文件 String targetFile = "D:\\转换后.docx";//输出的文件 /**  * PDF转Word操作  *  * @param sourceFile 源文件  * @param targetFile 目标文件  */ public static void pdf2doc(String sourceFile, String targetFile) {     try {         long old = System.currentTimeMillis();         FileOutputStream os = new FileOutputStream(targetFile);         com.aspose.pdf.Document doc = new com.aspose.pdf.Document(sourceFile);//加载源文件数据         doc.save(os, com.aspose.pdf.SaveFormat.DocX);//设置转换文件类型并转换         os.close();         long now = System.currentTimeMillis();         System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时     } catch (Exception e) {         e.printStackTrace();     } }`

> 声明 **本方法只做个人研究学习使用，切勿用于商用。** 其他参考 [Aspose.Words for Java21.11去除水印和数量限制](https://juejin.cn/post/7034387646168186894 "https://juejin.cn/post/7034387646168186894")  
> [Aspose.Cells for Java21.11去除水印和数量限制](https://juejin.cn/post/7034405665376321567 "https://juejin.cn/post/7034405665376321567")  
> [Aspose.Slides for Java21.10去除水印和数量限制](https://juejin.cn/post/7034415238602555405 "https://juejin.cn/post/7034415238602555405")

本文转自 <https://juejin.cn/post/7034398528067764231>，如有侵权，请联系删除。