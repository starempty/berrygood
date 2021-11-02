<template>
  <div class="container">
    <div class="user-info-box">
      <el-card shadow="always">
        <table v-if="!state.editMode" class="user-info-table">
          <tr><td><b>이름</b></td><td>{{state.user.name}}</td></tr>
          <tr v-if="state.user.role === 'user'"><td><b>별명</b></td><td>{{state.user.nickname}}</td></tr>
          <tr><td><b>이메일</b></td><td>{{state.user.email}}</td></tr>
          <tr><td><b>전화번호</b></td><td>{{state.user.phone}}</td></tr>
          <tr><td><b>주소</b></td><td>{{state.user.address}}</td></tr>
          <tr v-if="state.user.role === 'user'"><td><b>생일</b></td><td>{{state.user.birthday}}</td></tr>
          <tr v-if="state.wallet"><td><b>지갑계정</b></td><td>{{state.wallet.address}}</td></tr>
        </table>
        <table v-if="state.editMode" class="user-info-table">
          <tr><td><b>이름</b></td><td>{{state.user.name}}</td></tr>
          <tr v-if="state.user.role === 'user'"><td><b>별명</b></td><td><el-input v-model="state.user.nickname"/></td></tr>
          <tr><td><b>이메일</b></td><td>{{state.user.email}}</td></tr>
          <tr><td><b>전화번호</b></td><td><el-input v-model="state.user.phone"/></td></tr>
          <tr><td><b>주소</b></td><td><el-input v-model="state.user.address"/></td></tr>
          <tr v-if="state.user.role === 'user'"><td><b>생일</b></td><td>{{state.user.birthday}}</td></tr>
          <tr v-if="state.wallet"><td><b>지갑계정</b></td><td>{{state.wallet.address}}</td></tr>
        </table>
        <div style="width: 100%; text-align:center;">
        <el-button class="orange-btn" v-if="!state.editMode" @click="ToEditMode">수정</el-button>
        <el-button class="blue-btn" v-if="!state.editMode" @click="ToDeleteMode">탈퇴</el-button>
        <el-button class="orange-btn" v-if="state.editMode" @click="ToSaveMode">저장</el-button>
        </div>
      </el-card>
    </div>
  </div>
   <Footer/>
  <el-dialog
    title="회원정보 탈퇴"
    v-model="state.dialogVisible"
    width="500px"
  >
    <div style="text-align: center">
      <h3>정말로 탈퇴를 원하시나요?</h3>
      <p>탈퇴하기 버튼을 클릭하면 BERRYGOOD에서 탈퇴됩니다.</p>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="userDelete">탈퇴하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import Footer from "@/views/Footer.vue"

export default {
  components : {
    Footer,
  },
  setup() {
    const store = useStore()
    const state = reactive ({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      editMode:false,
      dialogVisible: false,
    })
    
    
    onMounted(() => {
      console.log('mydata')
    })
    const ToEditMode = () => {
      state.editMode = !state.editMode;
    }
    const ToSaveMode = () => {
      if(state.user.role === "company"){
        let payload = {
          email: state.user.email,
          address: state.user.address,
          name: state.user.name,
          phone: state.user.phone,
        }
        
        store.dispatch('Mydata/compEdit', payload).then(()=>{
          store.commit('USER', state.user);
          state.editMode = !state.editMode;
          ElMessage ({
            message: '수정이 완료되었습니다.',
            type: 'success'
          })
        }).catch(()=>{
          ElMessage.error('수정이 취소되었습니다. 다시 한 번 시도해주세요.')
        })
      }else{
        let payload = {
          email: state.user.email,
          address: state.user.address,
          nickname: state.user.nickname,
          phone: state.user.phone,
        }
        console.log(payload)
        store.dispatch('Mydata/userEdit', payload).then(()=>{
          store.commit('USER', state.user);
          state.editMode = !state.editMode;
          ElMessage ({
            message: '수정이 완료되었습니다.',
            type: 'success'
          })
        }).catch(()=>{
          ElMessage.error('수정이 취소되었습니다. 다시 한 번 시도해주세요.')
        })
      }
      
    }
    const ToDeleteMode = () => {
      state.dialogVisible = true;
    }
    const userDelete = () => {
      state.dialogVisible = false;
      if(state.user.role === "company"){
        store.dispatch('Mydata/compDelete', state.user.email).then(()=>{
          store.commit('USER', {});
          ElMessage ({
            message: '탈퇴가 완료되었습니다.',
            type: 'success'
          })
        }).catch(()=>{
          ElMessage.error('탈퇴가 취소되었습니다. 다시 한 번 시도해주세요.')
        })
      }else{
        store.dispatch('Mydata/userDelete', state.user.email).then(()=>{
          store.commit('USER', {});
          ElMessage ({
            message: '탈퇴가 완료되었습니다.',
            type: 'success'
          })
        }).catch(()=>{
          ElMessage.error('탈퇴가 취소되었습니다. 다시 한 번 시도해주세요.')
        })
      } 
    }
    return {
      state, 
      ToEditMode, 
      ToSaveMode,
      ToDeleteMode,
      userDelete
    }
  },
}
</script>

<style>
.user-info-box {
  width: 100%;
  height: calc(100vh - 100px);
  display: flex;
  justify-content: center;
  align-items: center;
  padding:10px;
  margin: 10px;
}
.user-info-table{
  width: 100%;
  /* border-collapse: separate;
  border-spacing: 0 50px; */
}

.user-info-table tr td{
  padding: 20px;
  margin: 20px;
}
.el-button#wallet-create-btn {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  padding: 1vw 1vh;
}
#wallet-create-btn:hover {
  opacity: 0.8;
}
.el-button#key-copy {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
#key-copy:hover {
  opacity: 0.8;
}
.el-button#key-close {
  /* background-color: lightcoral;
  border-color: lightcoral; */
  color: white;
}
#key-close:hover {
  opacity: 0.8;
}
#key-warning {
  margin-bottom: 1vh;
  color: #f56c6c;
  font-weight: bold;
}
#normal {
  color: black;
  font-weight: normal;
}
.fa-exclamation-triangle {
  color: #e6a23c;
}
.el-message-box__title span {
  color: #f56c6c;
  font-weight: bold;
}
.el-message-box__btns .el-button--default {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.el-message-box__btns .el-button--default:hover {
  opacity: 0.6;
}
.el-message-box__btns .el-button--primary {
  background-color: #f56c6c !important;
  border-color: #f56c6c !important;
  color: white;
  opacity: 0.9;
}
.el-message-box__btns .el-buttont--primary:hover {
  opacity: 0.7;
}
.orange-btn{
  color: white !important;
  background-color: #f78065 !important;
  border: #f78065 !important;
}
.blue-btn{
  color: white !important;
  background-color: #7e93eb !important;
  border: #7e93eb !important;
}
</style>
