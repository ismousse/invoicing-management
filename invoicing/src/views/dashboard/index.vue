<template>
  <div class="dashboard-container">
    <div class="dashboard-text">{{ username }}
      <el-button type="info" style="width:48%;" @click="dialog = true">修改密码</el-button>
    </div>
    <!-- 弹框 -->
    <el-dialog
      :title="'修改密码'"
      :visible.sync="dialog"
      style="text-align:left !important"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" label-width="80px">

        <el-form-item prop="password" label="密码">
          <el-input
            :key="passwordType"
            ref="password"
            v-model="form.password"
            :type="passwordType"
            placeholder="请输入密码"
            name="password"
            auto-complete="on"
          />
          <!-- <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span> -->
        </el-form-item>

        <el-form-item label="确认密码" prop="surepasswd">
          <el-input v-model="form.surepasswd" placeholder="请再次输入密码" type="password" autocomplete="off" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog = false">取 消</el-button>
        <el-button type="success" @click="saveInfo(form)">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import user from '@/api/user'
export default {
  data() {
    return {
      dialog: false,
      form: {},
      passwordType: 'password',
      loginForm: {
        username: 'admin',
        password: '111111'
        // username: '',
        // password: ''
      },
      username: ''
    }
  },
  methods: {
    // 保存数据
    saveInfo(form) {
      user.updatePwd(this.form.password).then(response => {
        return this.$message({
          type: 'success',
          message: '修改成功!'
        })
      })
      this.dialog = false
      this.form = {}
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleClose(done) {
      done()
      this.form = {}
      this.input = ''
    }
  }
}
</script>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
