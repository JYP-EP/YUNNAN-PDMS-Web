#!/bin/bash

#show help
function show_help_info {
 echo "options:"
 echo "    -y 是否打包为研发版本，研发版本将代码放在本地，映射docker中，y为研发版本或者n为纯docker版本，默认为非研发版本"
 echo "    -d 系统版本号"
 echo "    -e 不构建某个模块的程序，多个模块用,分隔"
 echo "    -h 打开帮助信息"
}


gitversion=1.0
version=heze
ifyanfa=n
while getopts :h:y:d:v:e: option
do
   if [ $option == h ]; then
      show_help_info
      exit 0
   fi
   case $option in
        y) export ifyanfa=$OPTARG;;
        d) export version=$OPTARG;;
        e) export modules=$OPTARG;;
        v) export gitversion=$OPTARG;;
        *) show_help_info ;;
   esac
done

echo $gitversion > version.txt
gitversion=1.0

gridnt_ui=gridnt_ui
gridnt_pdms=gridnt_pdms
gridnt_ruoyi=gridnt_ruoyi



echo "-------------------------------后台ruoyi开始---------------------"

dirpathruoyi="binsruoyi"
if [ -e $dirpathruoyi ]; then
    echo  $dirpathruoyi" exist.Delete old "$dirpathruoyi" and make new "$dirpathruoyi
    rm -rf $dirpathruoyi
fi
echo " Make new build dir: "$dirpathruoyi
mkdir -p $dirpathruoyi


echo "将文件复制到dirpathruoyi"
cp -r version.txt scripts/dist/admin
cp -r scripts/tbadmin.Dockfile  $dirpathruoyi/
cp -r scripts/dist/admin  $dirpathruoyi/
cp -r scripts/start_adminjar.sh $dirpathruoyi/admin/

echo "开始构建docker-java后台pdms"
cd $dirpathruoyi
#docker build -t ${gridnt_iot}:${version}${gitversion} -f tb.Dockfile .
starttime1=`date +'%Y-%m-%d %H:%M:%S'`
docker build --no-cache -t ${gridnt_ruoyi}:${version}${gitversion} -f tbadmin.Dockfile  . 

endtime1=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds1=$(date --date="$starttime1" +%s);
end_seconds1=$(date --date="$endtime1" +%s);
echo "本次运行时间： "$((end_seconds1-start_seconds1))"s"
echo "开始保存java后台"
starttime2=`date +'%Y-%m-%d %H:%M:%S'`
docker save -o ${gridnt_ruoyi}:${version}${gitversion} ${gridnt_ruoyi}:${version}${gitversion}

mv ${gridnt_ruoyi}:${version}${gitversion} ${gridnt_ruoyi}.${version}${gitversion}.tar

endtime2=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds2=$(date --date="$starttime2" +%s);
end_seconds2=$(date --date="$endtime2" +%s);
echo "本次运行时间： "$((end_seconds2-start_seconds2))"s"

#echo "删除docker"

 docker rmi ${gridnt_ruoyi}:${version}${gitversion}


echo "-------------------------------后台pdms开始---------------------"
cd ../

dirpathpdms="binspdms"
if [ -e $dirpathpdms ]; then
    echo  $dirpathpdms" exist.Delete old "$dirpathpdms" and make new "$dirpathpdms
    rm -rf $dirpathpdms
fi
echo " Make new build dir: "$dirpathpdms
mkdir -p $dirpathpdms


echo "将文件复制到dirpathpdms"
cp -r version.txt scripts/dist/pdms
cp -r scripts/pdms.Dockfile  $dirpathpdms/
cp -r scripts/dist/pdms  $dirpathpdms/
cp -r scripts/start_pdmsjar.sh $dirpathpdms/pdms/
echo "开始构建docker-java后台pdms"
cd $dirpathpdms
#docker build -t ${gridnt_iot}:${version}${gitversion} -f tb.Dockfile .
starttime1=`date +'%Y-%m-%d %H:%M:%S'`
docker build --no-cache -t ${gridnt_pdms}:${version}${gitversion} -f pdms.Dockfile  . 

