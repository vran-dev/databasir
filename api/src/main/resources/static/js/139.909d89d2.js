"use strict";(self["webpackChunkdatabasir_frontend"]=self["webpackChunkdatabasir_frontend"]||[]).push([[139],{8787:function(e,t,n){n.d(t,{aF:function(){return p},nK:function(){return r},Hj:function(){return l},Jp:function(){return i},FO:function(){return s},ri:function(){return d},KT:function(){return c}});var a=n(3872);const o="/oauth2",p=()=>a.Z.get(o+"/apps"),r=(e,t)=>a.Z.get(o+"/authorization/"+e,{params:t}),u="/api/v1.0/oauth2_apps",l=e=>a.Z.get(u,{params:e}),i=e=>a.Z["delete"](u+"/"+e),s=e=>a.Z.get(u+"/"+e),d=e=>a.Z.post(u,e),c=e=>a.Z.patch(u,e)},1082:function(e,t,n){n.d(t,{Z:function(){return d}});var a=n(6252),o=n(3577);const p=["src"],r=["src"];function u(e,t,u,l,i,s){const d=(0,a.up)("el-tooltip");return"github"==u.appType.toLowerCase()?((0,a.wg)(),(0,a.j4)(d,{key:0,content:u.appName,effect:"light"},{default:(0,a.w5)((()=>[(0,a._)("img",{src:u.appIcon?u.appIcon:n(9710),style:{"max-width":"20px","max-height":"20px"}},null,8,p)])),_:1},8,["content"])):"gitlab"==u.appType.toLowerCase()?((0,a.wg)(),(0,a.j4)(d,{key:1,content:u.appName,effect:"light"},{default:(0,a.w5)((()=>[(0,a._)("img",{src:u.appIcon?u.appIcon:n(4386),style:{"max-width":"20px","max-height":"20px"}},null,8,r)])),_:1},8,["content"])):((0,a.wg)(),(0,a.iD)(a.HY,{key:2},[(0,a.Uk)((0,o.zw)(u.appType),1)],64))}var l={props:{appType:String,appIcon:String,appName:String}},i=n(3744);const s=(0,i.Z)(l,[["render",u]]);var d=s},1139:function(e,t,n){n.r(t),n.d(t,{default:function(){return w}});var a=n(6252),o=n(9963);const p={class:"login-card"},r=(0,a._)("h1",null,"Databasir",-1),u=(0,a.Uk)(" 登录 "),l=(0,a.Uk)(" 忘记密码？ ");function i(e,t,n,i,s,d){const c=(0,a.up)("el-header"),f=(0,a.up)("el-link"),m=(0,a.up)("el-divider"),g=(0,a.up)("el-form-item"),h=(0,a.up)("el-button"),w=(0,a.up)("el-tooltip"),_=(0,a.up)("el-space"),y=(0,a.up)("oauth2-app-type"),k=(0,a.up)("el-form"),b=(0,a.up)("el-main"),W=(0,a.up)("el-footer"),v=(0,a.up)("el-container");return(0,a.wg)(),(0,a.j4)(v,null,{default:(0,a.w5)((()=>[(0,a.Wm)(c),(0,a.Wm)(b,{class:"login-main"},{default:(0,a.w5)((()=>[(0,a._)("div",p,[(0,a.Wm)(k,{ref:"formRef",rules:s.formRule,model:s.form,style:{border:"none"}},{default:(0,a.w5)((()=>[(0,a.Wm)(g,null,{default:(0,a.w5)((()=>[(0,a.Wm)(m,{"content-position":"left"},{default:(0,a.w5)((()=>[(0,a.Wm)(f,{href:"https://github.com/vran-dev/databasir",target:"_blank",underline:!1,type:"info"},{default:(0,a.w5)((()=>[r])),_:1})])),_:1})])),_:1}),(0,a.Wm)(g,{prop:"username"},{default:(0,a.w5)((()=>[(0,a.wy)((0,a._)("input",{type:"text",class:"login-input",placeholder:"用户名或邮箱","onUpdate:modelValue":t[0]||(t[0]=e=>s.form.username=e),onKeyup:t[1]||(t[1]=(0,o.D2)((e=>d.onLogin("formRef")),["enter"]))},null,544),[[o.nr,s.form.username]])])),_:1}),(0,a.Wm)(g,{prop:"password"},{default:(0,a.w5)((()=>[(0,a.wy)((0,a._)("input",{type:"password",class:"login-input",placeholder:"密码","onUpdate:modelValue":t[2]||(t[2]=e=>s.form.password=e),onKeyup:t[3]||(t[3]=(0,o.D2)((e=>d.onLogin("formRef")),["enter"]))},null,544),[[o.nr,s.form.password]])])),_:1}),(0,a.Wm)(g,null,{default:(0,a.w5)((()=>[(0,a.Wm)(_,{size:32},{default:(0,a.w5)((()=>[(0,a.Wm)(h,{style:{width:"120px","margin-top":"10px"},color:"#000",onClick:t[4]||(t[4]=e=>d.onLogin("formRef")),plain:"",round:""},{default:(0,a.w5)((()=>[u])),_:1}),(0,a.Wm)(w,{content:"请联系管理员为您重置密码"},{default:(0,a.w5)((()=>[(0,a.Wm)(f,{target:"_blank",underline:!1,type:"info"},{default:(0,a.w5)((()=>[l])),_:1})])),_:1})])),_:1})])),_:1}),(0,a.Wm)(g,null,{default:(0,a.w5)((()=>[s.oauthApps.length>0?((0,a.wg)(),(0,a.j4)(m,{key:0,"content-position":"right"},{default:(0,a.w5)((()=>[(0,a.Wm)(_,{size:26},{default:(0,a.w5)((()=>[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(s.oauthApps,((e,t)=>((0,a.wg)(),(0,a.j4)(f,{key:t,underline:!1,onClick:t=>d.onAuthLogin(e.registrationId)},{default:(0,a.w5)((()=>[(0,a.Wm)(y,{"app-type":e.appType,"app-icon":e.appIcon,"app-name":e.appName},null,8,["app-type","app-icon","app-name"])])),_:2},1032,["onClick"])))),128))])),_:1})])),_:1})):(0,a.kq)("",!0)])),_:1})])),_:1},8,["rules","model"])])])),_:1}),(0,a.Wm)(W,null,{default:(0,a.w5)((()=>[(0,a.Wm)(_)])),_:1})])),_:1})}var s=n(152),d=n(1836),c=n(8787),f=n(1082),m={components:{Oauth2AppType:f.Z},data(){return{form:{username:null,password:null},formRule:{username:[{required:!0,message:"请输入用户名或邮箱",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}]},oauthApps:[]}},created(){this.fetchAllOAuthApps()},methods:{resolveAppIcon(e){return e.appIcon&&""!=e.appIcon?e.appIcon:"github"==e.appType?n(9710):"gitlab"==e.appType?n(4386):""},fetchAllOAuthApps(){(0,c.aF)().then((e=>{e.errCode||(this.oauthApps=e.data)}))},toIndexPage(){this.$router.push({path:"/groups"})},onAuthLogin(e){const t=window.location.protocol,n=t+"//"+window.location.host+"/login/oauth2/"+e,a={redirect_uri:n};(0,c.nK)(e,a).then((e=>{e.errCode||(window.location.href=e.data)}))},onLogin(){this.$refs.formRef.validate((e=>{e&&(0,s.x4)(this.form).then((e=>{e.errCode||(d.E.saveUserLoginData(e.data),this.$store.commit("userUpdate",{nickname:e.data.nickname,username:e.data.username,email:e.data.email}),this.toIndexPage())}))}))}}},g=n(3744);const h=(0,g.Z)(m,[["render",i]]);var w=h},9710:function(e,t,n){e.exports=n.p+"img/github.e45f4724.svg"},4386:function(e,t,n){e.exports=n.p+"img/gitlab.6b1155ee.svg"}}]);
//# sourceMappingURL=139.909d89d2.js.map