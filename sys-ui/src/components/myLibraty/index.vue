<template>
  <div>
    <el-button type="primary" plain @click="logout($event)">退出系统</el-button>
    <el-button
        type="primary"
        @click="handleAdd()">新增书籍
    </el-button>
    <el-table
        ref="multipleTable"
        :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        tooltip-effect="dark"
        style=" 100%"
        @selection-change="handleSizeChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="id"
          label="编号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="name"
          label="书名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="status"
          label="status"
          width="120">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">修改
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center;margin-top: 30px;">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          @current-change="current_change">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "dataList",
  data() {
    return {
      tableData: [],
      multipleSelection: [],
      total: 0,
      pagesize: 10,
      currentPage: 1
    }
  },
  mounted: function () {
    this.getBookList();
  },
  methods: {
    logout() {
      this.$http.get('/uac/user/logout',
          {
            headers: {
              'token': sessionStorage.getItem('token'),
            }
          }).then((result) => {//提交成功
        sessionStorage.removeItem("token")
        this.$router.push('/');//跳转页面
      }).catch((error) => {//提交失败
        console.log('Error', error.message);
        this.$message.error('服务连接错误');
      });
    },
    handleAdd(){
      this.$http.post('/myLibrary/myLibrary/add',{},
          {
            headers: {
              'token': sessionStorage.getItem('token'),
            }
          }).then((result) => {//提交成功
        this.$message.error(result.data.msg);
      }).catch((error) => {//提交失败
        console.log('Error', error.message);
        this.$message.error('服务连接错误');
      });
    },
    handleDelete() {
      this.$http.delete('/myLibrary/myLibrary/delete',
          {
            headers: {
              'token': sessionStorage.getItem('token'),
            }
          }).then((result) => {//提交成功
          this.$message.error(result.data.msg);
      }).catch((error) => {//提交失败
        console.log('Error', error.message);
        this.$message.error('服务连接错误');
      });
    },
    handleEdit() {
      this.$http.post('/myLibrary/myLibrary/edite',{},
          {
            headers: {
              'token': sessionStorage.getItem('token'),
            }
          }).then((result) => {//提交成功
        this.$message.error(result.data.msg);
      }).catch((error) => {//提交失败
        console.log('Error', error.message);
        this.$message.error('服务连接错误');
      });
    },
    getBookList() {
      this.$http.get('/myLibrary/myLibrary/list',
          {
            headers: {
              'token': sessionStorage.getItem('token'),
            }
          }).then((result) => {//提交成功
        if (result.data.code == 0) {
          console.log(result.data.data)

          this.tableData = result.data.data
          this.total = 100
        } else {
          console.error("--------")
        }
      }).catch((error) => {//提交失败
        console.log('Error', error.message);
        this.$message.error('服务连接错误');
      });
    },

    current_change: function (currentPage) {
      this.currentPage = currentPage;
    },
    handleSizeChange(pageSize) {
      this.highPageSize = pageSize
      this.highCurrentPage = 1
      this.handleCurrentChange(this.currentPage)
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.getList()
    }
  }
}
</script>