endtime1=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds1=$(date --date="$starttime1" +%s);
end_seconds1=$(date --date="$endtime1" +%s);
echo "本次运行时间： "$((end_seconds1-start_seconds1))"s"
echo "开始保存java后台"
starttime2=`date +'%Y-%m-%d %H:%M:%S'`
docker save -o ${gridnt_pdms}:${version}${gitversion} ${gridnt_pdms}:${version}${gitversion}

mv ${gridnt_pdms}:${version}${gitversion} ${gridnt_pdms}.${version}${gitversion}.tar

endtime2=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds2=$(date --date="$starttime2" +%s);
end_seconds2=$(date --date="$endtime2" +%s);
echo "本次运行时间： "$((end_seconds2-start_seconds2))"s"

#echo "删除docker"

 docker rmi ${gridnt_pdms}:${version}${gitversion}



echo "-------------------------------后台结束，开始构建前台ui---------------------"

cd ../


dirpathui="binsui"
if [ -e $dirpathui ]; then
    echo  $dirpathui" exist.Delete old "$dirpathui" and make new "$dirpathui
    rm -rf $dirpathui
fi
echo " Make new build dir: "$dirpathui
mkdir -p $dirpathui

echo "开始创建文件夹"


echo "将文件复制到dirpathui"
cp -r version.txt scripts/dist/www
cp -r scripts/nginx.conf $dirpathui/
cp -r scripts/ngui.Dockfile  $dirpathui/
cp -r scripts/nginxserver.conf  $dirpathui/
cp -r scripts/dist/www  $dirpathui/



echo  "开始处理前台nginx和ui的docker"
echo "开始构建docker-ui前台"
cd $dirpathui
starttime3=`date +'%Y-%m-%d %H:%M:%S'`
docker build --no-cache -t ${gridnt_ui}:${version}${gitversion} -f ngui.Dockfile . 

endtime3=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds3=$(date --date="$starttime3" +%s);
end_seconds3=$(date --date="$endtime3" +%s);
echo "本次运行时间： "$((end_seconds3-start_seconds3))"s"
echo "开始保存前台docker"
starttime4=`date +'%Y-%m-%d %H:%M:%S'`
docker save -o ${gridnt_ui}:${version}${gitversion} ${gridnt_ui}:${version}${gitversion}
endtime4=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds4=$(date --date="$starttime4" +%s);
end_seconds4=$(date --date="$endtime4" +%s);
echo "本次运行时间： "$((end_seconds4-start_seconds4))"s"
mv ${gridnt_ui}:${version}${gitversion} ${gridnt_ui}.${version}${gitversion}.tar
echo "删除前台docker"

docker rmi ${gridnt_ui}:${version}${gitversion}


echo "-------------------------------开始将docker拷贝到disttar---------------------"


cd ..


if [ -d "disttar" ];then
   rm -rf disttar
fi
mkdir disttar
mkdir -p disttar/mysql_dbinit
mkdir -p disttar/docker-compose 



    echo  "打包为全docker版本，部署时请将docker和配置文件拷贝到目标机的/root/heze目录下"
    mv  $dirpathpdms/${gridnt_pdms}.${version}${gitversion}.tar   disttar/
    mv  $dirpathruoyi/${gridnt_ruoyi}.${version}${gitversion}.tar   disttar/
    mv  $dirpathui/${gridnt_ui}.${version}${gitversion}.tar   disttar/
    cp init.sql disttar/mysql_dbinit
    cp ruoyi/sql/* disttar/mysql_dbinit
    cp scripts/my.cnf disttar/mysql_dbinit
    cp scripts/docker-compose.yml disttar/docker-compose
    cp scripts/docker-composeminio.yml disttar/docker-compose
    cp scripts/docker-composemysqlredis.yml disttar/docker-compose
    cp scripts/nginx.conf disttar/mysql_dbinit
    cp scripts/nginxserver.conf disttar/mysql_dbinit
    cp -r $dirpathui/www disttar/mysql_dbinit/www


    



























