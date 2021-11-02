<template>
  <div class="service-create-box">
    <el-form
      :label-position="'top'"
      :rules="state.rules"
      :model="state.serviceCreate"
      v-model="state.serviceCreate"
    >
      <el-form-item prop="name" label="활동 이름">
        <el-input class="service-input" v-model="state.serviceCreate.name"></el-input>
      </el-form-item>
      <el-form-item label="활동 일자" class="picker">
        <el-date-picker
          v-model="state.serviceCreate.date"
          type="datetime"
          format="YYYY-MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm"
          :default-value="defaultTime1"
        >
        </el-date-picker>
      </el-form-item>
      <div class="row">
        <el-form-item prop="type" label="활동 유형" class="picker col">
          <el-select v-model="state.serviceCreate.type" placeholder="유형 선택">
            <el-option label="사회복지 및 보건증진" value="사회복지 및 보건증진"></el-option>
            <el-option label="환경보전" value="환경보전"></el-option>
            <el-option label="사회적 취약계층 권익 증진" value="사회적 취약계층 권익 증진"></el-option>
            <el-option label="지역사회 개발" value="지역사회 개발"></el-option>
            <el-option label="예체능 진흥" value="예체능 진흥"></el-option>
            <el-option label="공익사업" value="공익사업"></el-option>
            <el-option label="공공행정" value="공공행정"></el-option>
            <el-option label="기타" value="기타"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="num" label="모집 인원" class="col">
          <el-input class="service-input" v-model="state.serviceCreate.num"></el-input>
        </el-form-item>
      </div>
      <el-form-item label="활동 소개">
        <editor
          v-model="state.serviceCreate.content"
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
        <div v-for="(file, i) in state.serviceCreate.files" :key="i" class="img-list animate__animated animate__fadeIn">
          <div id="img">
            <i class="far fa-image me-2"></i>
            <span id="fileName">{{ file[0].name }}</span>
          </div>
          <i class="fas fa-times" @click="fileDelete(i)"></i>
        </div>
      </div>
      <el-form-item class="service-submit">
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
    const end = new Date(today.setMonth(today.getMonth() + 2))
    // const amountValid = /^[0-9]/g

    const state = reactive ({
      serviceCreate: {
        name: '',
        date: end.getFullYear() + '-' + ('0' + end.getMonth()).slice(-2) + '-' + ('0' + end.getDate()).slice(-2),
        files: [],
        num: '',
        type: '',
        content: '',
      },
      user: computed(() => store.getters['getUser']),
      rules: {
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" }
        ],
        date: [
          { required: true, message: '필수 항목입니다.', trigger: 'blur'},
        ],
        type: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" }
        ],
        num: [
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
      state.serviceCreate.files.push(uploadimg.files)
      console.log(state.serviceCreate.files)
    }
    const fileDelete = (idx) => {
      state.serviceCreate.files.splice(idx, 1)
      console.log(state.serviceCreate.files)
    }
    const clickCreate = () => {
      if (!state.serviceCreate.name) {
        ElMessage.error('활동명을 작성해주세요.')
      } else if (!state.serviceCreate.date) {
        ElMessage.error('활동 일자를 입력해주세요.')
      } else if (!state.serviceCreate.type) {
        ElMessage.error('활동 유형을 선택해주세요.')
      } else if (!state.serviceCreate.num) {
        ElMessage.error('모집 인원을 작성해주세요.')
      } else if (!state.serviceCreate.content) {
        ElMessage.error('활동 소개글을 작성해주세요.')
      } else if (!state.serviceCreate.files[0]) {
        ElMessage.error('사진을 선택해주세요.')
      } else {
        const formData = new FormData()
        formData.append('name', state.serviceCreate.name)
        formData.append('content', state.serviceCreate.content)
        formData.append('date', state.serviceCreate.date)
        formData.append('type', state.serviceCreate.type)
        formData.append('num', state.serviceCreate.num)
        for (var i=0; i < state.serviceCreate.files.length; i++) {
          formData.append('files', state.serviceCreate.files[i][0])
        }
        formData.append('email', state.user.email)
        store.dispatch('Service/serviceCreate', formData)
          .then(() => {
            router.push({ name: 'Servicelist' })
          })
      }
    }
    return { state, fileSelect, fileDelete, clickCreate }
  },
}
</script>

<style>
.service-create-box {
  width: 100%;
  min-height: 80vh;
  /* border: 1px solid black; */
  padding: 20px;
  margin-top: 12vh;
}
.service-create-box .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  /* margin-bottom: 1vh; */
  margin: 0 !important;
}
.service-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.service-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.service-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.service-submit .el-form-item__content {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.picker .el-form-item__content .el-date-editor {
  width: 100%;
}
.el-date-picker {
  --el-datepicker-active-color: #f78065 !important;
  --el-datepicker-hover-font-color: #f78065 !important;
}
.el-date-picker .el-button--text {
  color: #f78065 !important;
}
.available.today span {
  color: #f78065 !important;
}
.el-date-picker .el-button.is-plain:focus, .el-button.is-plain:hover {
  background: var(--el-color-white);
  border-color: #f78065 !important;
  color: #f78065 !important;
}
.picker .el-form-item__content .el-select {
  width: 100%;
}
.picker .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: 600;
}
.picker .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.picker .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.el-select .el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
.el-select .el-input.is-focus .el-input__inner {
  border-color: #f78065 !important;
}
.service-submit .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  margin-bottom: 10vh;
}
.service-submit .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.service-submit .el-button:focus {
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