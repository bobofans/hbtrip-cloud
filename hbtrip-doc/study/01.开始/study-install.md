这里整体描述理解yudao-cloud的框架学习过程，

# 1.开始
## 1.1.完成code下载
目前yudao-cloud的代码已经开源，可以到github上clone下来，
https://github.com/YunaiV/yudao-cloud
另yudao-cloud 提供了一个yudao-cloud-mini的版本，只支持sytem与infra模块
这里单独下载cloud-mini:
https://gitee.com/yudaocode/yudao-cloud-mini/

## 1.2.切换分支
yudao-cloud的代码已经分了多个分支，分别支持jdk8以及jdk17.可切换使用 master-jdk17的分支

# 2.转换
## 2.1 一健换包
进入yudao-gateway项目，找到ProjectReactor.java文件。针对配置主要有以下
```java
   String groupIdNew = "com.github.bobofans";
   String artifactIdNew = "hbtrip";
   String packageNameNew = "com.github.bobofans.hbtrip";
   String titleNew = "湖北旅游管理系统";
```
这里依据项目名称，进行调整，依据本人情况，初始化hbtrip,简化问题处理。

## 2.2 Gradle
yudao项目主要使用Maven3构建。这里会学习过程中，将代码Gradle构建

执行转换过程中，需要在根目录下先执行maven install -Dmaven.test.skip=true，否则会报错。

```console
 gradle init
```
执行完成后，会不执行，考虑后续升级同步yudao代码。只在buildSrc中进行处理

```groovy
plugins {
id 'java'
id 'java-library'
id 'idea'
id 'war'   //重点要增加这个
}

repositories {
mavenLocal()

    maven {
        name=name("Alibaba")
        allowInsecureProtocol = true
        url= uri("http://maven.aliyun.com/nexus/content/groups/public/")
    }

    mavenCentral()
}

group = 'com.github.bobofans'
version = '1.0.0-SNAPSHOT'
java.sourceCompatibility = JavaVersion.VERSION_17

dependencies { //提供lombook依据支持，不使用plugin方式
compileOnly 'org.projectlombok:lombok:1.18.34'
annotationProcessor 'org.projectlombok:lombok:1.18.34'
testCompileOnly 'org.projectlombok:lombok:1.18.34'
testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'
}

tasks.withType(JavaCompile) {
options.encoding = 'UTF-8'
options.warnings = false
options.deprecation = false
options.compilerArgs += ["-Xlint:deprecation","-Xlint:unchecked"]
}

tasks.withType(Javadoc) {
options.encoding = 'UTF-8'
}

test {
useJUnitPlatform()
}

configurations {
defaultTasks("clean","build")
}
```

## 2.3 Apifox 接口工具

点击 Apifox (opens new window)首页，下载对应的 Apifox 桌面版


# 3.基础设施
## 3.1.初始化MYSQL
执行创建数据库ruoyi-vue-pro，只用执行ruoyi-vue-pro.sql，目前选择mysql数据库。
默认配置下，MySQL 需要启动在 3306 端口，并且账号是 root，密码是 123456。如果不一致，需要修改 application-local.yaml 配置文件。

## 3.2.初始化Redis
需要安装redis
默认配置下，Redis 启动在 6379 端口，不设置账号密码。如果不一致，需要修改 application-local.yaml 配置文件。

运行Redis
```console
redis-server.exe redis.windows.conf
```
## 3.3.初始化Nacos
项目使用 Nacos 作为注册中心和配置中心.需要安装nacos

运行Nacos
```console
startup.cmd -m standalone
```
安装完成之后，需要创建 dev 命名空间，如下图所示：

# 4.基础设施(可选)
## 4.1.初始化RocketMQ



## 4.2.初始化XXL-JOB


## 4.3.初始化Seata


## 4.4.初始化 Sentinel


## 4.5.初始化Elasticsearch


# 5.服务
## 5.1 gateway服务
执行 GatewayServerApplication (opens new window)类，进行启动。

启动完成后，使用浏览器访问 http://127.0.0.1:48080 (opens new window)地址，返回如下 JSON 字符串，说明成功。

友情提示：注意，默认配置下，网关启动在 48080 端口。
```json
{"code":404,"data":null,"msg":null}
```

## 5.2 system服务
执行 SystemServerApplication (opens new window)类，进行启动。

启动完成后，使用浏览器访问 http://127.0.0.1:48081/admin-api/system/ (opens new window)和 http://127.0.0.1:48080/admin-api/system/ (opens new window)地址，都返回如下 JSON 字符串，说明成功。

友情提示：注意，默认配置下，yudao-module-system 服务启动在 48081 端口。

```json
{"code":401,"data":null,"msg":"账号未登录"}
```


## 5.3 infra 服务

执行 InfraServerApplication (opens new window)类，进行启动。

启动完成后，使用浏览器访问 http://127.0.0.1:48082/admin-api/infra/ (opens new window)和 http://127.0.0.1:48080/admin-api/infra/ (opens new window)地址，都返回如下 JSON 字符串，说明成功。

友情提示：注意，默认配置下，yudao-module-infra 服务启动在 48082 端口。

```json
{"code":401,"data":null,"msg":"账号未登录"}
```







