#!/usr/bin/env bash
trap stopDocker EXIT INT TERM
stopDocker(){
    docker rm -f  ${JOB_NAME}  1>/dev/null 2>&1
}
exit_log(){
   echo $1 1>&2
   exit 1
}
#show help
function show_help_info {
 echo "options:"
 echo "    -v git的版本号后7位，不输入则为gitnull"
 echo "    -d 系统版本号" 
 echo "    -h 打开帮助信息"
 echo "    -k 打包那个项目"
}

stopDocker
set -ex
image=192.168.8.200:5000/gridnt-iot:build

kehu=l
which docker || exit_log "not find docker"
#get option


while getopts :h:v:d:g:k: option
do
   if [ $option == h ]; then
      show_help_info
      exit 0
   fi
   case $option in
        v) export gitversion=$OPTARG;;
        d) export version=$OPTARG;;
        g) export gradlehome=$OPTARG;;
        k) export kehu=$OPTARG;; 		
        *) show_help_info ;;
   esac
done








starttime=`date +'%Y-%m-%d %H:%M:%S'`



docker run -t  -e WORKSPACE=/home -e GITNUM=$gitversion -e VERSION=$version   -v ${WORKSPACE}:/home   --privileged=true --rm --name ${JOB_NAME} ${image}


endtime=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds=$(date --date="$starttime" +%s);
end_seconds=$(date --date="$endtime" +%s);
echo "本次编译时间： "$((end_seconds-start_seconds))"s"
set +ex
