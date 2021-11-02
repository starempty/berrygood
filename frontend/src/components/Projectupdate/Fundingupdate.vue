<template>
  <div class="update-box">
    <div class="funding-update-box container">
      <el-form
        :label-position="'top'"
        :rules="state.rules"
        :model="state.fundingUpdate"
        v-model="state.fundingUpdate"
      >
        <el-form-item prop="name" label="펀딩 이름">
          <el-input class="funding-input" v-model="state.fundingUpdate.name"></el-input>
        </el-form-item>
        <div class="row">
          <el-form-item label="펀딩 마감 기한 (1달)" class="col">
            <el-input class="funding-input" v-model="state.fundingUpdate.due" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item prop="amount" label="목표 베리 (B)" class="col">
            <el-input class="funding-input" v-model="state.fundingUpdate.amount"></el-input>
          </el-form-item>
        </div>
        <el-form-item label="펀딩 소개">
          <editor
            v-model="state.fundingUpdate.content"
            api-key="tk5btofsrwela5rs0fweq2p0bwoiry8iyiidu1gqly2eyz4n"
            :init="{
              language: 'ko_KR',
              height: 500,
              menubar: 'favs file edit view insert format tools table help',
              plugins: [
              'advlist autolink link lists charmap print preview hr anchor pagebreak',
              'searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime nonbreaking',
              'table emoticons template paste help'
              ],
              toolbar:
              'undo redo | styleselect | bold italic forecolor backcolor emoticons | alignleft aligncenter alignright alignjustify | ' +
              'bullist numlist outdent indent | link image | print preview media fullpage | ' +
              'help'
            }"
          />
        </el-form-item>
        <!-- <input @change="fileSelect()" id="upload" type="file" accept="image/*" multiple/> -->
        <input type="file" name="file" accept="image/*" id="upload" @change="fileSelect()">
        <label for="upload" class="btn uploadBtn">사진 업로드<i class="fas fa-plus mx-2"></i></label>
        <span id="update-warning">* 수정 시 기존 사진은 삭제됩니다.</span>
        <div class="upload">
          <div v-for="(file, i) in state.fundingUpdate.files" :key="i" class="img-list animate__animated animate__fadeIn">
            <div id="img">
              <i class="far fa-image me-2"></i>
              <span id="fileName">{{ file[0].name }}</span>
            </div>
            <i class="fas fa-times" @click="fileDelete(i)"></i>
          </div>
        </div>
        <el-form-item class="funding-submit">
          <el-button @click="clickUpdate">수정 완료</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Editor from '@tinymce/tinymce-vue'

export default {
  components: {
    editor: Editor,
  },
  props: {
    fundingNo: String,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      funding: {},
      fundingUpdate: {
        name: '',
        due: '',
        amount: '',
        files: [],
        content: '',
      },
      user: computed(() => store.getters['getUser']),
      rules: {
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
          {
            pattern: /^[0-9]/g,
            message:
              "숫자만 입력 가능합니다.",
            trigger: ["blur", "change"],
          },
        ],
      }
    })

    const fileSelect = () => {
      const uploadimg = document.getElementById("upload")
      state.fundingUpdate.files.push(uploadimg.files)
    }
    const fileDelete = (idx) => {
      state.fundingUpdate.files.splice(idx, 1)
    }
    const clickUpdate = () => {
      if (!state.fundingUpdate.name) {
        ElMessage.error('펀딩명을 작성해주세요.')
      } else if (!state.fundingUpdate.amount) {
        ElMessage.error('목표 베리를 입력해주세요.')
      } 
      else if (!state.fundingUpdate.content) {
        ElMessage.error('펀딩 소개글을 작성해주세요.')
      } else if (!state.fundingUpdate.files[0]) {
        ElMessage.error('사진을 선택해주세요.')
      } else {
        const formData = new FormData()
        formData.append('name', state.fundingUpdate.name)
        formData.append('content', state.fundingUpdate.content)
        formData.append('due', state.fundingUpdate.due)
        formData.append('amount', state.fundingUpdate.amount)
        for (var i=0; i < state.fundingUpdate.files.length; i++) {
          formData.append('files', state.fundingUpdate.files[i][0])
        }
        formData.append('email', state.user.email)
        store.dispatch('Funding/fundingUpdate', { fundingNo: props.fundingNo, formData: formData })
          .then(() => {
            ElMessage({
              message: "수정되었습니다.",
              type: "success"
            })
            router.push({ name: 'Fundingdetail', params: { fundingNo: props.fundingNo }})
          })
      }
    }
    onMounted(() => {
      store.dispatch('Funding/fundingDetail', props.fundingNo)
      .then(({ data }) => {
        state.funding = data.funding
        state.fundingUpdate.name = data.funding.name
        state.fundingUpdate.content = data.funding.content
        state.fundingUpdate.amount = data.funding.amount
        state.fundingUpdate.due = data.funding.due
      })
    })
    return { state, fileSelect, fileDelete, clickUpdate }
  },
}
</script>

<style>
.update-box {
  height: 95vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.funding-input-box {
  width: 100%;
  height: 30vh;
  /* border: 1px solid black; */
  padding: 20px;
}
.funding-update-box .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  margin-bottom: 0 !important;
  /* margin-bottom: 1vh; */
}
.funding-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.funding-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.funding-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.funding-submit .el-form-item__content {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.funding-submit .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.funding-submit .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.funding-submit .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.upload {
  /* border: 1px solid #f78065;
  min-height: 12vh;
  border-radius: 10px; */
  padding: 1% 0;
  width: 100%;
}
#upload {
  display: none;
}
.img-list {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0 2%;
  margin-bottom: 5px;
  
}
.img-list:hover {
  cursor: pointer;
  background-color: #ffe5df;
  transition: all 1s;
  border-radius: 5px;
}
.img-list #img {
  width: 95%;
  /* border: 1px solid black; */
}
label.btn.uploadBtn {
  /* border: 1px solid #f78065; */
  padding: 0;
  margin-bottom: 0 !important;
  margin-right: 2%;
}
label.btn.uploadBtn:hover {
  /* border: 1px solid #f78065; */
  font-weight: 500;
  transform: scale(1.1);
}
.fa-times {
  color: rgb(255, 64, 64);
}
.fa-times:hover {
  cursor: pointer;
  transform: scale(1.1);
}
.fa-plus {
  color: #67c23a;
}
#update-warning {
  color: #f56c6c;
  font-size: 14px;
  font-weight: 600;
}
</style>