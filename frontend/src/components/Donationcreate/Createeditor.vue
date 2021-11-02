<template>
  <div class="donation-create-box">
    <el-form
      :label-position="'top'"
      :rules="state.rules"
      :model="state.donationCreate"
      v-model="state.donationCreate"
    >
      <el-form-item prop="name" label="기부 이름">
        <el-input class="donation-input" v-model="state.donationCreate.name"></el-input>
      </el-form-item>
      <el-form-item label="기부 마감 기한 (3달)">
        <el-input class="donation-input" v-model="state.donationCreate.due" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="기부 소개">
        <editor
          v-model="state.donationCreate.content"
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
      <div class="upload">
        <div v-for="(file, i) in state.donationCreate.files" :key="i" class="img-list animate__animated animate__fadeIn">
          <div id="img">
            <i class="far fa-image me-2"></i>
            <span id="fileName">{{ file[0].name }}</span>
          </div>
          <i class="fas fa-times" @click="fileDelete(i)"></i>
        </div>
      </div>
      <el-form-item class="donation-submit">
        <el-button @click="clickCreate">작성 완료</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Editor from '@tinymce/tinymce-vue'

export default {
  components: {
    editor: Editor,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const today = new Date()
    const end = new Date(today.setMonth(today.getMonth() + 4))

    const state = reactive ({
      donationCreate: {
        name: '',
        due: end.getFullYear() + '-' + ('0' + end.getMonth()).slice(-2) + '-' + ('0' + end.getDate()).slice(-2),
        files: [],
        content: '',
      },
      user: computed(() => store.getters['getUser']),
      rules: {
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" }
        ],
      }
    })

    const fileSelect = () => {
      const uploadimg = document.getElementById("upload")
      state.donationCreate.files.push(uploadimg.files)
      console.log(state.donationCreate.files)
    }
    const fileDelete = (idx) => {
      state.donationCreate.files.splice(idx, 1)
      console.log(state.donationCreate.files)
    }
    const clickCreate = () => {
      if (!state.donationCreate.name) {
        ElMessage.error('기부명을 작성해주세요.')
      } else if (!state.donationCreate.content) {
        ElMessage.error('기부 소개글을 작성해주세요.')
      } else if (!state.donationCreate.files[0]) {
        ElMessage.error('사진을 선택해주세요.')
      } else {
        const formData = new FormData()
        formData.append('name', state.donationCreate.name)
        formData.append('content', state.donationCreate.content)
        formData.append('due', state.donationCreate.due)
        for (var i=0; i < state.donationCreate.files.length; i++) {
          console.log(state.donationCreate.files[i][0])
          formData.append('files', state.donationCreate.files[i][0])
        }
        formData.append('email', state.user.email)
        store.dispatch('Donation/donateCreate', formData)
          .then(() => {
            router.push({ name: 'Donationlist' })
          })
      }
    }
    return { state, fileSelect, fileDelete, clickCreate }
  },
}
</script>

<style>
.donation-create-box {
  width: 100%;
  min-height: 80vh;
  /* border: 1px solid black; */
  padding: 20px;
  margin-top: 12vh;
  /* margin-bottom: 15vh; */
}
.donation-create-box .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  margin-bottom: 0 !important;
  /* margin-bottom: 1vh; */
}
.donation-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.donation-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.donation-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.donation-submit .el-form-item__content {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.donation-submit .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  margin-bottom: 10vh;
}
.donation-submit .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.donation-submit .el-button:focus {
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
  margin-bottom: 2%;
}
label.btn.uploadBtn:hover {
  /* border: 1px solid #f78065; */
  padding: 0;
  margin-bottom: 2%;
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
</style>