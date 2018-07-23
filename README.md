# BToast

 [![Download](https://api.bintray.com/packages/bsss/maven/BToast/images/download.svg) ](https://bintray.com/bsss/maven/BToast/_latestVersion)
 
 an Toast Util For Android!
 
### Show And Usage
  
  To display an success Toast：
  
   ```Java
   BToast.success(v.getContext())
                        .text("this is text")
                        .show();
   ```
   
   ![success](https://github.com/bravinshi/ImJack/blob/master/BToast_screen_cup/success.jpg =200x400) 
  
  
 
###Dependency
 
####1
 
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

###2
 
  ```Java
 dependencies {
    ...
    implementation 'com.bravin.btoast:BToast:1.0.0'
 }
 ```
 
 ###Dependency
 