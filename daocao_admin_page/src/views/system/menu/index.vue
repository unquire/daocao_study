<template>
      <!-- 头部搜索框 -->
      <el-form :model="queryForm" :inline="true">
        <el-form-item label="菜单名称">
            <el-input v-model="queryForm.menuName" />
        </el-form-item>
        <el-form-item label="权限名称">
            <el-input v-model="queryForm.perms" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
            <el-button @click="handleRest">重置</el-button>
        </el-form-item>
    </el-form>
    <!-- 功能按钮 -->
    <el-row :gutter="4">
        <el-col :span="6">
            <el-button type="primary" @click="handleAdd">新增</el-button>
            <el-button type="danger" @click="handleRemove(0)">删除</el-button>
        </el-col>
    </el-row>
    <!-- 列表 -->
    <el-table :data="menuListTest" style="width: 100%" row-key="id" default-expand-all @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="55" />
        <el-table-column prop="menuName" label="菜单名称" width="150" />
        <el-table-column prop="perms" label="权限名称" width="170" />
        <el-table-column prop="path" label="组件路径" width="170" />
        <el-table-column prop="componentPath" label="组件名称" width="170" />
        <el-table-column prop="createTime" label="创建时间" width="200" />
        <el-table-column prop="updateTime" label="修改时间" width="200" />
        <el-table-column prop="remark" label="备注" width="150" />
        <el-table-column label="操作" width="200">
            <template #default="scope">
                <!-- v-if和v-show区别 -->
                <el-button link type="primary" size="small" v-if="scope.row.menuType != 2">新增</el-button>
                <el-button link type="success" size="small" @click="handleEdit(scope.row.id)">修改</el-button>
                <el-button link type="danger" size="small" @click="handleRemove(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagination_container">
        <el-pagination 
            v-model:current-page="queryForm.pageNum" 
            v-model:page-size="queryForm.pageSize"
            :page-sizes="[10, 20, 30, 40, 50]" 
            layout="total, sizes, prev, pager, next, jumper" 
            :total="total"
            @size-change="handleSizeChange" 
            @current-change="handleCurrentChange" />
    </div>

     <!-- 新增和修改的弹窗 -->
     <el-dialog v-model="menuFormShow" :title="menuTitle" width="50%" :before-close="handleClose">
        <!-- 表单 -->
        <el-form :model="form" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="上级菜单" prop="form.parentId">
                        <el-tree-select check-strictly v-model="form.parentId" :data="menuSelectData" :render-after-expand="false" placeholder="请选择上级菜单"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="菜单类型" prop="form.menuType">
                        <el-select v-model="form.menuType" class="m-2" placeholder="请选择菜单类型">
                            <el-option
                                v-for="item in menuTypeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
           
            <el-row>
                <el-col :span="12">
                    <el-form-item label="菜单图标" prop="form.icon">
                        <el-popover
                            placement="bottom-start"
                            :width="460"
                            trigger="click"
                        >
                            <template #reference>
                                <!-- 通过插槽实现图标选择，默认是一个有图标的输入框 -->
                                <el-input v-model="form.icon" placeholder="请选择图标">
                                    <template #prefix>
                                        <!-- 判断是否选中了icon -->
                                        <svg-icon
                                            v-if="form.icon"
                                            slots="prefix"
                                            :name="form.icon"
                                            width="16px"
                                            height="16px"
                                        />
                                        <!-- 如果未选中，显示默认的搜索图标 -->
                                        <el-icon v-else class="el-input__icon"><search /></el-icon>
                                    </template>
                                </el-input>
                            </template>
                            <!-- 显示选择图标的组件 -->
                            <IconSelect ref="iconSelect" @selected="handleSelect" :active-icon="form.icon"/>
                        </el-popover>
                        
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="菜单名称" prop="form.menuName">
                        <el-input v-model="form.menuName" placeholder="请输入菜单名称"/>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="排序" prop="form.sort">
                        <el-input-number :min="0" v-model="form.sort"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="路由地址" prop="form.path">
                        <el-input v-model="form.path" placeholder="请输入路由地址"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="组件路径" prop="form.componentPath">
                        <el-input v-model="form.componentPath" placeholder="请输入组件路径"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="权限标识" prop="form.perms">
                        <el-input v-model="form.perms" placeholder="请输入权限标识"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="状态" prop="form.status">
                        <el-switch
                            v-model="form.status"
                            inline-prompt
                            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                            active-text="可用"
                            inactive-text="不可用"
                            :active-value="0"
                            :inactive-value="1"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注" prop="form.remark">
                        <el-input v-model="form.remark" placeholder="请输入备注"/>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="menuFormShow = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { onMounted, ref } from 'vue'
// 导入图标组件
import IconSelect from '@/components/IconSelect/index'
import { useMenuStore } from '@/stores/menu.js'
import { storeToRefs } from "pinia"
import { searchMenuAll, saveMenu, searchMenuById, updateMenu, deleteMenu } from '@/api/menu/index.js'
// 消息提示
// import { ElMessage } from 'element-plus';
// const menuStore = useMenuStore();
// const { menuList }  = storeToRefs(menuStore);
// const menuListAll = menuList;
let menuTitle = ref("")
// 树形选择器
let menuSelectData = ref([])

let selectAll = ref([]);
onMounted(() => {
    handleSelectMenuList()
})
let menuListTest = ref([])

