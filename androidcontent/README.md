# android知识点
## 一、android组件
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

## 二、自定义View
### 1、paint
* 1、概念
      画笔，保存了绘制几何图形，文本，和位图的样式和颜色
  
* 2、常用api
      常用api主要如颜色，效果和文本相关等。
~~~
        mPaint = new Paint();//初始化
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setARGB(255, 255, 255, 0);//设置paint对象颜色，范围0~255
        mPaint.setAlpha(200);//设置alpha 不透明，范围0~255
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//描边效果 FILL 填充;STROKE 描边; FILL_AND_STROKE 填充并表变
        mPaint.setStrokeWidth(4);//描边宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角效果 BUTT 默认; ROUND 圆角;SQUARE 方形
        mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格 MITER 尖角;ROUND 切除尖角;BEVEL 圆角
        
        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED));//设置环形渲染器
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));//设置图层混合模式
        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000));//设置颜色过滤器
        mPaint.setFilterBitmap(true);//设置双线性过滤
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜，传入度数和样式
        mPaint.setTextScaleX(2);//设置文本缩放倍数
        mPaint.setTextSize(38);//设置字体大小
        mPaint.setTextAlign(Paint.Align.LEFT);//对齐方式
        mPaint.setUnderlineText(true);//设置下划线

        String str = "Android 高级工程师";
        final Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect);//测量文本大小，将文本大小信息存放到rect中
        mPaint.measureText(str);//获取文本的宽
        mPaint.getFontMetrics();//获取字体度量对象
~~~

* 3、Paint详解-颜色相关

      setColor(int color)参数具体的颜色值，16进制数值，0xffff0000
      setARGB(int a,int r,int g,int b) 参数分别透明度，红，绿，蓝。0~255数值
      setShader(Shader shader) 参数着色器对象，一般使用shader的几个子类
            LinearGradient:线性渲染
            RadialGradient:环形渲染
            SweepGradient :扫描渲染
            BitmapShader  :位图渲染
            ComposeShader :组合渲染，例如 LinearGradient+BitmapShader 
      setColorFilter(ColorFilter colorFilter) 设置颜色过滤。一般是ColorFilter三个子类
            LightingColorFilter:光照效果
            PorterDuffColorFIlter:指定已颜色和一种PorterDuff.Mode 与绘制对象进行合成
            ColorMatrixColorFilter:使用一个ColorMatrix来对颜色进行处理
1. linearGradient线性渲染
        
       构造方法：
       LinearGradient(float x0,float x1,float y1,int color0,int color1,Float[]{z1,z2},Shader.TileMode tile)
       参数
       x0 y0 x1 y1:渐变的两个端点的位置
       color0 color1 是端点的颜色
       z1,z2 颜色在布局中开始的比例
       tile：端点范围之外的着色规则，类型是TileMode
       
       TileMode.CLAMP:绘制区域超过渲染区的部分，重复排版
       TileMode.CLAMP:绘制区域超过渲染区的部分，会以最后一个像素拉伸排版
       TileMode.MIRROR:绘制区域超过渲染区的部分,镜像翻转排版
       
       使用：
       mShader = new LinearGradient(0,0,500,500,new int[]{Color.RED,Color.BLUe},
       null,Shader.TileMode.CLAMP);
       mPaint.setShader(mShader);
       canvas.drawCircle(250,250,250,mPaint);

2. RadialGradient 环形渲染

        构造方法:
        RadialGradient(float centerX,float cententY,float radius,int centerColor,int edgeColr,ileMode tileMode)
        
        参数:
        centerX centerY:辐射中心的坐标
        radius:辐射半径
        centerColor:辐射中心的颜色
        edgeColor:辐射边缘的颜色
        tileMode:辐射范围之外的着色规则，类型是TIleMode
        
        使用:
        mShader = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED},
                null, Shader.TileMode.CLAMP)
        mPaint.setShader(mShader);
        canvas.drawCircle(250,250,250,mPaint);
    
3. SweepGradient扫描渲染
   
         构造方法:
         SweepGradient(float cx,float cy,int color0,int color1)
         
         参数:
         cx cy :扫描中心
         color0:扫描的其实颜色
         color1:扫描的终止颜色
         
         使用:
         mShader  = new SweepGradient(250,250,Color.RED,Color.GREEN);
         mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
     
