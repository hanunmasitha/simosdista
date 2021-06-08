# Simosdista - Bangkit 2021 - B21-CAP0277
Simosdista is an android based reporting system that can warn social distancing violations in certain areas. In this case, the user can make a report of violation and it will be sent to the system as a notification to the authorities in order to make a decision. After we know that there is a social distancing violation in a certain area, it will be followed up by the authorities.

## Built with
* Min SDK : 26
* [Android Studio](https://developer.android.com/studio "Android Studio")
* [Kotlin](https://developer.android.com/kotlin "Kotlin")
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started "Navigation Component")
* [Koin](https://insert-koin.io/ "Koin")
* [TensorFlow Lite](https://www.tensorflow.org/lite "tflite")
* [Firebase](https://firebase.google.com/ "firebase")

## Features
* User
  * Create social distancing violation report
  * Crowd detection
  * Recent crowd locations
  * Indonesia's Covid Data
* Admin / Officer
  * Create feedback report

## Machine Learning
This system also implements machine learning algorithm for crowd detection. We use Tensorflow 2.4.0 to implement deep learning with transfer learning method. We choose MobileNet V2 as pre-trained model and add some fully connected layers into it. We use dataset from https://www.crowdhuman.org/ and prepare 2000 images for dataset with 1000 crowd images and 1000 not crowd images.

## Screenshot
![Webp net-resizeimage](https://user-images.githubusercontent.com/56395797/120925016-bbd29680-c700-11eb-8a16-c885964aa396.jpg) ![Webp net-resizeimage (1)](https://user-images.githubusercontent.com/56395797/120925054-e58bbd80-c700-11eb-89d3-dc4122f09651.jpg) ![Webp net-resizeimage (3)](https://user-images.githubusercontent.com/56395797/120925307-36e87c80-c702-11eb-8e44-0b995a7d9e27.jpg) ![Webp net-resizeimage (2)](https://user-images.githubusercontent.com/56395797/120925198-b590ea00-c701-11eb-8a98-90125f5217fb.jpg) ![Webp net-resizeimage (4)](https://user-images.githubusercontent.com/56395797/120925308-37811300-c702-11eb-9a07-cedf1dd15f3d.jpg) ![Webp net-resizeimage (5)](https://user-images.githubusercontent.com/56395797/120925414-ba09d280-c702-11eb-9c14-d03cd7a02c1a.jpg) ![Webp net-resizeimage (6)](https://user-images.githubusercontent.com/56395797/120925442-defe4580-c702-11eb-9528-7e6bc389e748.jpg) ![ebp net-resizeimage (8)](https://user-images.githubusercontent.com/56395797/120925492-10771100-c703-11eb-8625-d6e2cc5d966a.jpg) ![Webp net-resizeimage (9)](https://user-images.githubusercontent.com/56395797/120925614-88ddd200-c703-11eb-90ab-9f1d001195cb.jpg) ![Webp net-resizeimage (10)](https://user-images.githubusercontent.com/56395797/120925635-a6ab3700-c703-11eb-9e15-3c7f7069c5b8.jpg) ![Webp net-resizeimage (11)](https://user-images.githubusercontent.com/56395797/120925686-dfe3a700-c703-11eb-9a14-ac8c1dd04f56.jpg) ![Webp net-resizeimage (12)](https://user-images.githubusercontent.com/56395797/120925758-318c3180-c704-11eb-979d-3dd8d7ebaba2.jpg)









