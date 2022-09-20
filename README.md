# Computer Vision


A playground for the Opencv library with Java bindings. This project includes samples for:
 - face detection 
 - gender prediction
 - exposure fusion
 
## Requirements

It requires Oracle's Java SDK for the UI.

## Training Images

For the training images of the face recognition feature refer to the 
[OpenCV topic on face recognition](https://docs.opencv.org/2.4/modules/contrib/doc/facerec/facerec_tutorial.html#face-database).

The included images in this project are taken from the Yale Facedatabase A.

## Starting
### Face Detection
To start the Swing UI with face detection using a DNN:
```
./gradlew run --args='-u=s -d=dnn'
```
Since DNN is default, the detector type can be skipped:
```
./gradlew run --args='-u=s'
```

To use the GUI from OpenCV use `-u=c` as an argument.

### Gender Prediction
To see the gender prediction you have to use the older [cascade classifier](https://docs.opencv.org/3.4/db/d28/tutorial_cascade_classifier.html). The DNN does not support it yet. 
```
./gradlew run --args='-u=s -d=haar'
```
### Exposure Fusion
The project can also be used to create a [HDR image out of a series of photos created with different exposures](https://docs.opencv.org/3.4/d3/db7/tutorial_hdr_imaging.html).
```
./gradlew run --args='-s=<SOURCE_PATH>'
```
This will take all the images in the source path and merge them into a new one. The path has to be absolute. Sample: 
```
./gradlew run --args='-s=/Users/joe/Pictures/flower'
```

### Getting help
To see all possible arguments
```
./gradlew run --args='-h'
``` 