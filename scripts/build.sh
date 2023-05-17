#!/bin/bash
#cd ..
# 新建一个目录
rm -rf dist
mkdir dist
echo 'after mkdir'
dir dist

cd ruoyi
#打包ruoyi
mvn clean package -DskipTests
if (( $? ))
then
        echo "ruoyi mvn cdh failed"
        exit 1
else
        echo "ruoyi mvn cdh success"
fi


#打包pdms
cd ../pdms
mvn clean package -DskipTests
#mkdir -p dist/codec
if (( $? ))
then
        echo "mvn cdh failed"
        exit 1
else
        echo "mvn cdh success"
fi




# 打包前端的程序
cd  ui
echo "构建前端程序"
npm install
npm run build:prod
echo "前端程序构建完成"
cd ../../

# 拷贝所有需要的程序到target下
cp -r pdms/pdms-admin/target/pdms-admin-package/pdms-admin dist/pdms
cp -r ruoyi/ruoyi-admin/target/ruoyi-admin-package/ruoyi-admin dist/admin
cp -r pdms/ui/dist dist/www
cp -r dist scripts/

