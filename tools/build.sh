#!/bin/sh

ajar=/usr/lib/android-sdk/platforms/android-23/android.jar
kjar=out/kt.jar
src_dir=src/com/example/firstkotlin
main=MainAct.java
sub=Sub.java
alt=Alt.kt
apk=unsigned.apk
dex=classes.dex
manif=AndroidManifest.xml

PATH=/usr/lib/android-sdk/build-tools/debian:"$PATH"

#V=t
v(){ test "x$V" = xt ; }
v && v=-v
v && verbose=-verbose
v && vout=stdout
v || vout=null

#alias j="ecj -7 $verbose -cp out:$ajar"
alias j="javac   $verbose -cp out:$ajar:$kjar --release 8"
alias k="kotlinc $verbose -cp out:$ajar"
alias pkg="aapt p $v -M $manif -S res -I $ajar"
alias add="aapt a $v"

findsort(){ find -type f | sort ; true ; }

# --- main ---

test "x$1" = xfind && findsort && exit
test "x$1" = x-c && { j -d out $src; exit; }

rm -fr gen out
test "x$1" = xclean && exit
mkdir -p gen out

pkg -J gen || exit
j -d out gen/R.java
j -d out $src_dir/$sub || exit
k -d $kjar -include-runtime -no-reflect $src_dir/$alt || exit
j -d out $src_dir/$main || exit
dx --dex --output=out/$dex out
pkg -F out/$apk

cd out
add -f $apk $dex >/dev/$vout
cd - >/dev/null

v && aapt l out/$apk
v && findsort
true
