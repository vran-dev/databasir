"use strict";(self["webpackChunkdatabasir"]=self["webpackChunkdatabasir"]||[]).push([[834],{38787:function(e,t,n){n.d(t,{aF:function(){return r},nK:function(){return l},Hj:function(){return o},Jp:function(){return i},FO:function(){return c},ri:function(){return m},KT:function(){return d}});var a=n(63872),u="/oauth2",r=function(){return a.Z.get(u+"/apps")},l=function(e,t){return a.Z.get(u+"/authorization/"+e,{params:t})},p="/api/v1.0/oauth2_apps",o=function(e){return a.Z.get(p,{params:e})},i=function(e){return a.Z["delete"](p+"/"+e)},c=function(e){return a.Z.get(p+"/"+e)},m=function(e){return a.Z.post(p,e)},d=function(e){return a.Z.patch(p,e)}},1082:function(e,t,n){n.d(t,{Z:function(){return m}});var a=n(66252),u=n(3577),r=["src"],l=["src"];function p(e,t,p,o,i,c){var m=(0,a.up)("el-tooltip");return"github"==p.appType.toLowerCase()?((0,a.wg)(),(0,a.j4)(m,{key:0,content:p.appName,effect:"light"},{default:(0,a.w5)((function(){return[(0,a._)("img",{src:p.appIcon?p.appIcon:n(39710),style:{"max-width":"20px","max-height":"20px"}},null,8,r)]})),_:1},8,["content"])):"gitlab"==p.appType.toLowerCase()?((0,a.wg)(),(0,a.j4)(m,{key:1,content:p.appName,effect:"light"},{default:(0,a.w5)((function(){return[(0,a._)("img",{src:p.appIcon?p.appIcon:n(84386),style:{"max-width":"20px","max-height":"20px"}},null,8,l)]})),_:1},8,["content"])):((0,a.wg)(),(0,a.iD)(a.HY,{key:2},[(0,a.Uk)((0,u.zw)(p.appType),1)],64))}var o={props:{appType:String,appIcon:String,appName:String}},i=n(83744);const c=(0,i.Z)(o,[["render",p]]);var m=c},86834:function(e,t,n){n.r(t),n.d(t,{default:function(){return k}});var a=n(66252),u=n(3577),r={style:{"margin-left":"12px"}},l=(0,a.Uk)(" 应用 ID "),p=(0,a.Uk)(" 应用类型 "),o=(0,a.Uk)(" clientId "),i=(0,a.Uk)(" 资源地址 "),c=(0,a.Uk)(" 授权地址 "),m=(0,a.Uk)(" 创建时间 "),d={style:{"margin-top":"20px"}},f=(0,a.Uk)(" 编辑 "),s=(0,a.Uk)(" 删除 "),g=(0,a.Uk)("保存"),w=(0,a.Uk)("取消");function h(e,t,n,h,W,_){var y=(0,a.up)("el-button"),D=(0,a.up)("el-tooltip"),b=(0,a.up)("el-col"),k=(0,a.up)("el-input"),U=(0,a.up)("el-option"),C=(0,a.up)("el-select"),P=(0,a.up)("el-divider"),x=(0,a.up)("el-row"),F=(0,a.up)("oauth2-app-type"),V=(0,a.up)("top-right"),v=(0,a.up)("el-icon"),A=(0,a.up)("el-descriptions-item"),I=(0,a.up)("el-tag"),z=(0,a.up)("tickets"),T=(0,a.up)("Link"),S=(0,a.up)("el-link"),E=(0,a.up)("clock"),N=(0,a.up)("el-descriptions"),Q=(0,a.up)("el-space"),Z=(0,a.up)("el-card"),j=(0,a.up)("el-empty"),q=(0,a.up)("el-form-item"),H=(0,a.up)("info-filled"),K=(0,a.up)("el-form"),$=(0,a.up)("el-dialog"),O=(0,a.up)("el-main"),R=(0,a.up)("el-pagination"),B=(0,a.up)("el-footer"),L=(0,a.up)("el-container");return(0,a.wg)(),(0,a.j4)(L,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(O,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(x,{gutter:12},{default:(0,a.w5)((function(){return[(0,a.Wm)(b,{xs:24,sm:6,md:6,lg:3,xl:3},{default:(0,a.w5)((function(){return[(0,a.Wm)(D,{content:"创建应用",placement:"top"},{default:(0,a.w5)((function(){return[(0,a.Wm)(y,{type:"primary",icon:"plus",style:{width:"100%"},onClick:t[0]||(t[0]=function(e){return _.onAppCreate()})})]})),_:1})]})),_:1}),(0,a.Wm)(b,{xs:24,sm:10,md:10,lg:6,xl:4},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{onChange:_.onQuery,modelValue:W.appPageQuery.appNameContains,"onUpdate:modelValue":t[1]||(t[1]=function(e){return W.appPageQuery.appNameContains=e}),label:"应用名称",placeholder:"应用名称搜素","prefix-icon":"search"},null,8,["onChange","modelValue"])]})),_:1}),(0,a.Wm)(b,{xs:24,sm:8,md:8,lg:6,xl:4},{default:(0,a.w5)((function(){return[(0,a.Wm)(C,{modelValue:W.appPageQuery.appType,"onUpdate:modelValue":t[2]||(t[2]=function(e){return W.appPageQuery.appType=e}),placeholder:"应用类型",onChange:_.onQuery,clearable:"",style:{width:"100%"}},{default:(0,a.w5)((function(){return[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(W.appTypes,(function(e){return(0,a.wg)(),(0,a.j4)(U,{key:e,label:e,value:e},null,8,["label","value"])})),128))]})),_:1},8,["modelValue","onChange"])]})),_:1}),(0,a.Wm)(b,{span:24},{default:(0,a.w5)((function(){return[(0,a.Wm)(P)]})),_:1})]})),_:1}),(0,a.Wm)(x,{gutter:33},{default:(0,a.w5)((function(){return[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(W.appPageData.data,(function(t){return(0,a.wg)(),(0,a.j4)(b,{xs:24,sm:24,md:12,lg:8,xl:6,key:t.id},{default:(0,a.w5)((function(){return[(0,a.Wm)(Z,{shadow:"hover"},{default:(0,a.w5)((function(){return[(0,a.Wm)(P,{"content-position":"left"},{default:(0,a.w5)((function(){return[(0,a.Wm)(F,{"app-type":t.appType,"app-name":t.appName},null,8,["app-type","app-name"]),(0,a._)("span",r,(0,u.zw)(t.appName),1)]})),_:2},1024),(0,a._)("div",null,[(0,a.Wm)(N,{column:1,size:e.size,border:""},{default:(0,a.w5)((function(){return[(0,a.Wm)(A,{label:"appId"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(V)]})),_:1}),l]})),default:(0,a.w5)((function(){return[(0,a.Uk)(" "+(0,u.zw)(t.registrationId),1)]})),_:2},1024),(0,a.Wm)(A,{label:"appType"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(V)]})),_:1}),p]})),default:(0,a.w5)((function(){return[(0,a.Wm)(I,null,{default:(0,a.w5)((function(){return[(0,a.Uk)((0,u.zw)(t.appType),1)]})),_:2},1024)]})),_:2},1024),(0,a.Wm)(A,{label:"clientId"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(z)]})),_:1}),o]})),default:(0,a.w5)((function(){return[(0,a.Uk)(" "+(0,u.zw)(t.clientId),1)]})),_:2},1024),(0,a.Wm)(A,{label:"资源地址"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(T)]})),_:1}),i]})),default:(0,a.w5)((function(){return[(0,a.Wm)(S,null,{default:(0,a.w5)((function(){return[(0,a.Uk)((0,u.zw)(t.authUrl),1)]})),_:2},1024)]})),_:2},1024),(0,a.Wm)(A,{label:"授权地址"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(T)]})),_:1}),c]})),default:(0,a.w5)((function(){return[(0,a.Wm)(S,null,{default:(0,a.w5)((function(){return[(0,a.Uk)((0,u.zw)(t.resourceUrl),1)]})),_:2},1024)]})),_:2},1024),(0,a.Wm)(A,{label:"创建时间"},{label:(0,a.w5)((function(){return[(0,a.Wm)(v,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(E)]})),_:1}),m]})),default:(0,a.w5)((function(){return[(0,a.Uk)(" "+(0,u.zw)(t.createAt),1)]})),_:2},1024)]})),_:2},1032,["size"])]),(0,a._)("div",d,[(0,a.Wm)(Q,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(y,{type:"primary",size:"small",icon:"Edit",onClick:function(e){return _.onAppEdit(t)}},{default:(0,a.w5)((function(){return[f]})),_:2},1032,["onClick"]),(0,a.Wm)(y,{type:"danger",onClick:function(e){return _.onDelete(t)},size:"small",icon:"Delete"},{default:(0,a.w5)((function(){return[s]})),_:2},1032,["onClick"])]})),_:2},1024)])]})),_:2},1024)]})),_:2},1024)})),128))]})),_:1}),0==W.appPageData.data.length?((0,a.wg)(),(0,a.j4)(j,{key:0,"image-size":200,description:"暂无数据"})):(0,a.kq)("",!0),(0,a.Wm)($,{modelValue:W.isShowEditAppDialog,"onUpdate:modelValue":t[12]||(t[12]=function(e){return W.isShowEditAppDialog=e}),width:"38%",center:"","destroy-on-close":""},{default:(0,a.w5)((function(){return[(0,a.Wm)(K,{model:W.appFormData,rules:W.appFormDataRule,ref:"appFormDataRef","label-position":"top"},{default:(0,a.w5)((function(){return[(0,a.Wm)(x,{gutter:28},{default:(0,a.w5)((function(){return[(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"应用 ID",prop:"registrationId"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.registrationId,"onUpdate:modelValue":t[3]||(t[3]=function(e){return W.appFormData.registrationId=e}),placeholder:"建议输入全英文字符"},null,8,["modelValue"])]})),_:1})]})),_:1}),(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"应用名称",prop:"appName"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.appName,"onUpdate:modelValue":t[4]||(t[4]=function(e){return W.appFormData.appName=e}),placeholder:"用户可理解的登陆应用名"},null,8,["modelValue"])]})),_:1})]})),_:1})]})),_:1}),(0,a.Wm)(q,{label:"应用类型",prop:"appName"},{default:(0,a.w5)((function(){return[(0,a.Wm)(C,{modelValue:W.appFormData.appType,"onUpdate:modelValue":t[5]||(t[5]=function(e){return W.appFormData.appType=e}),placeholder:"请选择应用类型",size:"default"},{default:(0,a.w5)((function(){return[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(W.appTypes,(function(e){return(0,a.wg)(),(0,a.j4)(U,{key:e,label:e,value:e},null,8,["label","value"])})),128))]})),_:1},8,["modelValue"])]})),_:1}),(0,a.Wm)(x,{gutter:28},{default:(0,a.w5)((function(){return[(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"Client Id",prop:"clientId"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.clientId,"onUpdate:modelValue":t[6]||(t[6]=function(e){return W.appFormData.clientId=e}),placeholder:"Oauth2 平台下发的 clientId"},null,8,["modelValue"])]})),_:1})]})),_:1}),(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"Client Secret",prop:"clientSecret"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.clientSecret,"onUpdate:modelValue":t[7]||(t[7]=function(e){return W.appFormData.clientSecret=e}),placeholder:"Oauth2 平台下发的秘钥"},null,8,["modelValue"])]})),_:1})]})),_:1})]})),_:1}),(0,a.Wm)(x,{gutter:28},{default:(0,a.w5)((function(){return[(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"授权地址",prop:"authUrl"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.authUrl,"onUpdate:modelValue":t[8]||(t[8]=function(e){return W.appFormData.authUrl=e}),placeholder:"用于获取 access token 的服务地址"},null,8,["modelValue"])]})),_:1})]})),_:1}),(0,a.Wm)(b,{xs:24,sm:24,md:12,lg:10},{default:(0,a.w5)((function(){return[(0,a.Wm)(q,{label:"资源地址",prop:"resourceUrl"},{default:(0,a.w5)((function(){return[(0,a.Wm)(k,{modelValue:W.appFormData.resourceUrl,"onUpdate:modelValue":t[9]||(t[9]=function(e){return W.appFormData.resourceUrl=e}),placeholder:"用于获取用户信息的服务地址"},null,8,["modelValue"])]})),_:1})]})),_:1})]})),_:1}),(0,a.Wm)(x,{style:{"margin-bottom":"33px"}},{default:(0,a.w5)((function(){return[(0,a.Wm)(b,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(P,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(v,{color:"#000"},{default:(0,a.w5)((function(){return[(0,a.Wm)(H)]})),_:1}),(0,a.Uk)(" 请在 "+(0,u.zw)(W.appFormData.appType)+" 中配置回调地址 ",1)]})),_:1}),(0,a.Wm)(S,{type:"primary"},{default:(0,a.w5)((function(){return[(0,a.Uk)((0,u.zw)(W.redirectUri)+(0,u.zw)(W.appFormData.registrationId),1)]})),_:1})]})),_:1})]})),_:1}),(0,a.Wm)(q,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(y,{type:"primary",onClick:t[10]||(t[10]=function(e){return _.onAppSave("groupFormRef")})},{default:(0,a.w5)((function(){return[g]})),_:1}),(0,a.Wm)(y,{onClick:t[11]||(t[11]=function(e){return W.isShowEditAppDialog=!1})},{default:(0,a.w5)((function(){return[w]})),_:1})]})),_:1})]})),_:1},8,["model","rules"])]})),_:1},8,["modelValue"])]})),_:1}),(0,a.Wm)(B,null,{default:(0,a.w5)((function(){return[(0,a.Wm)(R,{layout:"prev, pager, next","hide-on-single-page":!1,currentPage:W.appPageData.number,"page-size":W.appPageData.size,"page-count":W.appPageData.totalPages,onCurrentChange:_.onAppCurrentPageChange},null,8,["currentPage","page-size","page-count","onCurrentChange"])]})),_:1})]})),_:1})}var W=n(38787),_=n(1082),y={components:{Oauth2AppType:_.Z},data:function(){return{appPageData:{data:[],number:1,size:10,totalElements:0,totalPages:0},appPageQuery:{page:0,size:10,appNameContains:null,appType:null},isShowEditAppDialog:!1,redirectUri:"",appFormData:{id:null},appFormDataRule:{registrationId:[{required:!0,message:"请为应用配置唯一 ID",trigger:"blur"}],appName:[{required:!0,message:"请输入应用名称",trigger:"blur"}],appType:[{required:!0,message:"请选择应用类型",trigger:"blur"}],authUrl:[{required:!0,message:"请配置请求授权地址",trigger:"blur"}],resourceUrl:[{required:!0,message:"请配置资源 API 地址",trigger:"blur"}],clientId:[{required:!0,message:"请配置申请的 clientId",trigger:"blur"}],clientSecret:[{required:!0,message:"请配置申请的 clientSecret",trigger:"blur"}]},appTypes:["GITLAB","GITHUB"]}},created:function(){this.redirectUri=window.location.protocol+"//"+window.location.host+"/login/oauth2/",this.onAppCurrentPageChange(1)},methods:{fetchApps:function(e){var t=this;e&&(this.appPageQuery.page=e-1),""==this.appPageQuery.appType&&(this.appPageQuery.appType=null),(0,W.Hj)(this.appPageQuery).then((function(e){e.errCode||(t.appPageData.data=e.data.content,t.appPageData.number=e.data.number+1,t.appPageData.size=e.data.size,t.appPageData.totalPages=e.data.totalPages,t.appPageData.totalElements=e.data.totalElements)}))},onAppCurrentPageChange:function(e){e&&this.fetchApps(e-1)},onQuery:function(){this.fetchApps()},onAppEdit:function(e){var t=this;(0,W.FO)(e.id).then((function(e){e.errCode||(t.appFormData=e.data,t.isShowEditAppDialog=!0)}))},onAppCreate:function(){this.appFormData={},this.isShowEditAppDialog=!0},onDelete:function(e){var t=this;this.$confirm("确认删除该 APP 登录方式吗？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){(0,W.Jp)(e.id).then((function(e){e.errCode||(t.$message.success("删除成功"),t.fetchApps())}))}))},onAppSave:function(){var e=this;this.$refs.appFormDataRef.validate((function(t){t?e.appFormData.id?(0,W.KT)(e.appFormData).then((function(t){t.errCode||(e.$message.success("更新成功"),e.isShowEditAppDialog=!1,e.fetchApps())})):(0,W.ri)(e.appFormData).then((function(t){t.errCode||(e.$message.success("创建成功"),e.isShowEditAppDialog=!1,e.fetchApps())})):e.$message.error("请填写表单必填项")}))}}},D=n(83744);const b=(0,D.Z)(y,[["render",h]]);var k=b},39710:function(e,t,n){e.exports=n.p+"img/github.e45f4724.svg"},84386:function(e,t,n){e.exports=n.p+"img/gitlab.6b1155ee.svg"}}]);
//# sourceMappingURL=834-legacy.07f2e4ab.js.map