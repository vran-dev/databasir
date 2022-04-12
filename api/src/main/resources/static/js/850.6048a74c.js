"use strict";(self["webpackChunkdatabasir"]=self["webpackChunkdatabasir"]||[]).push([[850],{3110:function(e,a,l){l.d(a,{yw:function(){return n},Dx:function(){return o},B9:function(){return u},__:function(){return s},IJ:function(){return i},r4:function(){return d},oD:function(){return m},_:function(){return p},Uz:function(){return w},gQ:function(){return g},Gz:function(){return h}});var t=l(3872);const r="/api/v1.0/users",n=e=>t.Z.get(r,{params:e}),o=e=>t.Z.post(r+"/"+e+"/enable"),u=e=>t.Z.post(r+"/"+e+"/disable"),s=e=>t.Z.get(r+"/"+e),i=e=>t.Z["delete"](r+"/"+e),d=e=>t.Z.post(r,e),m=e=>t.Z.post(r+"/"+e+"/renew_password"),p=e=>t.Z.post(r+"/"+e+"/sys_owners"),w=e=>t.Z["delete"](r+"/"+e+"/sys_owners"),g=(e,a)=>t.Z.post(r+"/"+e+"/password",a),h=(e,a)=>t.Z.post(r+"/"+e+"/nickname",a)},4850:function(e,a,l){l.r(a),l.d(a,{default:function(){return D}});var t=l(6252),r=l(3577),n=l(9963);const o=(0,t.Uk)("重置密码"),u=(0,t.Uk)("删除账号"),s=(0,t._)("br",null,null,-1),i=(0,t._)("h3",null,"角色信息",-1),d=(0,t.Uk)("保存"),m=(0,t.Uk)("取消");function p(e,a,l,p,w,g){const h=(0,t.up)("el-button"),c=(0,t.up)("el-tooltip"),f=(0,t.up)("el-col"),b=(0,t.up)("el-input"),D=(0,t.up)("el-row"),W=(0,t.up)("el-header"),U=(0,t.up)("el-table-column"),_=(0,t.up)("el-link"),y=(0,t.up)("el-option"),C=(0,t.up)("el-select"),k=(0,t.up)("el-switch"),V=(0,t.up)("el-table"),z=(0,t.up)("el-main"),P=(0,t.up)("el-pagination"),S=(0,t.up)("el-footer"),O=(0,t.up)("el-descriptions-item"),E=(0,t.up)("el-descriptions"),R=(0,t.up)("List"),Q=(0,t.up)("el-icon"),I=(0,t.up)("el-drawer"),Z=(0,t.up)("el-form-item"),v=(0,t.up)("el-form"),x=(0,t.up)("el-dialog"),L=(0,t.up)("el-container");return(0,t.wg)(),(0,t.j4)(L,null,{default:(0,t.w5)((()=>[(0,t.Wm)(W,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{gutter:12},{default:(0,t.w5)((()=>[(0,t.Wm)(f,{span:2},{default:(0,t.w5)((()=>[(0,t.Wm)(c,{content:"创建新用户",placement:"top"},{default:(0,t.w5)((()=>[(0,t.Wm)(h,{type:"primary",icon:"plus",style:{width:"100%"},onClick:a[0]||(a[0]=e=>g.toCreatePage())})])),_:1})])),_:1}),(0,t.Wm)(f,{span:6},{default:(0,t.w5)((()=>[(0,t.Wm)(b,{onChange:g.onQuery,modelValue:w.userPageQuery.nicknameOrUsernameOrEmailContains,"onUpdate:modelValue":a[1]||(a[1]=e=>w.userPageQuery.nicknameOrUsernameOrEmailContains=e),label:"用户名",placeholder:"昵称、用户名或邮箱搜索","prefix-icon":"search"},null,8,["onChange","modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(z,null,{default:(0,t.w5)((()=>[(0,t.Wm)(V,{data:w.userPageData.content,border:"",width:"80%"},{default:(0,t.w5)((()=>[(0,t.Wm)(U,{prop:"id",label:"ID","min-width":"60",fixed:"left"}),(0,t.Wm)(U,{prop:"nickname",label:"昵称","min-width":"120",fixed:"left",resizable:""}),(0,t.Wm)(U,{prop:"username",label:"用户名","min-width":"120",resizable:""}),(0,t.Wm)(U,{label:"邮箱",width:"200",resizable:""},{default:(0,t.w5)((e=>[(0,t.Wm)(_,{underline:!0,onClick:a=>g.onGetUserDetail(e.row),type:"primary"},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(e.row.email),1)])),_:2},1032,["onClick"])])),_:1}),(0,t.Wm)(U,{label:"启用状态",resizable:""},{header:(0,t.w5)((()=>[(0,t.Wm)(C,{modelValue:w.userPageQuery.enabled,"onUpdate:modelValue":a[2]||(a[2]=e=>w.userPageQuery.enabled=e),placeholder:"启用状态",onChange:g.onQuery,clearable:""},{default:(0,t.w5)((()=>[((0,t.wg)(),(0,t.iD)(t.HY,null,(0,t.Ko)([!0,!1],(e=>(0,t.Wm)(y,{key:e,label:e?"启用":"禁用",value:e},null,8,["label","value"]))),64))])),_:1},8,["modelValue","onChange"])])),default:(0,t.w5)((e=>[(0,t.Wm)(k,{modelValue:e.row.enabled,"onUpdate:modelValue":a=>e.row.enabled=a,loading:w.loading.userEnableLoading,onChange:a=>g.onSwitchEnabled(e.row.id,e.row.enabled),disabled:g.shouldDisableSwitch(e.row)},null,8,["modelValue","onUpdate:modelValue","loading","onChange","disabled"])])),_:1}),(0,t.Wm)(U,{label:"系统管理员"},{default:(0,t.w5)((e=>[(0,t.Wm)(k,{modelValue:e.row.isSysOwner,"onUpdate:modelValue":a=>e.row.isSysOwner=a,loading:w.loading.sysOwnerLoading,onChange:a=>g.onChangeSysOwner(e.row),disabled:g.shouldDisableSwitch(e.row)},null,8,["modelValue","onUpdate:modelValue","loading","onChange","disabled"])])),_:1}),(0,t.Wm)(U,{prop:"createAt",label:"创建时间","min-width":"140"}),(0,t.Wm)(U,{label:"操作","min-width":"120",resizable:""},{default:(0,t.w5)((e=>[(0,t.Wm)(h,{icon:"Refresh",type:"warning",size:"small",onClick:(0,n.iM)((a=>g.onRenewPassword(e.row.id)),["stop"])},{default:(0,t.w5)((()=>[o])),_:2},1032,["onClick"]),(0,t.Wm)(h,{icon:"Delete",type:"danger",size:"small",onClick:(0,n.iM)((a=>g.onDeleteUser(e.row.id)),["stop"])},{default:(0,t.w5)((()=>[u])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"])])),_:1}),(0,t.Wm)(S,null,{default:(0,t.w5)((()=>[(0,t.Wm)(P,{layout:"sizes, prev, pager, next","hide-on-single-page":!1,currentPage:w.userPageData.number,"page-size":w.userPageQuery.size,"page-sizes":[10,15,20,30],"page-count":w.userPageData.totalPages,onSizeChange:g.onPageSizeChange,onCurrentChange:g.onPageChange},null,8,["currentPage","page-size","page-count","onSizeChange","onCurrentChange"])])),_:1}),(0,t.Wm)(I,{modelValue:w.isShowUserDetailDrawer,"onUpdate:modelValue":a[3]||(a[3]=e=>w.isShowUserDetailDrawer=e),title:"用户详情",direction:"rtl",size:"50%"},{default:(0,t.w5)((()=>[(0,t.Wm)(E,{title:"基础信息",column:1,border:""},{default:(0,t.w5)((()=>[(0,t.Wm)(O,{label:"ID"},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.id),1)])),_:1}),(0,t.Wm)(O,{label:"昵称"},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.nickname),1)])),_:1}),(0,t.Wm)(O,{label:"用户名"},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.username),1)])),_:1}),(0,t.Wm)(O,{label:"邮箱",span:2},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.email),1)])),_:1}),(0,t.Wm)(O,{label:"启用状态",span:2},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.enabled?"启用中":"已禁用"),1)])),_:1}),(0,t.Wm)(O,{label:"注册时间",span:2},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(w.userDetailData.createAt),1)])),_:1})])),_:1}),s,i,(0,t.Wm)(V,{data:w.userDetailData.roles,stripe:""},{default:(0,t.w5)((()=>[(0,t.Wm)(U,{label:"角色",prop:"role",formatter:w.roleNameFormatter},null,8,["formatter"]),(0,t.Wm)(U,{label:"所属分组"},{default:(0,t.w5)((e=>[e.row.groupId?((0,t.wg)(),(0,t.j4)(_,{key:0,onClick:a=>g.toGroupPage(e.row.groupId,e.row.groupName)},{default:(0,t.w5)((()=>[(0,t.Uk)((0,r.zw)(e.row.groupName)+" ",1),(0,t.Wm)(Q,null,{default:(0,t.w5)((()=>[(0,t.Wm)(R)])),_:1})])),_:2},1032,["onClick"])):(0,t.kq)("",!0)])),_:1}),(0,t.Wm)(U,{prop:"groupId",label:"分组 ID"}),(0,t.Wm)(U,{prop:"createAt",label:"角色分配时间"})])),_:1},8,["data"])])),_:1},8,["modelValue"]),(0,t.Wm)(x,{modelValue:w.isShowEditUserDialog,"onUpdate:modelValue":a[11]||(a[11]=e=>w.isShowEditUserDialog=e),width:"38%",center:"","destroy-on-close":"",title:"创建用户"},{default:(0,t.w5)((()=>[(0,t.Wm)(v,{model:w.userData,"label-position":"top",rules:w.userFormRule,ref:"userFormRef"},{default:(0,t.w5)((()=>[(0,t.Wm)(Z,{label:"昵称",prop:"nickname"},{default:(0,t.w5)((()=>[(0,t.Wm)(b,{modelValue:w.userData.nickname,"onUpdate:modelValue":a[4]||(a[4]=e=>w.userData.nickname=e)},null,8,["modelValue"])])),_:1}),(0,t.Wm)(Z,{label:"用户名",prop:"username"},{default:(0,t.w5)((()=>[(0,t.Wm)(b,{modelValue:w.userData.username,"onUpdate:modelValue":a[5]||(a[5]=e=>w.userData.username=e)},null,8,["modelValue"])])),_:1}),(0,t.Wm)(Z,{label:"邮箱",prop:"email"},{default:(0,t.w5)((()=>[(0,t.Wm)(b,{modelValue:w.userData.email,"onUpdate:modelValue":a[6]||(a[6]=e=>w.userData.email=e)},null,8,["modelValue"])])),_:1}),(0,t.Wm)(Z,{label:"密码",prop:"password"},{default:(0,t.w5)((()=>[(0,t.Wm)(b,{modelValue:w.userData.password,"onUpdate:modelValue":a[7]||(a[7]=e=>w.userData.password=e),type:"password",placeholder:"请输入密码","show-password":""},null,8,["modelValue"])])),_:1}),(0,t.Wm)(Z,{label:"启用状态"},{default:(0,t.w5)((()=>[(0,t.Wm)(k,{modelValue:w.userData.enabled,"onUpdate:modelValue":a[8]||(a[8]=e=>w.userData.enabled=e)},null,8,["modelValue"])])),_:1}),(0,t.Wm)(Z,null,{default:(0,t.w5)((()=>[(0,t.Wm)(h,{type:"primary",plain:"",onClick:a[9]||(a[9]=e=>g.onSaveUserData("userFormRef"))},{default:(0,t.w5)((()=>[d])),_:1}),(0,t.Wm)(h,{plain:"",onClick:a[10]||(a[10]=e=>w.isShowEditUserDialog=!1)},{default:(0,t.w5)((()=>[m])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1},8,["modelValue"])])),_:1})}var w=l(3110),g=l(7234),h=l(1836),c={data(){return{loading:{sysOwnerLoading:!1,userEnableLoading:!1},userData:{enabled:!0},userFormRule:{nickname:[this.requiredInputValidRule("昵称不能为空")],username:[this.requiredInputValidRule("用户名不能为空")],email:[this.requiredInputValidRule("邮箱不能为空"),{type:"email",message:"邮箱格式不正确",trigger:"blur"}],password:[this.requiredInputValidRule("密码不能为空"),{min:6,max:18,message:"密码位数位数要求在 6~18 之间",trigger:"blur"}]},userPageData:{content:[]},userPageQuery:{nicknameOrUsernameOrEmailContains:null,enabled:null,page:0,size:10},userDetailData:{},isShowUserDetailDrawer:!1,isShowEditUserDialog:!1,roleNameFormatter:function(e,a,l){return"SYS_OWNER"==l?"系统管理员":"GROUP_OWNER"==l?"组长":"GROUP_MEMBER"==l?"组员":l}}},created(){this.fetchUsers()},methods:{fetchUsers(){(0,w.yw)(this.userPageQuery).then((e=>{e.errCode||(this.userPageData=e.data,this.userPageData.number=e.data.number+1)}))},requiredInputValidRule(e){return{required:!0,message:e,trigger:"blur"}},onSwitchEnabled(e,a){a?(0,w.Dx)(e):(0,w.B9)(e)},onRenewPassword(e){this.$confirm("确认重置该用户密码？新密码将通过邮件下发","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{(0,w.oD)(e).then((e=>{e.errCode||(0,g.z8)({showClose:!0,message:"密码重置成功",type:"success",duration:3e3})}))}))},onDeleteUser(e){this.$confirm("确认删除该用户吗？删除后无法恢复，请谨慎操作","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{(0,w.IJ)(e).then((e=>{e.errCode||(this.fetchUsers(),(0,g.z8)({showClose:!0,message:"用户删除成功",type:"success",duration:3e3}))}))}))},onPageChange(e){e&&e-1!=this.userPageQuery.page&&(this.userPageQuery.page=e-1,this.fetchUsers())},onPageSizeChange(e){e&&(this.userPageQuery.size=e,this.fetchUsers())},onQuery(){this.userPageQuery.page=0,this.fetchUsers()},onGetUserDetail(e){this.isShowUserDetailDrawer=!0,(0,w.__)(e.id).then((e=>{e.errCode||(this.userDetailData=e.data)}))},onSaveUserData(){this.$refs.userFormRef.validate((e=>!!e&&((0,w.r4)(this.userData).then((e=>{e.errCode||(this.$message.success("保存用户成功"),this.isShowEditUserDialog=!1,this.userData={enabled:!1},this.fetchUsers())})),!0)))},onChangeSysOwner(e){const a=e.id;return this.loading.sysOwnerLoading=!0,e.isSysOwner?(0,w._)(a).then((e=>{e.errCode||this.$message.success("启用系统管理员成功"),this.loading.sysOwnerLoading=!1})):(0,w.Uz)(a).then((e=>{e.errCode||this.$message.warning("禁用系统管理员成功"),this.loading.sysOwnerLoading=!1}))},toCreatePage(){this.isShowEditUserDialog=!0},toGroupPage(e,a){e&&this.$router.push({path:"/groups/"+e,query:{groupName:a}})},shouldDisableSwitch(e){const a=h.E.loadUserLoginData();return!(!a||a.id!=e.id)}}},f=l(3744);const b=(0,f.Z)(c,[["render",p]]);var D=b}}]);
//# sourceMappingURL=850.6048a74c.js.map