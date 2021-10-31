# Assignment 7 User Guide

# Script/Text Syntax

### To create a layer:

create layer "image filename"

### To remove a layer: (indexed from 1)

remove "index"

### To apply an image process/filter:

filter "process name" "index"

Process names include "blur" "sharpen" "sepia" "greyscale", also indexed from 1

### To toggle visibility:

visibility "status" "index"

Status is either true or false, also indexed from 1

### To save the topmost visible image:

save "filename"

Filename is either "default" (saves to the original filename) or a new filename including the suffix ".png" ".ppm" or ".jpg"

## Example use: 

create layer bi.png
create layer Koala2.ppm
filter blur 2
filter sepia 1
save koala.png
visibility false 2
save bi.jpg

This creates 2 layers, one with a png image and another on top of it with a ppm image. It then blurs the top layer and sepias the bottom layer. It saves the top layer to koala.png, makes that layer invisible, and then saves the bottom layer to bi.jpg.

## Conditions:

The image filenames used in create must exist as images in the same directory as the jar file. 

Indices used must be valid and not out of bounds. Filter, visibility, and save cannot be called when there are no layers present. Remove can only be called when there is a layer to remove. 

# GUI Use

To load a script, hit the open script button up top, find a script, and then hit the load button to load the script. 

The same process works for loading images, open an image and then load it. 

The topmost visible layer will appear in the image box, and layers can be checked on/off (visible/invisible) with the checkboxes to the right of the display. 

Possible actions appear below the displayed image, click the buttons and the process will be applied to the visible image.

At the bottom of the panel, the image can be saved to a default name or to a specified filename. This filename must contain a proper suffix (".ppm", ".png", ".jpg").