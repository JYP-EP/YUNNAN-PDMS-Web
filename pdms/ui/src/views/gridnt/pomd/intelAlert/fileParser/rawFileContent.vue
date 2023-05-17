<template>
  <div>
    <el-button
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="title" :visible.sync="fileDialogVisible" style="height: 100%">
      <div style="text-align: left; word-wrap: break-word; height: 100%">
        <button @click="download(alertItem)" style="float: right; margin-bottom: 10px; margin-right: 10px;">
          <i class="el-icon-download"></i>
        </button>
        <textarea rows="50" style="width: 100%; height: 100%; border: none; resize: none">
          {{fileContent}}
        </textarea>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getFileRawContent} from "@/api/gridnt/intelAlert";

export default {
  name: "rawFileContent",
  props: ['alertItem'],
  data(){
    return{
      fileDialogVisible: false,
      filename: '',
      fileContent: null
    }
  },
  computed: {
    title(){
      return this.filename;
    }
  },
  methods:{
    statusParser(row){
      return row.status === 1 ? "预览" : "";
    },
    showDetail(row){
      if(row.status !== 1){
        return;
      }

      getFileRawContent({fileId: row.fileId}).then(resp=>{
        this.filename = resp.data.filename;
        this.fileContent = resp.data.context;

        this.fileDialogVisible = true;
      })
    },
    download(row) {
      let blob = new Blob([this.fileContent], {type: 'application/xml'})
      let link = document.createElement('a')
      link.href = window.URL.createObjectURL(blob)
      link.download = this.filename.length > 0 ? this.filename : this.alertItem.fileId + '.xml';
      link.click();
    }
  }
}
</script>

<style scoped>

</style>
