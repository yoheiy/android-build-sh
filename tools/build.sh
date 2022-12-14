#!/bin/sh

jar=/usr/lib/android-sdk/platforms/android-23/android.jar
src=src/com/example/canvas/MainAct.java
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

#alias j="ecj $verbose -7 -cp out:$jar"
alias j="javac $verbose --release 7 -cp out:$jar"
alias package="aapt p $v -M $manif -S res -I $jar"
alias add="aapt a $v"

findsort(){ find -type f | sort ; true ; }

# --- main ---

test "x$1" = xfind && findsort && exit
test "x$1" = x-c && { j -d out $src; exit; }

rm -fr gen out
test "x$1" = xclean && exit
mkdir -p gen out

package -J gen || exit
j -d out gen/R.java
j -d out $src || exit
dx --dex --output=out/$dex out
package -F out/$apk

cd out
add -f $apk $dex >/dev/$vout
cd - >/dev/null

v && aapt l out/$apk
v && findsort
true
