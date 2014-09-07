#!/bin/bash          

FILES=$(find . -type f -name '*.webp')
for file in $FILES
do
	rm -r *.webp
  # cwebp $FILE -q 75 -o $FILE.webp
done