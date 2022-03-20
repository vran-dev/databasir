"use strict";(self["webpackChunkdatabasir_frontend"]=self["webpackChunkdatabasir_frontend"]||[]).push([[130],{3110:function(e,a,l){l.d(a,{yw:function(){return n},Dx:function(){return u},B9:function(){return o},__:function(){return s},r4:function(){return i},oD:function(){return d},_:function(){return m},Uz:function(){return p},gQ:function(){return w},Gz:function(){return g}});var r=l(3872);const t="/api/v1.0/users",n=e=>r.Z.get(t,{params:e}),u=e=>r.Z.post(t+"/"+e+"/enable"),o=e=>r.Z.post(t+"/"+e+"/disable"),s=e=>r.Z.get(t+"/"+e),i=e=>r.Z.post(t,e),d=e=>r.Z.post(t+"/"+e+"/renew_password"),m=e=>r.Z.post(t+"/"+e+"/sys_owners"),p=e=>r.Z["delete"](t+"/"+e+"/sys_owners"),w=(e,a)=>r.Z.post(t+"/"+e+"/password",a),g=(e,a)=>r.Z.post(t+"/"+e+"/nickname",a)},8130:function(e,a,l){l.r(a),l.d(a,{default:function(){return b}});var r=l(6252),t=l(3577),n=l(9963);const u=(0,r.Uk)("重置密码"),o=(0,r._)("br",null,null,-1),s=(0,r._)("h3",null,"角色信息",-1),i=(0,r.Uk)("保存"),d=(0,r.Uk)("取消");function m(e,a,l,m,p,w){const g=(0,r.up)("el-button"),h=(0,r.up)("el-tooltip"),c=(0,r.up)("el-col"),f=(0,r.up)("el-option"),b=(0,r.up)("el-select"),D=(0,r.up)("el-input"),W=(0,r.up)("el-row"),_=(0,r.up)("el-header"),U=(0,r.up)("el-table-column"),y=(0,r.up)("el-link"),C=(0,r.up)("el-switch"),V=(0,r.up)("el-table"),k=(0,r.up)("el-main"),P=(0,r.up)("el-pagination"),z=(0,r.up)("el-footer"),S=(0,r.up)("el-descriptions-item"),O=(0,r.up)("el-descriptions"),E=(0,r.up)("List"),R=(0,r.up)("el-icon"),Q=(0,r.up)("el-drawer"),v=(0,r.up)("el-form-item"),I=(0,r.up)("el-form"),Z=(0,r.up)("el-dialog"),x=(0,r.up)("el-container");return(0,r.wg)(),(0,r.j4)(x,null,{default:(0,r.w5)((()=>[(0,r.Wm)(_,null,{default:(0,r.w5)((()=>[(0,r.Wm)(W,{gutter:12},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{span:2},{default:(0,r.w5)((()=>[(0,r.Wm)(h,{content:"创建新用户",placement:"top"},{default:(0,r.w5)((()=>[(0,r.Wm)(g,{type:"primary",icon:"plus",style:{width:"100%"},onClick:a[0]||(a[0]=e=>w.toCreatePage())})])),_:1})])),_:1}),(0,r.Wm)(c,{span:3},{default:(0,r.w5)((()=>[(0,r.Wm)(b,{modelValue:p.userPageQuery.enabled,"onUpdate:modelValue":a[1]||(a[1]=e=>p.userPageQuery.enabled=e),placeholder:"启用状态",onChange:w.onQuery,clearable:""},{default:(0,r.w5)((()=>[((0,r.wg)(),(0,r.iD)(r.HY,null,(0,r.Ko)([!0,!1],(e=>(0,r.Wm)(f,{key:e,label:e?"启用":"禁用",value:e},null,8,["label","value"]))),64))])),_:1},8,["modelValue","onChange"])])),_:1}),(0,r.Wm)(c,{span:6},{default:(0,r.w5)((()=>[(0,r.Wm)(D,{onChange:w.onQuery,modelValue:p.userPageQuery.nicknameOrUsernameOrEmailContains,"onUpdate:modelValue":a[2]||(a[2]=e=>p.userPageQuery.nicknameOrUsernameOrEmailContains=e),label:"用户名",placeholder:"昵称、用户名或邮箱搜索","prefix-icon":"search"},null,8,["onChange","modelValue"])])),_:1})])),_:1})])),_:1}),(0,r.Wm)(k,null,{default:(0,r.w5)((()=>[(0,r.Wm)(V,{data:p.userPageData.content,border:"",width:"80%"},{default:(0,r.w5)((()=>[(0,r.Wm)(U,{prop:"id",label:"ID","min-width":"60",fixed:"left"}),(0,r.Wm)(U,{prop:"nickname",label:"昵称","min-width":"120",fixed:"left",resizable:""}),(0,r.Wm)(U,{prop:"username",label:"用户名","min-width":"120",resizable:""}),(0,r.Wm)(U,{label:"邮箱",width:"200",resizable:""},{default:(0,r.w5)((e=>[(0,r.Wm)(y,{underline:!0,onClick:a=>w.onGetUserDetail(e.row),type:"primary"},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(e.row.email),1)])),_:2},1032,["onClick"])])),_:1}),(0,r.Wm)(U,{label:"启用状态",resizable:""},{default:(0,r.w5)((e=>[(0,r.Wm)(C,{modelValue:e.row.enabled,"onUpdate:modelValue":a=>e.row.enabled=a,loading:p.loading.userEnableLoading,onChange:a=>w.onSwitchEnabled(e.row.id,e.row.enabled),disabled:w.shouldDisableSwitch(e.row)},null,8,["modelValue","onUpdate:modelValue","loading","onChange","disabled"])])),_:1}),(0,r.Wm)(U,{label:"系统管理员"},{default:(0,r.w5)((e=>[(0,r.Wm)(C,{modelValue:e.row.isSysOwner,"onUpdate:modelValue":a=>e.row.isSysOwner=a,loading:p.loading.sysOwnerLoading,onChange:a=>w.onChangeSysOwner(e.row),disabled:w.shouldDisableSwitch(e.row)},null,8,["modelValue","onUpdate:modelValue","loading","onChange","disabled"])])),_:1}),(0,r.Wm)(U,{prop:"createAt",label:"创建时间","min-width":"140"}),(0,r.Wm)(U,{label:"操作","min-width":"120",resizable:""},{default:(0,r.w5)((e=>[(0,r.Wm)(g,{type:"danger",size:"small",onClick:(0,n.iM)((a=>w.onRenewPassword(e.row.id)),["stop"])},{default:(0,r.w5)((()=>[u])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"])])),_:1}),(0,r.Wm)(z,null,{default:(0,r.w5)((()=>[(0,r.Wm)(P,{layout:"sizes, prev, pager, next","hide-on-single-page":!1,currentPage:p.userPageData.number,"page-size":p.userPageQuery.size,"page-sizes":[10,15,20,30],"page-count":p.userPageData.totalPages,onSizeChange:w.onPageSizeChange,onCurrentChange:w.onPageChange},null,8,["currentPage","page-size","page-count","onSizeChange","onCurrentChange"])])),_:1}),(0,r.Wm)(Q,{modelValue:p.isShowUserDetailDrawer,"onUpdate:modelValue":a[3]||(a[3]=e=>p.isShowUserDetailDrawer=e),title:"用户详情",direction:"rtl",size:"50%"},{default:(0,r.w5)((()=>[(0,r.Wm)(O,{title:"基础信息",column:1,border:""},{default:(0,r.w5)((()=>[(0,r.Wm)(S,{label:"ID"},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.id),1)])),_:1}),(0,r.Wm)(S,{label:"昵称"},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.nickname),1)])),_:1}),(0,r.Wm)(S,{label:"用户名"},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.username),1)])),_:1}),(0,r.Wm)(S,{label:"邮箱",span:2},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.email),1)])),_:1}),(0,r.Wm)(S,{label:"启用状态",span:2},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.enabled?"启用中":"已禁用"),1)])),_:1}),(0,r.Wm)(S,{label:"注册时间",span:2},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(p.userDetailData.createAt),1)])),_:1})])),_:1}),o,s,(0,r.Wm)(V,{data:p.userDetailData.roles,stripe:""},{default:(0,r.w5)((()=>[(0,r.Wm)(U,{label:"角色",prop:"role",formatter:p.roleNameFormatter},null,8,["formatter"]),(0,r.Wm)(U,{label:"所属分组"},{default:(0,r.w5)((e=>[e.row.groupId?((0,r.wg)(),(0,r.j4)(y,{key:0,onClick:a=>w.toGroupPage(e.row.groupId,e.row.groupName)},{default:(0,r.w5)((()=>[(0,r.Uk)((0,t.zw)(e.row.groupName)+" ",1),(0,r.Wm)(R,null,{default:(0,r.w5)((()=>[(0,r.Wm)(E)])),_:1})])),_:2},1032,["onClick"])):(0,r.kq)("",!0)])),_:1}),(0,r.Wm)(U,{prop:"groupId",label:"分组 ID"}),(0,r.Wm)(U,{prop:"createAt",label:"角色分配时间"})])),_:1},8,["data"])])),_:1},8,["modelValue"]),(0,r.Wm)(Z,{modelValue:p.isShowEditUserDialog,"onUpdate:modelValue":a[11]||(a[11]=e=>p.isShowEditUserDialog=e),width:"38%",center:"","destroy-on-close":"",title:"创建用户"},{default:(0,r.w5)((()=>[(0,r.Wm)(I,{model:p.userData,"label-position":"top",rules:p.userFormRule,ref:"userFormRef"},{default:(0,r.w5)((()=>[(0,r.Wm)(v,{label:"昵称",prop:"nickname"},{default:(0,r.w5)((()=>[(0,r.Wm)(D,{modelValue:p.userData.nickname,"onUpdate:modelValue":a[4]||(a[4]=e=>p.userData.nickname=e)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(v,{label:"用户名",prop:"username"},{default:(0,r.w5)((()=>[(0,r.Wm)(D,{modelValue:p.userData.username,"onUpdate:modelValue":a[5]||(a[5]=e=>p.userData.username=e)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(v,{label:"邮箱",prop:"email"},{default:(0,r.w5)((()=>[(0,r.Wm)(D,{modelValue:p.userData.email,"onUpdate:modelValue":a[6]||(a[6]=e=>p.userData.email=e)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(v,{label:"密码",prop:"password"},{default:(0,r.w5)((()=>[(0,r.Wm)(D,{modelValue:p.userData.password,"onUpdate:modelValue":a[7]||(a[7]=e=>p.userData.password=e),type:"password",placeholder:"请输入密码","show-password":""},null,8,["modelValue"])])),_:1}),(0,r.Wm)(v,{label:"启用状态"},{default:(0,r.w5)((()=>[(0,r.Wm)(C,{modelValue:p.userData.enabled,"onUpdate:modelValue":a[8]||(a[8]=e=>p.userData.enabled=e)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(v,null,{default:(0,r.w5)((()=>[(0,r.Wm)(g,{type:"primary",plain:"",onClick:a[9]||(a[9]=e=>w.onSaveUserData("userFormRef"))},{default:(0,r.w5)((()=>[i])),_:1}),(0,r.Wm)(g,{plain:"",onClick:a[10]||(a[10]=e=>p.isShowEditUserDialog=!1)},{default:(0,r.w5)((()=>[d])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1},8,["modelValue"])])),_:1})}var p=l(3110),w=l(7234),g=l(1836),h={data(){return{loading:{sysOwnerLoading:!1,userEnableLoading:!1},userData:{enabled:!1},userFormRule:{nickname:[this.requiredInputValidRule("昵称不能为空")],username:[this.requiredInputValidRule("用户名不能为空")],email:[this.requiredInputValidRule("邮箱不能为空"),{type:"email",message:"邮箱格式不正确",trigger:"blur"}],password:[this.requiredInputValidRule("密码不能为空"),{min:6,max:18,message:"密码位数位数要求在 6~18 之间",trigger:"blur"}]},userPageData:{content:[]},userPageQuery:{nicknameOrUsernameOrEmailContains:null,enabled:null,page:0,size:10},userDetailData:{},isShowUserDetailDrawer:!1,isShowEditUserDialog:!1,roleNameFormatter:function(e,a,l){return"SYS_OWNER"==l?"系统管理员":"GROUP_OWNER"==l?"组长":"GROUP_MEMBER"==l?"组员":l}}},created(){this.fetchUsers()},methods:{fetchUsers(){(0,p.yw)(this.userPageQuery).then((e=>{e.errCode||(this.userPageData=e.data,this.userPageData.number=e.data.number+1)}))},requiredInputValidRule(e){return{required:!0,message:e,trigger:"blur"}},onSwitchEnabled(e,a){a?(0,p.Dx)(e):(0,p.B9)(e)},onRenewPassword(e){this.$confirm("确认重置该用户密码？新密码将通过邮件下发","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{(0,p.oD)(e).then((e=>{e.errCode||(0,w.z8)({showClose:!0,message:"密码重置成功",type:"success",duration:3e3})}))}))},onPageChange(e){e&&e-1!=this.userPageQuery.page&&(this.userPageQuery.page=e-1,this.fetchUsers())},onPageSizeChange(e){e&&(this.userPageQuery.size=e,this.fetchUsers())},onQuery(){this.userPageQuery.page=0,this.fetchUsers()},onGetUserDetail(e){this.isShowUserDetailDrawer=!0,(0,p.__)(e.id).then((e=>{e.errCode||(this.userDetailData=e.data)}))},onSaveUserData(){this.$refs.userFormRef.validate((e=>!!e&&((0,p.r4)(this.userData).then((e=>{e.errCode||(this.$message.success("保存用户成功"),this.isShowEditUserDialog=!1,this.userData={enabled:!1},this.fetchUsers())})),!0)))},onChangeSysOwner(e){const a=e.id;return this.loading.sysOwnerLoading=!0,e.isSysOwner?(0,p._)(a).then((e=>{e.errCode||this.$message.success("启用系统管理员成功"),this.loading.sysOwnerLoading=!1})):(0,p.Uz)(a).then((e=>{e.errCode||this.$message.warning("禁用系统管理员成功"),this.loading.sysOwnerLoading=!1}))},toCreatePage(){this.isShowEditUserDialog=!0},toGroupPage(e,a){e&&this.$router.push({path:"/groups/"+e,query:{groupName:a}})},shouldDisableSwitch(e){const a=g.E.loadUserLoginData();return!(!a||a.id!=e.id)}}},c=l(3744);const f=(0,c.Z)(h,[["render",m]]);var b=f}}]);
//# sourceMappingURL=130.c7db75bc.js.map