# libs 
    android lib 库。常用的java工具封装，ui布局封装。
##一 UI布局
    采用标准分辨率（1080*1920），生成对应的分辨率的尺寸 
##二 初始化
    application创建时，生成的工具
* com.soul.lib.base.Global 提供两个handler线程，一个为主ui线程，另一个为子线程。
##三 模块(module)
* 事件传递event  
* 网络状态监听
* android System 控制 (音频)
##四 utils 工具类
* app帮助类，主要做线程判断
 `com.soul.lib.utils.AppUtil`
* app 信息类，其他app的信息
 `com.soul.lib.utils.AppUtils`
* 状态栏相关工具类，状态的高度，颜色获取和调整
`com.soul.lib.utils.BarUtils`
* 
* 
* 
* 
* 
##五 线程池
    创建多个线程使用    
 `com.soul.lib.thread.ThreadFactory`