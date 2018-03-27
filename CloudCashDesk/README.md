**项目说明**
----
[Deprecated]云收银客户端, 迁移到模块化版本： http://git.2dfire-inc.com/ccd-android/CloudCashDesk

**模块说明**

各模块项目工程: http://git.2dfire-inc.com/groups/ccd-android

具体使用可以参考对应项目工程下的Readme，模块如下：

* [[CCDRes]](http://git.2dfire-inc.com/ccd-android/CCDRes) 资源模块
* [[CCDUtils]](http://git.2dfire-inc.com/ccd-android/CCDUtils) 工具方法模块
* [[CCDWidget]](http://git.2dfire-inc.com/ccd-android/CCDWidget) 自定义控件模块
* [[CCDBean]](git@git.2dfire-inc.com:ccd-android/CCDBean.git) 实体类模块:
* [[CCDBase]](http://git.2dfire-inc.com/ccd-android/CCDBase) 核心依赖模块
* [[CCDPrint]](http://git.2dfire-inc.com/ccd-android/CCDPrint) 打印模块
* [[CCDScan]](http://git.2dfire-inc.com/ccd-android/CCDScan) 扫一扫模块
* [[CCDPos]](http://git.2dfire-inc.com/ccd-android/CCDPos) POS模块
* [[CCDMenu]](http://git.2dfire-inc.com/ccd-android/CCDMenu) 菜单购物车模块
* [[CCDReceipt]](http://git.2dfire-inc.com/ccd-android/CCDReceipt) 收款模块
* [[CCDTakeout]](http://git.2dfire-inc.com/ccd-android/CCDTakeout) 外卖模块

模块repo管理参考[[CCDManifest]](http://git.2dfire-inc.com/ccd-android/CCDManifest)


**App包结构说明**

* [app]        全局类。
* [constant]   常量。
* [data]      获取数据。
* [dispatcher]  事件分发。
* [event]   自定义事件。
* [helper]   帮助类。
* [module]    具体业务模块。


--------------
**使用说明**
### 编译环境

配置文件在项目根目录下的gradle.properties
```Java
org.gradle.jvmargs=-Xmx3072m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true

JDK = 1.8

gradle_plugin = 2.2.2
gradle_wrapper = 2.14.1

COMPILE_SDK_VERSION = 25
BUILD_TOOLS_VERSION = 25.0.1
SUPPORT_LIB_VERSION = 25.0.1

MIN_SDK_VERSION = 15
TARGET_SDK_VERSION = 25

VERSION_CODE = 10000
VERSION_NAME = 1.0.0

```

### 贡献说明
1.每次提交commit时，注明下修改类型

* [fix] 修改问题, 如果有问题跟踪，请带上issueId
* [add] 添加功能或文件
* [remove] 移除功能或文件
* [refactor] 重构
* [update] 通用改动

例如：[fix] 点击登录时空指针异常 issueId：#3451

2.分支说明

master分支为主要开发分支，并且受到保护，只有**通过的merge request**才能合并到master分支。master分支上保持最新代码。

新的改动需要基于master最新分支创建新的分支，分支的命名类似于上文中commit的开头。例如```fix-login-nullpointer```

改动完成后发起```merge request```指定一人进行review，有必要的话在下方的描述框中@相关的人一起评审，比如影响较广泛则可以@所有人，或者@对修改的这块代码比较熟悉的人。merge完成后删除这个分支。新的改动需在master上重新拉出一个分支再修改。

>
为了更方便阅读和撤销等操作，每次commit尽量保证只做一件事.
同样的，每个merge request最好只包含一个feature或者fix.



3.提交merge request前先进行**静态代码检查**
命令：
```
 [./]gradlew check
```
报告会生成在```app/build/reports/```文件夹中

>
check命令包含了pmd findbugs checkstyle lint检查，也可以_单独运行_，例如：
```
[./]gradlew checkstyle
```
4.渠道打包
java -jar ./walle-cli-all.jar batch  -f ./app/channel {apk-to-release}

### 主要引用

Gson: https://github.com/google/gson

Glide: https://github.com/bumptech/glide

ButterKnife: https://github.com/JakeWharton/butterknife/

EventBus：https://github.com/greenrobot/EventBus/

Rxjava：https://github.com/ReactiveX/RxJava

RxAndroid： https://github.com/ReactiveX/RxAndroid

Rxbinding：https://github.com/JakeWharton/RxBinding/