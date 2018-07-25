# BToast

 [![Download](https://api.bintray.com/packages/bsss/maven/BToast/images/download.svg) ](https://bintray.com/bsss/maven/BToast/_latestVersion)
 
 an Toast Util For Android!
 
 ## Dependency
 
 #### 1
 
```Java
 buildscript {
    repositories {
        ...
        jcenter()
    }
    dependencies {
       ...
    }
 }
```

#### 2
 
```Java
 dependencies {
    ...
    implementation 'com.bravin.btoast:BToast:1.0.0'
 }
```
 
 ## Config
 
 custom your Application and override onCreate() method
 
such as:

```Java
   public class BToastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
		
        BToast.Config.getInstance()
//                .setAnimate() // Whether to startAnimation. default is fasle;
//                .setAnimationDuration()// Animation duration. default is 800 millisecond
//                .setAnimationGravity()// Animation entering position. default is BToast.ANIMATION_GRAVITY_TOP
//                .setDuration()// toast duration  is Either BToast.DURATION_SHORT or BToast.DURATION_LONG
//                .setTextColor()// textcolor. default is white
//                .setErrorColor()// error style background Color default is red
//                .setInfoColor()// info style background Color default is blue
//                .setSuccessColor()// success style background Color default is green
//                .setWarningColor()// waring style background Color default is orange
//                .setLayoutGravity()// whan show an toast with target, coder can assgin position relative to target. default is BToast.LAYOUT_GRAVITY_BOTTOM
//                .setLongDurationMillis()// long duration. default is 4500 millisecond
//                .setRadius()// radius. default is half of view's height. coder can assgin a positive value
//                .setRelativeGravity()// whan show an toast with target, coder can assgin position relative to toastself(like relativeLayout start end center), default is BToast.RELATIVE_GRAVITY_CENTER 
//                .setSameLength()// sameLength.  whan layoutGravity is BToast.LAYOUT_GRAVITY_TOP or BToast.LAYOUT_GRAVITY_BOTTOM,sameLength mean toast's width is as same as target,otherwise is same height 
//                .setShortDurationMillis()// short duration. default is 3000 millisecond
//                .setShowIcon()// show or hide icon
//                .setTextSize()// text size. sp unit
                .apply(this);// must call
    }
}
```
 
 
## Show And Usage (Attributes can be combined at will, Here is only a small amount)

### base
  
  To display an success Toast：
  
```Java
BToast.success(v.getContext())
            .text("this is text")
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/success.jpg) 
  
  
  To display an animate success Toast：(coder can assign animation direction using animationGravity. default is BToast.ANIMATION_GRAVITY_TOP. other values are BToast.ANIMATION_GRAVITY_LEFT, BToast.ANIMATION_GRAVITY_RIGHT, BToast.ANIMATION_GRAVITY_BOTTOM)
```Java
BToast.success(v.getContext())
            .text(R.string.text_test_content)
            .animate(true)
            .show();
```
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/animate_success.gif) 
  
  
  To display an success Toast with target(View)：
```Java
BToast.success(v.getContext())
            .text(R.string.text_test_content)
            .target(target)
            .show();
```
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/layout_bottom.jpg) 
  
  
### more

To display an error Toast：
  
```Java
BToast.error(v.getContext())
            .text(R.string.text_test_content)
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/error.jpg) 
   
   To display an info Toast：
  
```Java
BToast.info(v.getContext())
            .text(R.string.text_test_content)
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/info.jpg) 
   
   To display an waring Toast：
  
```Java
BToast.waring(v.getContext())
            .text(R.string.text_test_content)
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/warning.jpg) 
   
   To display an normal Toast：
  
```Java
BToast.normal(v.getContext())
            .text(R.string.text_test_content)
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/normal.jpg) 
  
  To display an rectangle success Toast：
  
```Java
BToast.success(v.getContext())
            .text(R.string.text_test_content)
            .radius(0)
            .target(target)
            .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/radius0.jpg) 
 
 
 To display an samelength success Toast with target(View)：  (samelength mean same width(layout_gravity_top layout_gravity_bottm) or same height(layout_gravity_left layout_gravity_right))
```Java
BToast.success(v.getContext())
            .text(R.string.text_test_content)
            .sameLength(true)
            .target(target)
            .show();
```
   
   
   
  ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/layout_bottom_samelength.jpg) 
 
 
 To display an relative_end samelength success Toast:
```Java
BToast.success(v.getContext())
        .text(R.string.text_test_content)
        .sameLength(true)
        .relativeGravity(BToast.RELATIVE_GRAVITY_END)
        .show();
```
   
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/relative_end_samelength.jpg) 
To display an relative_end samelength animate success Toast:
   
```Java
   BToast.success(v.getContext())
            .text(R.string.text_test_content)
            .relativeGravity(BToast.RELATIVE_GRAVITY_END)
            .sameLength(true)
            .animate(true)
            .target(target)
            .show();
```
   
   ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/relative_end_samelength_animate_success.gif) 
   
   ### layout gravity
   
   ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/layout_gravity.png) 
   
   ### relative gravity
   
   ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/relative_gravity.png) 
   
   
   
 ## Advanced Features
 
 ### tag
tag is a advanced feature of BToast, toasts with same tag only can keep one in the waiting queue.if you do not set tag, tag is 0(default)

![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/tag0.gif) 

create a toast with tag

```Java
BToast.warning(v.getContext())
            .text(R.string.text_test_content)
            .tag(1)
            .show();
```
		
![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/tag1.gif) 

 ### not depend on Looper
 u can use BToast in a sub thread without run Looper.prepare() and Looper.loop()  

 for example:  
		
	
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                BToast.info(MainActivity.this).text("text").show();  
            }  
        }).start();
    


 ### clear toast when activity finished
 when activity finished， toasts(delivered by this activity) inside of queue will be removed(If there is still)
 


 