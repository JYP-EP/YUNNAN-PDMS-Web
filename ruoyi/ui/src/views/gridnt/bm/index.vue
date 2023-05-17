<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部门名称" prop="bmmc">
        <el-input
          v-model="queryParams.bmmc"
          placeholder="请输入部门名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门编号" prop="bmbh">
        <el-input
          v-model="queryParams.bmbh"
          placeholder="请输入部门编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上级部门" prop="sjbm">
        <el-input
          v-model="queryParams.sjbm"
          placeholder="请输入上级部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门负责人" prop="bmfzr">
        <el-input
          v-model="queryParams.bmfzr"
          placeholder="请输入部门负责人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
	    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gridnt:bm:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="bmList"
      row-key="bmid"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="部门名称" align="center" prop="bmmc" />
      <el-table-column label="部门编号" prop="bmbh" />
      <el-table-column label="上级部门" align="center" prop="sjbm" />
      <el-table-column label="部门负责人" align="center" prop="bmfzr" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gridnt:bm:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['gridnt:bm:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gridnt:bm:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
      <BmEditor :editorconfig="editorconfig" ref="BmEditor" @reloadList="getList" />
  </div>
</template>

<script>
import { listBm, delBm, exportBm } from "@/api/gridnt/bm";
import BmEditor from "@/views/gridnt/bm/editTree"
export default {
  name: "Bm",
  components: {
      "BmEditor":BmEditor
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 部门信息表格数据
      bmList: [],
      // 部门信息树选项
      bmOptions: [],
        editorconfig:{},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        bmmc: null,
        bmbh: null,
        sjbm: null,
        bmfzr: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bmmc: [
          { required: true, message: "部门名称不能为空", trigger: "blur" }
        ],
        bmbh: [
          { required: true, message: "部门编号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询部门信息列表 */
    getList() {
      this.loading = true;
      listBm(this.queryParams).then(response => {
        this.bmList = this.handleTree(response.data, "bmid", "sjbm");
        this.loading = false;
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
        this.$refs.BmEditor.handleAdd(row);

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
        this.$refs.BmEditor.handleUpdate(row);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除当前选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBm(row.bmid);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功", 3000);
        })
    }
  }
};
</script>
