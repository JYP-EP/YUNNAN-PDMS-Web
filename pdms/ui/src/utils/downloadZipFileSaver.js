import axios from "axios"
import JSZip from "jszip"
import FileSaver from "file-saver"
export const downloadZip = {
  handleBatchDownload(data,Zipname) {
    const zip = new JSZip()
    const cache = {}
    const promises = []
    let _this = this
    data.forEach((item) => {
      const promise = _this.getFile2(item.url).then((data) => {
        // 下载文件, 并存成ArrayBuffer对象
        zip.file(item.file_name, data, {
          binary: true,
        }) // 逐个添加文件
        cache[item.file_name] = data
      })
      promises.push(promise)
    })
    Promise.all(promises).then(() => {
      zip
        .generateAsync({
          type: "blob",
        })
        .then((content) => {
          // 生成二进制流
          FileSaver.saveAs(content, Zipname) // 利用file-saver保存文件
        })
    })
  },
  getFile2(url) {
    return new Promise((resolve, reject) => {
      axios({
        method: "get", //使用get请求
        url, //放入的文件地址
        responseType: "arraybuffer", //请求的数据buffer对象
      })
        .then((data) => {
          resolve(data.data)
        })
        .catch((error) => {
          reject(error.toString())
        })
    })
  },
  GetNowDate(type,sign){
    let date = new Date();
    let sign2 = ":";
    if(sign != "")
    { sign2 = sign; }
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();

    if(month>=1&&month<=9)
    {
      month = "0" + month;
    }
    if(day>=1&&day<=9)
    {
      day = "0" + day;
    }
    if(hour>=1&&hour<=9)
    {
      hour = "0" + hour;
    }
    if(minutes>=1&&minutes<=9)
    {
      minutes = "0" + minutes;
    }
    if(seconds>=1&&seconds<=9)
    {
      seconds = "0" + seconds;
    }
    switch (type){
      case "y":
        return year;
        break;
      case "date":
        return year + "-" + month + "-" + day;
        break;
      case "datetime":
        return year + "-" + month + "-" + day + " " + hour + sign2 + minutes + sign2 + seconds;
        break;
    }
  },
}
