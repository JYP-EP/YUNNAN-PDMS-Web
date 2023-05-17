#!/bin/bash

#show help
function show_help_info {
 echo "options:"
 echo "    -y 是否打包为研发版本，研发版本将代码放在本地，映射docker中，y为研发版本或者n为纯docker版本，默认为非研发版本"
 echo "    -d git提交版本号短"
 echo "    -e 不构建某个模块的程序，多个模块用,分隔"
 echo "    -v 系统版本号"
 echo "    -h 打开帮助信息"

}

docker_REPO="192.168.8.200:8082"
gitversion=1.0
version=master
ifyanfa=n
while getopts :h:y:d:v:e: option
do
   if [ $option == h ]; then
      show_help_info
      exit 0
   fi
   case $option in
        y) export ifyanfa=$OPTARG;;
        v) export version=$OPTARG;;
        e) export modules=$OPTARG;;
        d) export gitversion=$OPTARG;;
        *) show_help_info ;;
   esac
done

echo $gitversion > version.txt
gitversion=1.0

gridnt_admin=sasac/admin

dirpathadmin="binsadmin"
if [ -e $dirpathadmin ]; then
    echo  $dirpathadmin" exist.Delete old "$dirpathadmin" and make new "$dirpathadmin
    rm -rf $dirpathadmin
fi
echo " Make new build dir: "$dirpathadmin
mkdir -p $dirpathadmin

echo "开始创建文件夹"


echo "将文件复制到dirpathadmin"
cp -r version.txt scripts/dist/admin
cp -r scripts/ruoyi.Dockfile  $dirpathadmin/
cp -r scripts/dist/admin $dirpathadmin/

cd $dirpathadmin
#docker build -t ${gridnt_admin}:${version}${gitversion} -f scripts/tb.Dockfile .
starttime1=`date +'%Y-%m-%d %H:%M:%S'`
#docker build --no-cache -t ${gridnt_admin}:${version}${gitversion} -f ruoyi.Dockfile . 
docker build --no-cache -t ${docker_REPO}/${gridnt_admin}:${version} -f ruoyi.Dockfile .

endtime1=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds1=$(date --date="$starttime1" +%s);
end_seconds1=$(date --date="$endtime1" +%s);
echo "本次运行时间： "$((end_seconds1-start_seconds1))"s"

#echo "开始保存java admin后台"
#starttime2=`date +'%Y-%m-%d %H:%M:%S'`
#docker save -o ${docker_REPO}/${gridnt_admin}:${version} ${gridnt_admin}:${version}${gitversion}

#mv ${gridnt_admin}:${version}${gitversion} ${gridnt_admin}.${version}${gitversion}.tar
#endtime2=`date +'%Y-%m-%d %H:%M:%S'`
#start_seconds2=$(date --date="$starttime2" +%s);
#end_seconds2=$(date --date="$endtime2" +%s);
#echo "本次运行时间： "$((end_seconds2-start_seconds2))"s"

#echo "删除docker"

#docker rmi ${gridnt_admin}:${version}${gitversion}


#echo "-------------------------------开始将docker拷贝到disttar---------------------"


#cd ..


#if [ -d "disttar" ];then
#   rm -rf disttar
#fi
#mkdir disttar

#    echo  "打包为全docker版本，部署时请将docker和配置文件拷贝到目标机的/data目录下"

 #   mv  $dirpathadmin/${gridnt_admin}.${version}${gitversion}.tar   disttar/
 
