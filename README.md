#YUNNAN-PDMS-Web

本项目为PDMS系统的Web端项目。改项目包括三个模块
* ruoyi -> 以开源项目`RouYi`为基础的基础功能
* pdms -> 真正业务逻辑的SpringBoot后端
* pdms/ui -> 真正业务逻辑的Vue前端

## 开发环境要求

* Mysql
* Redis
* Spring Boot
* Node.js

### 依赖的内部库

需要将`dependency`目录下的库复制到maven的库下载路径中

## 运行

运行前必须先建好数据库表，并导入初始化数据

按顺序启动
* 运行ruoyi项目
* 运行pdms项目
* 运行前端服务 `cd pdms/ui && npm run dev`
    不要使用cnpm，可能会出现核心依赖缺失问题