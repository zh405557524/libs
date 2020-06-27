# android知识点
## 一、四大组件
### activity 知识点

* 生命周期

  > 1、`onCreate` 、`onRestart`、`onStart`、`onResume`、`onPause`、`onStop`、`onDestroy`
  >
  > 2、`onPause` ->`onSaveInstanceState`、`onStart`->`onRestoreInstanceState`

* 启动模式

  > 1、`standard  `    标准模式、创建实例位于栈顶，谁启动就位于谁的栈顶
  >
  > 2、`singleTop` 栈顶复用模式 启动时，位于栈顶，复用，调用`onNewIntent` ,否则创建
  >
  > 3、`singleTask` 栈内复用模式  启动时，栈内存在 复用，上面实例清除 调用`onNewIntent` 否则创建
  >
  > 4、`singleInstance` 单例模式 寻找任务栈，存在直接复用，不存在创建任务栈，并将`activity`放入其中.

* 启动流程


## activity面试题

* `Activity A `打开`Activity B` 按返回键的生命流程

  > A `onPause` -> B `onCreate` -> B `onStart`-> B `onResume` ->A `onStop` ->back ->B `onPause` -> A `onRestart` -> A `onStart` -> A `onResume` -> B `onStop` -> B `onDestroy`

* 生命周期

  > 1 、`startService/stopService ` : `onCreate`-> `onStartCommand`->`ondestroy`
  >
  > 2、`bindService/unbindService`: `onCreate`->`onBind`->`onUnBind`->`onDestroy`
  >
  > 3、混合 同时使用  `onCreate` 只会执行一次，只有解除绑定后 `stopService` 才会生效

* `intentService`

  * 生命周期 `onCreate` ->`onStartCommand`->`onHandleIntent` ->`onDestroy`
  * `onhandleIntent` 在子线程，并且任务执行完，则销毁。
  * 多次调用，耗时任务在 `onhandleIntent`中执行，第一个执行完，才执行第二个。

## service 面试题

## service 知识点

* 生命周期

  > 1 、`startService/stopService ` : `onCreate`-> `onStartCommand`->`ondestroy`
  >
  > 2、`bindService/unbindService`: `onCreate`->`onBind`->`onUnBind`->`onDestroy`
  >
  > 3、混合 同时使用  `onCreate` 只会执行一次，只有解除绑定后 `stopService` 才会生效

* `intentService`

  * 生命周期 `onCreate` ->`onStartCommand`->`onHandleIntent` ->`onDestroy`
  * `onhandleIntent` 在子线程，并且任务执行完，则销毁。
  * 多次调用，耗时任务在 `onhandleIntent`中执行，第一个执行完，才执行第二个。

## service 面试题

* `startService`多次start会什么反应？ 会多次调用 `onStartCommand`

* `bindService` 多次`bind`会有什么反应？ 传入的`serviceConnection`参数是之前绑定的  无反应。

* 远程服务的创建过程

  * `step1` 在对应的文件目录下创建 `aidl `文件
  * `step2` 创建一个`service` ，并在内部实现`aidl `的接口函数，`onBind `返回这个实例。
  * `step3` 在`Android androidManifest` 设置该 `service` 对应的属性
  * `step4` 在client 来`bindservice` ，并在`serviceConnecet` 回调参数中 获取`onBind `返回的实例，来与`remote`进行交互
  * 保活，1 两个服务相互监听， 2 在断开服务链接 时 重新绑定， 3 使用死亡代理，service 死掉后，重新绑定。

* `service` 与 `intentService` 的区别

  * * `service`

      >  1、无法处理耗时任务，除非开启一个线程
      >
      >  2、长时间运行在后台，除非调用`stop`

    * `intentService`

      > 1、开启后 在`onHandleIntent`执行耗时操作，不会阻塞应用程序的主线程。
      >
      > 2、执行完任务后，自动停止
      >
      > 3、多次启动，耗时操作会以工作队列的方式在`onHandleIntent`回调中执行，并且，每次只会执行一个工作线程。



## 广播

* 特点：1 收到广播，无进程，自动创建 2 应用必须被打开过，广播才被执行。 3 强行停止后，不会自己创建进程，除非用户自己手动打开界面。
* 无序广播与有序广播。
  * 无序 注册可接受，不可中断、修改
  * 有序 按优先一级级传递，可中断、修改

* 动态广播，代码注册
* 静态广播，`androidMainfest` 注册，，开机，`sd`卡 。

## `ContentProvider`

* 管理对结构化的数据集方问。内部，实现了 增删改查四种操作。 `onCreate` 优先`appliaction`的创建。 `oncreate` 为什么在`application`之前

* 数据的升级

* `sql`语句

  * 创建表

    ~~~java
    CREATE TABLE <表名>(<列名> <数据类型>[列级完整性约束条件]
                      [,<列名> <数据类型>[列级完整性约束条件]]…);
    ~~~

  * 删除表`DROP TABLE <表名>;`

  * 清空表 `TRUNCATE TABLE <表名>;`

  * 修改表

    ~~~java
    -- 添加列
    ALTER TABLE <表名> [ADD <新列名> <数据类型>[列级完整性约束条件]]
    -- 删除列
    ALTER TABLE <表名> [DROP COLUMN <列名>]
    -- 修改列
    ALTER TABLE <表名> [MODIFY COLUMN <列名> <数据类型> [列级完整性约束条件]]
    ~~~

  * 查询

    ~~~java
    SELECT [ALL | DISTINCT] <目标列表达式>[,<目标列表达式>]…
      FROM <表名或视图名>[,<表名或视图名>]…
      [WHERE <条件表达式>]
      [GROUP BY <列名> [HAVING <条件表达式>]]
      [ORDER BY <列名> [ASC|DESC]…]
    ~~~


##
## 二、自定义View
## 三、动画
## 四、IPC
## 五、framwork层、开机启动，AMS
## 六、apk相关，打包，编译，安装，签名
## 七、虚拟机编译过程
## 八、性能优化
## 九、热更新



