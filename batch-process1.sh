#! /bin/sh 
for f in "$1"/*; do
  if [ -f "$f" ]; then
    SIZE="$(du -sh "${f}" | cut -f1)"
    WORD="$(wc -w "${f}" | cut -d' ' -f1)"
    echo "Processing $f file..."
    echo "$SIZE"
    echo "$WORD"
  fi
done

