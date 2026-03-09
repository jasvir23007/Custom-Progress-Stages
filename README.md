### Visual Preview

[![](https://user-images.githubusercontent.com/53420188/127960477-c3caea4d-7559-4d67-9047-82092b938419.jpg)](https://jitpack.io/#jasvir23007/Custom-Progress-Stages)

| :---: | :---: |
| ![](https://github.com/user-attachments/assets/26791beb-1bcd-42c6-8399-e283ab5eb2b4) | ![](https://github.com/user-attachments/assets/852b749f-94f8-4fd8-ab7e-167b950120ec) |

---
# Custom-Progress-SeekBar

A fully Customizable Semi Circle Arc Progress Bar.
You can customize the the width and color of both progress and progress place holder from Java and XML.
Also you can set the amount of progress bar with percentage from Java and XML.
Additionally you can set percentage with a smooth filling animation.


# Setting Percentage With Animation
Please note that if you set percentage with animation ".setPercentWithAnimation(10)" at the initial of your activity, set it with a delay to let the view to load and then animate.
# Width to Height Ratio
Also for a better circular look, the width to height ratio is recommended to be 2:2 for example if your width is 100dp set your height to 100dp.

## Installation

Add this repository to your Module build.gradle

```bash
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
And then add this dependency to your Project build.gradle

```bash
dependencies {
    implementation 'com.github.jasvir23007:Custom-Progress-Stages:2.0.0'
}
```

## Usage

```Java
//Java or kotlin
progressBar.setPercent(40);
progressBar.setPercentWithAnimation(10);
        
progressBar.setProgressBarColor(0xffff00ff);
progressBar.setProgressPlaceHolderColor(0xff00ffff);
        
progressBar.setProgressBarWidth(10);
progressBar.setProgressPlaceHolderWidth(10);



// to set stages

 val list = ArrayList<Int>()
        list.add(0)
        list.add(50)
        list.add(100)
        list.add(150)
        progressBar.setData(list)
        progressBar.setPercentWithAnimation(125)
```

```xml
//XML
<com.jasvir.seekbar.SemiCircleArcProgressBar
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:percent="30"
        app:progressBarColor="#ff00ff"
        app:progressPlaceHolderColor="#00ffff"
        app:progressBarWidth="10"
        app:progressPlaceHolderWidth="10" />
```

```kotlin
// Jetpack Compose

 // Step 1
   setContent {
            CircularProgressScreen()
        }

  // Step 2
    @Composable
    fun CircularProgressScreen() {
        // Material 3 Inspired Palette
        val PrimaryColor = Color(0xFFFBEAEB)
        val TrackColor = Color(0xFF2F3C7E)
        val stages = remember { arrayListOf(0,50,100,150) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                modifier = Modifier
                    .size(300.dp) // Maintain aspect ratio
                    .padding(16.dp),
                factory = { context ->
                    // 1. Initialize the view
                    SemiCircleArcProgressBar(context).apply {
                        setData(stages)
                        setProgressBarColor(PrimaryColor.toArgb())
                        setProgressPlaceHolderColor(TrackColor.toArgb())
                        setProgressBarWidth(10.dpToPx())
                        setProgressPlaceHolderWidth(14.dpToPx())
                    }
                },
                update = { view ->
                    // 2. Perform updates here (this runs on recomposition)
                    view.setPercentWithAnimation(125)
                }
            )
        }
    }

// Step 3
fun Int.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}

```


