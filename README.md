# Android command line build example

To build:

    make

Output APK file is `out/signed.apk`.

It automatically create key store if not given.

It depends on some Debian Packages:
  - aapt: /usr/lib/android-sdk/build-tools/debian/aapt
  - apksigner: /usr/lib/android-sdk/build-tools/debian/apksigner
  - dalvik-exchange: /usr/lib/android-sdk/build-tools/debian/dx
  - ecj: /usr/bin/ecj, OR
  - openjdk-11-jdk-headless: /usr/lib/jvm/java-11-openjdk-amd64/bin/javac
  - openjdk-11-jre-headless: /usr/lib/jvm/java-11-openjdk-amd64/bin/keytool

It depends on kotlinc from kotlinlang.org.
