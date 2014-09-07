#!/bin/bash          

FILES=$(find . -type f -name '*.jpg')
for file in $FILES
do
	# echo $file
  cwebp $file -q 75 -o $file.webp
done