envtype=$1
if [ "$envtype" == "daily" ] || [ "$envtype" == "preview" ] || [ "$envtype" == "release" ]
	then
		echo "当前编译tag:$envtype"
else
  	echo "请输入需要编译的tag: daily, preview, release"
  	exit -1
fi

git pull

apkPath=""

./gradlew clean

if [ "$envtype" == "daily" ] ; then
	./gradlew assembleDebug
	filePath="app/build/outputs/apk/app-debug.apk"
elif  [ "$envtype" == "preview" ]; then
	./gradlew assembleDebug
	filePath="app/build/outputs/apk/app-debug.apk"

elif [ "$envtype" == "release" ]; then
	./gradlew assembleRelease
	filePath="app/build/outputs/apk/app-release.apk"
fi

if [ "$envtype" == "daily" ] || [ "$envtype" == "preview" ] || [ "$envtype" == "release" ]
	then
		echo "==================Build完成==================="
		echo ${filePath}
fi
