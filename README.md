# BToast

 [![Download](https://api.bintray.com/packages/bsss/maven/BToast/images/download.svg) ](https://bintray.com/bsss/maven/BToast/_latestVersion)
 
 an Toast Util For Android!
 
 ### Dependency
 
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

### 2
 
  ```Java
 dependencies {
    ...
    implementation 'com.bravin.btoast:BToast:1.0.0'
 }
 ```
 
### Show And Usage

#### base
  
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
  
  
#### more

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
   
   ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/waring.jpg) 
   
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
            .sameLength(true)
            .target(target)
            .show();
   ```
   
   ![](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/radius0.jpg) 
 
 
 To display an samelength success Toast with target(View)：  (samelength mean same width(layout_gravity_top layout_gravity_bottm) or same height(layout_gravity_left layout_gravity_right))
  ```Java
   BToast.success(v.getContext())
            .text(R.string.text_test_content)
			.samelength(true)
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


 