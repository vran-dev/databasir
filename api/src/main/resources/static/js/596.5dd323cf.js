"use strict";(self["webpackChunkdatabasir_frontend"]=self["webpackChunkdatabasir_frontend"]||[]).push([[596],{7596:function(e,l,t){t.r(l),t.d(l,{default:function(){return _}});var r=t(6252);const a=(0,r.Uk)(" 系统邮箱设置 "),s=(0,r.Uk)(" : "),o=(0,r.Uk)("保存"),u=(0,r.Uk)("重置");function m(e,l,t,m,n,d){const p=(0,r.up)("box"),i=(0,r.up)("el-icon"),f=(0,r.up)("el-divider"),c=(0,r.up)("el-input"),h=(0,r.up)("el-form-item"),w=(0,r.up)("el-col"),_=(0,r.up)("el-button"),g=(0,r.up)("el-form"),W=(0,r.up)("el-card"),V=(0,r.up)("el-main"),y=(0,r.up)("el-container");return(0,r.wg)(),(0,r.j4)(y,null,{default:(0,r.w5)((()=>[(0,r.Wm)(V,null,{default:(0,r.w5)((()=>[(0,r.Wm)(W,{shadow:"hover",style:{"max-width":"600px"}},{default:(0,r.w5)((()=>[(0,r.Wm)(f,null,{default:(0,r.w5)((()=>[(0,r.Wm)(i,null,{default:(0,r.w5)((()=>[(0,r.Wm)(p)])),_:1}),a])),_:1}),(0,r.Wm)(g,{model:n.form,"label-position":"top",rules:n.formRule,ref:"formRef",style:{"max-width":"900px"}},{default:(0,r.w5)((()=>[(0,r.Wm)(h,{label:"邮箱账号",prop:"username"},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{modelValue:n.form.username,"onUpdate:modelValue":l[0]||(l[0]=e=>n.form.username=e),placeholder:"请输入邮箱账号"},null,8,["modelValue"])])),_:1}),(0,r.Wm)(h,{label:"邮箱密码",prop:"password"},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{modelValue:n.form.password,"onUpdate:modelValue":l[1]||(l[1]=e=>n.form.password=e),type:"password",placeholder:"请输入密码","show-password":""},null,8,["modelValue"])])),_:1}),(0,r.Wm)(h,{label:"SMTP",prop:"smtpHost"},{default:(0,r.w5)((()=>[(0,r.Wm)(w,{span:12},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{modelValue:n.form.smtpHost,"onUpdate:modelValue":l[2]||(l[2]=e=>n.form.smtpHost=e),placeholder:"SMTP Host"},null,8,["modelValue"])])),_:1}),(0,r.Wm)(w,{span:1,style:{"text-align":"center"}},{default:(0,r.w5)((()=>[s])),_:1}),(0,r.Wm)(w,{span:6},{default:(0,r.w5)((()=>[(0,r.Wm)(c,{modelValue:n.form.smtpPort,"onUpdate:modelValue":l[3]||(l[3]=e=>n.form.smtpPort=e),placeholder:"SMTP Port"},null,8,["modelValue"])])),_:1})])),_:1}),(0,r.Wm)(h,{style:{"margin-top":"38px"}},{default:(0,r.w5)((()=>[(0,r.Wm)(_,{type:"primary",onClick:l[4]||(l[4]=e=>d.onSubmit("formRef"))},{default:(0,r.w5)((()=>[o])),_:1}),(0,r.Wm)(_,{type:"danger",onClick:l[5]||(l[5]=e=>d.onReset())},{default:(0,r.w5)((()=>[u])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1})])),_:1})])),_:1})}var n=t(3872);const d="/api/v1.0/settings",p=()=>n.Z.get(d+"/sys_email"),i=e=>n.Z.post(d+"/sys_email",e),f=()=>n.Z["delete"](d+"/sys_email");var c={data(){return{form:{smtpHost:null,smtpPort:null,username:null,password:null},formRule:{username:[this.requiredInputValidRule("请输入邮箱账号"),{type:"email",message:"邮箱格式不正确",trigger:"blur"}],password:[this.requiredInputValidRule("请输入邮箱密码")],smtpHost:[this.requiredInputValidRule("请输入 SMTP 地址")],smtpPort:[this.requiredInputValidRule("请输入 SMTP 端口"),{min:1,max:65535,message:"端口有效值为 1~65535",trigger:"blur"}]}}},mounted(){this.fetchSysMail()},methods:{requiredInputValidRule(e){return{required:!0,message:e,trigger:"blur"}},async fetchSysMail(){const e=await p().then((e=>e.data));e&&(this.form=e)},onSubmit(){this.$refs.formRef.validate((e=>e?(i(this.form).then((e=>{e.errCode||this.$message.success("更新成功")})),!0):(this.$message.error("请完善表单相关信息！"),!1)))},onReset(){this.$confirm("确认重置系统邮件吗？删除后数据将无法恢复","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{f().then((e=>{e.errCode||(this.form={},this.$message.success("重置成功"))}))}))}}},h=t(3744);const w=(0,h.Z)(c,[["render",m]]);var _=w}}]);
//# sourceMappingURL=596.5dd323cf.js.map