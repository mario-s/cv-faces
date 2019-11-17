Computer Vision
===============

A playground for the Opencv library with Java bindings. This project includes samples for:
 - face detection 
 - gender prediction
 - exposure fusion
 
Requirements
------------
It requires Oracle's Java SDK for the UI.

Training Images
---
For the training images of the face recognition feature refer to the 
[OpenCV topic on face recognition](https://docs.opencv.org/2.4/modules/contrib/doc/facerec/facerec_tutorial.html#face-database).

The included images in this project are taken from the Yale Facedatabase A.

Starting
---
With gradle execute the command:
```
./gradlew run
``` 
Or
```
./gradlew run --args='c'
``` 