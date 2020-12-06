<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.goodid" placeholder="商品id" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.goodname" placeholder="商品名" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="info" icon="el-icon-refresh-right" @click="resetData()">清空</el-button>
      <el-button type="danger" icon="el-icon-s-shop" @click="count=true;fetchData()">缺少</el-button>
    </el-form>

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
      <el-table-column prop="stockid" label="库存id" width="300" align="center" />
      <el-table-column prop="goodid" label="商品id" width="300" align="center" />
      <el-table-column prop="goodname" label="商品名" width="250" align="center" />
      <el-table-column prop="count" label="库存数量" width="200" align="center" />
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
import stock from '@/api/stock'
export default {
  data() { // 定义数据
    return {
      count: false,
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
      if (this.count === true) {
        this.searchObj.count = 0
      }
      stock.list(this.page, this.limit, this.searchObj).then(response => {
        if (response.success === true) {
          this.list = response.data.rows
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    resetData() {
      this.count = false
      this.searchObj = {}
      this.fetchData()
    },
    handleClose(done) {
      done()
      this.form = {}
      this.input = ''
    }
  }
}
</script>
