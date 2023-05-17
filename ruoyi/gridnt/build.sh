#!/bin/bash
cd ..
# 新建一个目录
rm -rf dist
mkdir dist
echo 'after mkdir'
dir dist
# 执行一个新的打包命令
mvn clean package -DskipTests

if (( $? ))
then
        echo "mvn cdh failed"
        exit 1
else
        echo "mvn cdh success"
fi

# 打包前端的程序
#cd  ui
#echo "构建前端程序"
#yarn install
#yarn run build:prod
#echo "前端程序构建完成"
#cd ..

# 拷贝所有需要的程序到target下
cp -r ruoyi-admin/target/ruoyi-admin-package/ruoyi-admin dist/admin
#cp -r ui/dist dist/www
cp -r dist scripts/
