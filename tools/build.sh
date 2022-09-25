#!/bin/sh

ajar=/usr/lib/android-sdk/platforms/android-23/android.jar
kjar=out/classes/kt.jar
src_dir=src/com/example/firstkotlin
cls_dir=out/classes
main=MainAct.java
sub=Sub.java
alt=Alt.kt
apk=unsigned.apk
dex=classes.dex
manif=AndroidManifest.xml
#dx_opt=--no-optimize

PATH=/usr/lib/android-sdk/build-tools/debian:"$PATH"

#V=t
v(){ test "x$V" = xt ; }
v && v=-v
v && verbose=-verbose
v && vout=stdout
v || vout=null

#alias j="ecj -7 $verbose -cp $cls_dir:$ajar"
alias j="javac   $verbose -cp $cls_dir:$ajar:$kjar --release 8"
alias k="kotlinc $verbose -cp $cls_dir:$ajar"
alias pkg="aapt p $v -M $manif -S res -I $ajar"
alias add="aapt a $v"

findsort(){ find * -type f | sort ; true ; }

# --- main ---

test "x$1" = xfind && findsort && exit
test "x$1" = x-c && { j -d $cls_dir $src_dir; exit; }

rm -fr gen out
test "x$1" = xclean && exit
mkdir -p gen $cls_dir

v && set -x

pkg -J gen || exit
j -d $cls_dir gen/R.java
j -d $cls_dir $src_dir/$sub || exit
k -d $kjar -include-runtime -no-reflect $src_dir/$alt || exit
j -d $cls_dir $src_dir/$main || exit
dx --dex $dx_opt --output=out/$dex $cls_dir
pkg -F out/$apk

cd out
add -f $apk $dex >/dev/$vout
cd - >/dev/null

v && aapt l out/$apk
v && findsort
true
