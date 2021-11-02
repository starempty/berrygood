import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue";
import Project from "@/views/Project.vue";
import Fundingcreate from "@/views/Fundingcreate.vue";
import Fundingdetail from "@/views/Fundingdetail.vue";
import Fundingupdate from '@/components/Projectupdate/Fundingupdate.vue'
import Donationlist from "@/components/Project/Donationlist.vue";
import Fundinglist from "@/components/Project/Fundinglist.vue";
import Servicelist from "@/components/Project/Servicelist.vue";
import Mypage from "@/views/Mypage.vue";
import Mydata from "@/views/User.vue";
import Bloodinput from "@/views/Bloodinput.vue";
import Signup from "@/views/Signup.vue";
import Login from "@/views/Login.vue";
import Notfound from "@/views/Notfound.vue";
import Donationcreate from "@/views/Donationcreate.vue";
import Donationdetail from "@/views/Donationdetail.vue";
import Donationupdate from "@/components/Projectupdate/Donationupdate.vue"
import Servicecreate from "@/views/Servicecreate.vue";
import Servicedetail from "@/views/Servicedetail.vue";
import Serviceupdate from "@/components/Projectupdate/Serviceupdate.vue"
import store from '../store/index.js';

const Auth = () => (to, from, next) => {
  const token = store.state.token
  if (token) {
    return next()
  }
  next('/login')
}


const User = () => (to, from, next) => {
  const role = store.state.user.role
  const token = store.state.token
  if (role === 'user' && token) {
    return next()
  }
}

const Company = (Fundinglist) => (to, from, next) => {
  const role = store.state.user.role
  const token = store.state.token
  if (role === 'company' && token) {
    return next()
  }
  else { 
    router.push({ name: Fundinglist })
  }
}

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/project',
    name: 'Project',
    component: Project,
    beforeEnter: Auth(),
  },
  {
    path: '/donation',
    name: 'Donationlist',
    component: Donationlist,
  },
  {
    path: '/donation/create',
    name: 'Donationcreate',
    component: Donationcreate,
    beforeEnter: Company('Donationlist'),
  },
  {
    path: '/donation/detail/:donationNo',
    name: 'Donationdetail',
    component: Donationdetail,
    props: true,
    beforeEnter: Auth(),
  },
  {
    path: '/donation/update/:donationNo',
    name: 'Donationupdate',
    component: Donationupdate,
    props: true,
    beforeEnter: Company('Donationlist'),
  },
  {
    path: '/funding',
    name: 'Fundinglist',
    component: Fundinglist,
  },
  {
    path: '/funding/create',
    name: 'Fundingcreate',
    component: Fundingcreate,
    beforeEnter: Company('Fundinglist'),
  },
  {
    path: '/funding/detail/:fundingNo',
    name: 'Fundingdetail',
    component: Fundingdetail,
    props: true,
    beforeEnter: Auth(),
  },
  {
    path: '/funding/update/:fundingNo',
    name: 'Fundingupdate',
    component: Fundingupdate,
    props: true,
    beforeEnter: Company('Fundinglist'),
  },
  {
    path: '/service',
    name: 'Servicelist',
    component: Servicelist,
  },
  {
    path: '/service/create',
    name: 'Servicecreate',
    component: Servicecreate,
    beforeEnter: User('Servicelist'),
  },
  {
    path: '/service/detail/:serviceNo',
    name: 'Servicedetail',
    component: Servicedetail,
    props: true,
    beforeEnter: Auth(),
  },
  {
    path: '/service/update/:serviceNo',
    name: 'Serviceupdate',
    component: Serviceupdate,
    props: true,
    beforeEnter: User('Servicelist'),
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage,
    beforeEnter: Auth(),
  },
  {
    path: '/blood',
    name: 'Bloodinput',
    component: Bloodinput,
    beforeEnter: Auth(),
  },
  {
    path: '/mydata',
    name: 'Mydata',
    component: Mydata,
    beforeEnter: Auth(),
  },
  {
    path: "/:catchAll(.*)",
    name: 'Notfound',
    component: Notfound,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior () {
    return { top: 0 }
  }
})

export default router
