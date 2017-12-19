# RxAndroidLibrary

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	    compile 'com.github.shantoofor:rxandroidlibrary:1.0'
	}

如何使用
在项目的Application的onCreate方法中进行注册

RxAndroid.init(this);
