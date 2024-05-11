<template>
    <!-- 根div -->
    <div class="login_container">
        <!-- 登录表单 -->
        <div class="login_form">
            <h3 class="title">稻草快速开发平台</h3> 
            <el-form ref="formRef" :model="loginForm">
            <!-- 用户名 -->
            <el-form-item >
                <el-input v-model="loginForm.account"  placeholder="请输入账户" >
                    <template #prefix>
                        <el-icon class="el-input__icon"><User /></el-icon>
                    </template>
                </el-input>
            </el-form-item>
             <!-- 密码 -->
            <el-form-item >
                <el-input v-model="loginForm.password"  placeholder="请输入密码" type="password">
                    <template #prefix>
                        <el-icon class="el-input__icon"><Lock /></el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <div class="rememberMe">
                <!-- remember me -->
                <el-checkbox v-model="loginForm.rememberMe" label="记住我" size="large"/>
                <!-- forget password -->
                <el-text class="forgetpassword" type="primary">忘记密码</el-text>
            </div>
            <el-divider>其他登录方式</el-divider>
            <div class="other_login">
                <el-icon class="other_login_item"><ChromeFilled /></el-icon>
                <el-icon class="other_login_item"><ChromeFilled /></el-icon>
                <el-icon class="other_login_item"><ChromeFilled /></el-icon>
            </div>
            <el-form-item>
                    <!-- 按钮 -->
                    <el-button style="width: 100%;" type="primary" @click="handleLogin">登录</el-button>
            </el-form-item>  
            </el-form>
        </div>
        
    </div>

</template>

<script setup>
    // 导入ref
    import { ref } from 'vue'
    // 导入login方法
    import { login } from '@/api/auth/index.js'
    // 导入setToken方法
    import { setToken } from '@/utils/token/index.js'
    import { searchSelfRouter,searchUserInfo } from '@/api/user/index.js'
    import { useMenuStore } from '@/stores/menu.js'
    import { useUserStore } from '@/stores/user.js'
    import { useRouter }    from 'vue-router'
    // 声明表单绑定值
    const menuStore = useMenuStore();
    const userStore = useUserStore();
    const router = useRouter();
    const loginForm = ref({
        account: undefined,
        password: undefined,
        rememberMe: undefined
    })
    function handleLogin(){
        // 调用login方法
        login(loginForm.value).then((res)=>{
            console.log("登录====>",res);
            // 判断是否成功
            if(res.data.code == 200){
                // 将用户token存入到pinia/sessionStorage中
                setToken("daocaoToken",res.data.token);
                // TODO 获取用户的路由访问权限
                searchSelfRouter().then(res=>{
                    console.log("res=====>",res);
                    // 将路由信息存储到pinia中
                    menuStore.setMenuList(res.data.data);
                    // 跳转页面  /index
                    // 1.在路由守卫上渲染动态路由
                    // 2.开发项目主页面【左侧导航，头部，主体部分】
                    router.push("/index")
                }); 
                searchUserInfo().then(res=>{
                    // 存储到pinia中
                    if(res.data.code == 200){
                        userStore.setUserInfo(res.data.data);
                    }
                });
                console.log("登录成功!");
            }
        })
    }
</script>

<style lang="scss" scoped>
.login_container {
    // 背景图
    background-image: url('../assets/lefulan.png');
    background-size: 100%;
    height: 100vh;
    display: flex;
    justify-content: flex-end;
    .login_form {
        display: flex;
        justify-content: center;
        align-items: center;
        //设置换行
        flex-direction: column;
        width: 50%;
        background-color: #fff;

        .title {
            margin-bottom: 20px;
        }
        .rememberMe {
            display: flex;
            justify-content: space-between;
            align-items: center;
            .forgetpass {
                cursor: pointer;
            }
        }
        // 其他登录
        .other_login {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
            .other_login_item {
                margin-right: 30px;
                margin-left:30px;
                cursor: pointer;
            }
        }

    }


// 设置form的宽度
.el-form {
    width: 60%;
}
.el-form-item {
    width: 100%;
}

}

</style>