4. 位图渲染

         构造方法
         BitmapShader(Bitmap bitmap,Shader.TileMode titleX,Shader.TileMode tileY)
         
         参数
         bitmap:用来做模板的bitmap对象
         tileX:横向着色规则，类型是TileMode
         tileY:纵向着色规则，类型是TileMode
         
         使用：
         mShader = new BitmapShader(mBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
         mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
5. 组合渲染

         构造方法：
         ComposeShader(Shader shaderA,Shader shaderB,PorterDuff.Mode mode)
         
         参数
         shaderA,shaderB:两个相继使用的Shader
         mode:两个Shader的叠加模式，即ShaderA和ShaderB 应该怎样共同绘制。它的类型是PorterDuff.Mode
         
         使用：
         BitmapShader bitmapShader = new BitmapShader(mBItmap,
         Shader.Tile.REPEAT,Shader.TileMode.REPEAT);
         LinearGradient linearGradient = new LInearGradient(0,0,1000,16000,new 
         int[]{Color.RED,Color.GREEN,Color.BLUE},null,Shader.TileMode.CLAMP);
         mShader = new ComposeShader(bitmapShader,linearGradient,PorterDuff.Mode.MULTIPLY);
          mPaint.setShader(mShader);
         canvas.drawCircle(250,250,250,mPaint);
     
7. PorterDuff.Mode图层混合模式

        它将所绘制图形的像素与Canvas中对应位置的像素按照一定规则进行混合，形成新的像素值，从而更新Canvas中
        最终的像素颜色值。
        
        18种模式
        Mode.CLEAR    Mode.SRC      Mode.DST
        Mode.SRC_OVER Mode.DST_OVER Mode.SRC_IN
        Mode.DST_IN   Mode.SRC_OUT  Mode.DST_OUT
        Mode.SRC_ATOP Mode.DST_ATOP Mode.XOR
        Mode.DARKEN   Mode.LIGHTEN  Mode.MULTIPLY
        Mode.SCREEN   Mode.OVERLAY  Mode.ADD
    
    
    ​    
~~~
            //所有绘制不会提交到画布上
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            //显示上层绘制的图像
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            //显示下层绘制图像
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            //正常绘制显示，上下层绘制叠盖
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            //上下层都显示，下层居上显示
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            //取两层绘制交集，显示上层
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            //取两层绘制交集，显示上层
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            //取上层绘制非交集部分，交集部分变透明
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            //取下层绘制非交集部分，交集部分变透明
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            //取上层交集部分与下层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            //取下层交集部分与下层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            //去除两图层交集部分
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            //取两图层全部区域，交集本分颜色加深
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            //取两图层全部区域，交集本分颜色点亮
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            //取两图层交集部分，颜色叠加
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            //取两图层全部区域，交集部分虑色
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
            //取两图层全部区域，交集部分饱和度相加
            new PorterDuffXfermode(PorterDuff.Mode.ADD),
            //取两图层全部区域，交集部分叠加
            new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
~~~
  ![18种图层混合模式](https://github.com/zh405557524/AndroidAdvanceLearn/blob/master/1_ui/0_ui_core/2_paint/1_paint/device-2019-04-16-212639.png)
8. 离屏绘制

        通过使用离屏缓冲，把要绘制的内容单独绘制在缓冲层，保证Xfermode的使用不会出现错误的结果。
        
        使用离屏绘缓冲有两种方式:
        Canvas.saveLayer() 可以做短时的离屏缓冲，在绘制之前保存，绘制之后恢复:
        int saveId = canvas.saveLayer(0,0,width,height,Canvas.ALL_SAVE_FLAG);
        Canvas.drawBItmap(rectBitmap,0,0,paint);画方
        Paint.setXfermode(xfermode);//设置xfermode
        Canvas.drawBitmap(circleBitmap,0,0,paint);//画圆
        Paint.setXfermode(null);//用完及时清除Xfermode
        cavans.restoreToCount(saveId);
    
    
    ​    
        View.setLayerType() 直接把整个View都绘制在离屏缓冲中。
        setLayerType(LAYER_TYPE_HARDWARE)使用GPU来缓冲，
        setLayerTYpe(LAYER_TYPE_SOFTWARE) 使用一个bitmap来缓冲

* 4、 paint详解-效果相关

1. LightingColorFilter滤镜

       LightingColorFilter
       构造方法：
       LightingColorFilter(int mul,int add)
    
       参数:
       mul 和 add 都是和颜色值格式相同的int值，其中mul用来和目标像素相乘，add用来和目标像素相加:
       R` = R*mul.R/0xff+add.R
       G` = G*mul.G/0xff+add.G
       B` = B*mul.B/0xff+add.B
       
       使用:
       ColorFilter lighting = new LightingColorFilter(0x00ffff,0x000000);
       paint.setColorFilter(lighting);
       canvas.drawBitmap(bitmap,0,0,paint);

2. PorterDuffColorFilter 滤镜

       PorterDuffColorFilter
       构造方法:
       PorterDuffColorFilter(int color,PorterDuff.Mode mode)
       
       参数:
       color,具体的颜色值，例如Color.RED
       mode ,指定PorterDuff.Mode 混合模式
    
       使用:
       PorterDuffColorFilter porterDuffColorFilter = new 
       PorterDuffColorFilter(Color.RED,PorterDuff.Mode.DARKEN);
       paint.setColorFilter(porterDuffColorFilter);
       cavans.drawBitmap(mBitmap,100,0,paint);

3. ColorMatrixColorFilter 滤镜

       ColorMatrixColorFilter
       构造方法:
       ColorMatrixColorFilter(float[] colorMatrix);
       
       参数:
       colorMatrix 矩阵数组 
       
       使用:
       float[] colorMatrix = {
            1,0,0,0,0,//red
            0,1,0,0,0,//green
            0,0,1,0,0,//blue
            0,0,0,1,0 //alpha
       }
       mColorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
       mPaint.setColorFilter(mColorMatrixColorFilter);
       canvas.drawBitmap(mBitmap,100,0,mPaint);
    ![色彩矩阵分析](https://raw.githubusercontent.com/zh405557524/AndroidAdvanceLearn/master/1_ui/0_ui_core/2_paint/1_paint/1671555555375.jpg)  
   
    
### 2、canvas

* 1、概念
  
     画布，通过画笔绘制几何图形、文本、路径和位图等
     
* 2、常用api类型

     常用api分为绘制，变换，状态保存和恢复

1. 绘制几何图形，文本，位图等 

       view drawBitmap(Bitmap bitmap,float left,float top,Paint paint);在指定坐标绘制位图
     
       void drawLine(float startX,float startY,float stopX,float stopY,Paint paint);根据给定的起始点和结束点之间绘制连线
       
       void drawPath(Path path,Paint paint);根据给定的path，绘制连线。
       
       void drawPoint(float X,float y,Paint paint);根据给定的坐标，绘制点。
       
       void drawText(String text,int start,int end,Paint paint);根据给定的坐标，绘制文字
       ...
2. 位置，形状变换等

       void translate(float dx,float dy);平移操作
       
       void scale(float sx,float sy);缩放操作
       
       void rotate(float degrees);旋转操作
       
       void skew(float sx,float sy);倾斜操作
       
       void clipXXX(...);//切割操作，参数指定区域内可以继续绘制
       
       void clipOutXXX(...);反向切割操作，参数指定区域内部不可以绘制
       
       void setMatrix(Matrix matrix);可通过matrix实现平移，缩放，旋转等操作。
    
3. 状态保存和恢复

       Canvas 调用了translate,scale,rotate,skew,clipRect等变化后，后续的操作都是基于变化后的Canvas,都会收到影响，对于后续的操作很不方便。Canvas提供了save，saveLayer,saveLayerAlpha,restore,restoreToCount来保存和恢复状态。
       
       int state = canvas.save();//保存状态1 
       canvas.translate(70,50);
       canvas.drawRect(0,0,400,400,mPaint);
       canvas.save();//保存状态2 
       canvas.restore();//返回最新状态(状态2)
       mPaint.setColor(Color.BLUE);
       canvas.drawRect(0,0,400,400,mPaint);
       canvas.restoreToCount(state);//手动指定的返回到状态1
### 3、path
* 1、概念
    路径，可用于绘制直线，曲线构成几何路径，还可用于根据路径绘制文字
* 2、常用api
    常用api如移动，连线，闭合，添加图形等。
* 3、[贝塞尔曲线](https://github.com/zh405557524/AndroidNote2/blob/master/CustomView/Advance/%5B06%5DPath_Bezier.md)



### 4、matrix
### 5、anim(动画)

### 6、touch(事件分发模型)
### 7、screen(屏幕适配)
### 8、project

## 四、IPC
## 五、framwork层、开机启动，AMS
## 六、apk相关，打包，编译，安装，签名
## 七、虚拟机编译过程
## 八、性能优化
## 九、热更新