function handleSelectMenuList(){
    searchMenuAll().then((res)=>{
        if(res.data.code==200){
            let menuData = res.data.data;
            menuListTest.value = res.data.data;
            console.log("menuData:======>",menuData);
            let menuSelect = [];
            // 添加一个根目录
            menuSelect.push({label:"主目录",value:0,children:[]});
            for (const item of menuData) {
                let label = item.menuName;
                let value = item.id;
                let childrenList = [];
                if(item.children.length>0){
                    for (const childrenItem of item.children) {
                        let label = childrenItem.menuName;
                        let value = childrenItem.id;
                        let children = {label:label,value:value}
                        childrenList.push(children)
                    }
                } 
                menuSelect[0].children.push({label:label,value:value,children:childrenList});
            }
            console.log(" menuSelect:====>",menuSelect);
            console.log(" menuSelect[0]:====>",menuSelect[0]);
            menuSelectData.value = menuSelect;
        }
    })
}
let total = ref(0)
let queryForm = ref({
    manuName:undefined,
    paerms:undefined,
    pageNum:1,// 当前页
    pageSize:4 //总页数
})

// 新增和修改数据时的表单数据
let form = ref({
    id: undefined,
    parentId: undefined,
    menuName: undefined,
    path: undefined,
    componentPath: undefined,
    perms: undefined,
    icon: undefined,
    menuType: 0,
    sort: 0,
    status: 0,
    remark: undefined,
})
let menuFormShow = ref(false);

// 菜单类型选择器
let menuTypeOptions = ref([
    {
        value: 0,
        label: "目录"
    },
    {
        value: 1,
        label: "菜单"
    },
    {
        value: 2,
        label: "按钮"
    }
])
// 触发图标替换事件，select后台触发事件，由子组件返回个name，父组件进行修改
function handleSelect(name){
    console.log("name:=====>",name);
    form.value.icon = name;
}
// 搜索
function handleQuery(){

}
// 点击触发事件，将tab框显示出来
function handleAdd(){
    menuFormShow.value = true;
    menuTitle.value = "新增菜单";
}
// 点击触发修改页面
function handleUpdate(){
    menuFormShow.value = true;
    menuTitle.value = "修改菜单"
}
// 修改数据
function handleEdit(id){
    menuFormShow.value = true;
    menuTitle.value = "修改菜单"
    searchMenuById(id).then((res)=>{
        if(res.data.code == 200){
            form.value = res.data.data;
        }
    })
}
function handleSelectionChange(selection){
    console.log(selection);
    let ids = selection.map(item=>item.id);
    console.log(ids)
    selectAll.value = ids
}
function handleRemove(ids){
    let id=undefined;
    if(ids>0){
        id = [ids]
    }else{
        id = selectAll.value;
    }
    ElMessageBox.confirm(
    '您是要删除这些数据吗?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
      center: true,
    }
    )
    .then(() => {
        console.log("进入到了OK界面!")
        deleteMenu(id).then(res => {
            if(res.data.code == 200){
                ElMessage({
                        message: '菜单删除成功',
                        type: 'success'
                }) 
                handleSelectMenuList();     
            }else{
                ElMessage({
                        message: '菜单修改失败',
                        type: 'warning'
                }) 
            }
        })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })

}

function handleSubmit(){
    // 做数据校验
    if(form.value.id){
        updateMenu(form.value).then(res =>{
            if(res.data.code == 200){
                menuFormShow.value = false;
                // 刷新菜单
                handleSelectMenuList();
                ElMessage({
                    message: '菜单修改成功',
                    type: 'success'
                }) 
                console.log("菜单刷新已完成!");
                form.value = ref({
                        id: undefined,
                        parentId: undefined,
                        menuName: undefined,
                        path: undefined,
                        componentPath: undefined,
                        perms: undefined,
                        icon: undefined,
                        menuType: 0,
                        sort: 0,
                        status: 0,
                        remark: undefined,
                    })    
            } else {
                menuFormShow.value = false;
                ElMessage({
                    message: '菜单修改失败',
                    type: 'warning'
                })   
            }
        })
        }else{
        saveMenu(form.value).then(res =>{
        if(res.data.code == 200){
            menuFormShow.value = false;
            // 刷新菜单
            handleSelectMenuList();
            ElMessage({
                message: '菜单添加成功',
                type: 'success'
            })     
        } else {
            menuFormShow.value = false;
                // 刷新菜单
            handleSelectMenuList();
            ElMessage({
                message: '菜单添加失败',
                type: 'warning',
            })
            form.value = ref({
                        id: undefined,
                        parentId: undefined,
                        menuName: undefined,
                        path: undefined,
                        componentPath: undefined,
                        perms: undefined,
                        icon: undefined,
                        menuType: 0,
                        sort: 0,
                        status: 0,
                        remark: undefined,
                    })   
        }
    })
    }
}
function handleClose(){
    menuFormShow.value = false;
    form.value = ref({
                        id: undefined,
                        parentId: undefined,
                        menuName: undefined,
                        path: undefined,
                        componentPath: undefined,
                        perms: undefined,
                        icon: undefined,
                        menuType: 0,
                        sort: 0,
                        status: 0,
                        remark: undefined,
                    })
}

</script>

<style lang="scss" scoped>
    .pagination_container{
       position: relative;
       height:40px;
       margin-top:8px;
       right:30px 
    }
    .el-pagination{
        position:absolute;
        right:0px
    }
</style>