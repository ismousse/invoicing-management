<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.goodid" placeholder="商品id" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="商品名称" />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="开始时间"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="结束时间"
        />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.exportname" placeholder="操作人员" />
      </el-form-item>
      <br>
      <center>
        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="info" icon="el-icon-refresh-right" @click="resetData()">清空</el-button>
        <el-button type="warning" icon="el-icon-plus" @click="addData()">出货</el-button>
      </center>
    </el-form>
    <br>

    <!-- 弹框 -->
    <el-dialog
      :title="'出货'"
      :visible.sync="dialog"
      style="text-align:left !important"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item v-if="false" prop="goodid">
          <el-input v-if="false" v-model="form.goodid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="商品列表" prop="goodname">
          <el-select
            v-model="form.goodname"
            filterable
            default-first-option
            placeholder="请选择商品"
            @change="changeOption"
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="{id: item.id, name: item.name}"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出货数量" prop="count">
          <el-input v-model.number="form.count" autocomplete="off" />
        </el-form-item>
        <el-form-item label="库存" prop="ku">
          <!-- <el-input id="returncount" v-model="form.count" readonly autocomplete="off" /> -->
          <el-input v-if="false" readonly autocomplete="off" />{{ ku }}
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
        width="80"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column v-if="false" prop="exportid" label="出货id" width="250" align="center" />
      <el-table-column prop="goodid" label="商品id" width="300" align="center" />
      <el-table-column prop="goodname" label="商品名称" width="200" align="center" />
      <el-table-column prop="count" label="进货数量" width="200" align="center" />
      <el-table-column prop="created" label="进货时间" width="280" align="center" />
      <el-table-column prop="username" label="操作人员" width="225" align="center" />
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
import exportt from '@/api/export'
export default {
  data() { // 定义数据
    return {
      dialog: false,
      form: {},
      // Select选择器
      options: [],
      value: {},
      ku: 0,
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
      this.page = page
      this.listLoading = true
      exportt.list(this.page, this.limit, this.searchObj).then(response => {
        if (response.success === true) {
          this.list = response.data.rows
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    // 保存数据
    saveInfo(form) {
      if (this.add === true) {
        if (this.form.count && this.form.goodid) {
          exportt.save(this.form).then(response => {
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
      }
    },
    addData() {
      exportt.getGood().then(response => {
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
      const goodid = params.id
      const goodname = params.name
      this.form.goodid = goodid
      this.form.goodname = goodname
      exportt.getCountByGoodId(goodid).then(response => {
        this.ku = response.data.count
        // this.form.ku = response.data.count
      })
      console.log('label=' + goodname)
      console.log('value=' + goodid)
    }
  }
}
</script>
