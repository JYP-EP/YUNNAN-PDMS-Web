<template>
  <!-- 添加或修改部门信息对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="部门名称" prop="bmmc">
        <el-input v-model="form.bmmc" placeholder="请输入部门名称"/>
      </el-form-item>
      <el-form-item label="部门编号" prop="bmbh">
        <el-input v-model="form.bmbh" placeholder="请输入部门编号"/>
      </el-form-item>
      <el-form-item label="上级部门" prop="sjbm">
        <treeselect v-model="form.sjbm" :options="bmOptions" :normalizer="normalizer" placeholder="请选择上级部门"/>
      </el-form-item>
      <el-form-item label="部门负责人" prop="bmfzr">
        <el-input v-model="form.bmfzr" placeholder="请输入部门负责人"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {listBm, getBm, delBm, addBm, updateBm, exportBm} from "@/api/gridnt/bm";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  props: {
    editorconfig: {
      type: Object
    }
  },
  name: "BmEditor",
  components: {
    Treeselect
  },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 部门信息表格数据
      bmList: [],
      // 部门信息树选项
      bmOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bmmc: [
          {required: true, message: "部门名称不能为空", trigger: "blur"}
        ],
        bmbh: [
          {required: true, message: "部门编号不能为空", trigger: "blur"}
        ],
      }
    };
  },
  methods: {
    /** 转换部门信息数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.bmid,
        label: node.bmmc,
        children: node.children
      };
    },
    /** 查询部门信息下拉树结构 */
    getTreeselect() {
      listBm().then(response => {
        this.bmOptions = [];
        const data = {bmid: 0, bmmc: '顶级节点', children: []};
        data.children = this.handleTree(response.data, "bmid", "sjbm");
        this.bmOptions.push(data);
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        bmmc: null,
        bmbh: null,
        sjbm: null,
        bmfzr: null,
        bmid: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.bmid) {
        this.form.sjbm = row.bmid;
      } else {
        this.form.sjbm = 0;
      }
      this.open = true;
      this.title = "添加部门信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.sjbm = row.bmid;
      }
      getBm(row.bmid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改部门信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bmid != null) {
            updateBm(this.form).then(response => {
              this.msgSuccess("修改成功", 3000);
              this.open = false;
              this.$emit("reloadList")
            });
          } else {
            addBm(this.form).then(response => {
              this.msgSuccess("新增成功", 3000);
              this.open = false;
              this.$emit("reloadList")
            });
          }
        }
      });
    }
  }
};
</script>
