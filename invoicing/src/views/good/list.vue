<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.id" placeholder="商品id" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="商品名称" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="info" icon="el-icon-refresh-right" @click="resetData()">清空</el-button>
      <el-button type="warning" icon="el-icon-plus" @click="addData()">添加</el-button>
    </el-form>

    <!-- 弹框 -->
    <el-dialog
      :title="add?'新增商品':'修改商品'"
      :visible.sync="dialog"
      style="text-align:left !important"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item v-if="flase" prop="unitid">
          <el-input v-if="flase" v-model="form.unitid" autocomplete="off" />
        </el-form-item>
        <el-form-item v-if="flase" prop="goodid">
          <el-input v-if="flase" v-model="form.goodid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="商品单位" prop="unitname">
          <el-select
            v-model="form.unitname"
            filterable
            allow-create
            default-first-option
            placeholder="请选择单位"
            @change="changeOption"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="{value: item.value, label: item.label}"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="goodname">
          <el-input v-model="form.goodname" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog = false">取 消</el-button>
        <el-button type="success" @click="saveInfo(form)">保存</el-button>
      </div>
    </el-dialog>

    <!-- 列表-表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column
        label="序号"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="goodid" label="商品id" width="350" align="center" class="hidden-row" />
      <el-table-column v-if="flase" prop="unitid" label="单位id" width="350" align="center" />
      <el-table-column prop="goodname" label="商品名" width="200" align="center" />
      <el-table-column prop="unitname" label="单位名" width="200" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="editRow(scope.row.goodid, scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.goodid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />

  </div>
</template>
<script>
import good from '@/api/good'
export default {
  data() { // 定义数据
    return {
      dialog: false,
      add: true,
      form: {},
      // Select选择器
      options: [],
      value: {},
      listLoading: true, // 是否显示loading信息
      list: null, // 数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 5, // 每页记录数
      searchObj: {}// 查询条件
    }
  },

  created() { // 当页面加载时获取数据
    this.fetchData()
  },

  methods: {
    fetchData(page = 1) { // 调用api层获取数据库中的数据
      console.log('加载列表')
      // 当点击分页组件的切换按钮的时候，会传输一个当前页码的参数page
      // 解决分页无效问题
      this.page = page
      this.listLoading = true
      good.list(this.page, this.limit, this.searchObj).then(response => {
        // debugger 设置断点调试
        if (response.success === true) {
          this.list = response.data.goods// response.data.rows
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    removeDataById(id) {
      // debugger
      this.$confirm('是否确认删除该记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return good.removeById(id)
      }).then(() => { // 如果上一个then成功则执行此处的then回调
        this.fetchData()
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch((response) => { // 失败
        if (response === 'cancel') {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        }
      })
    },
    // 保存数据
    saveInfo(form) {
      if (this.add === true) {
        if (this.form.goodname && this.form.unitname) { // this.form.unitid
          good.save(this.form).then(response => {
            this.fetchData()
            return this.$message({
              type: 'success',
              message: '保存成功!'
            })
          })
          // this.dialog = false //不要关，取消的时候才关
          this.form = {}
        } else {
          alert('不能为空')
        }
      } else {
        good.updateById(this.form).then(response => {
          this.fetchData()
          return this.$message({
            type: 'success',
            message: '修改成功!'
          })
        })
        this.dialog = false
        this.form = {}
      }
    },
    // 修改
    editRow(id, row) {
      // option赋值
      good.getUnit().then(response => {
        this.key = row.unitid
        this.value = row.unitname
        this.options = response.data.options
      })
      this.dialog = true
      this.add = false
      this._index = id
      // row.unitname = this.form.unitname
      this.form = Object.assign({}, row)
    },
    addData() {
      good.getUnit().then(response => {
        this.options = response.data.options
      })
      this.add = true
      this.dialog = true
    },
    handleClose(done) {
      done()
      this.form = {}
      this.input = ''
    },
    changeOption(params) {
      this.value = params
      // const { value, label } = params
      const value = params.value
      let label = params.label
      if (label == null) {
        label = this.value
        this.form.unitname = this.value
      }
      this.form.unitid = value
      this.form.unitname = label
      // console.log(this.value)
      // console.log('label=' + label)
      // console.log('value=' + value)
    }
  }
}
</script>
<style lang="scss">
.hidden-row{
  display: none
}
</style>
